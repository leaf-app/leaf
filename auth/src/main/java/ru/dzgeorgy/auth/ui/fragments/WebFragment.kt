package ru.dzgeorgy.auth.ui.fragments

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.auth.BuildConfig
import ru.dzgeorgy.auth.LoginViewModel
import ru.dzgeorgy.auth.R
import ru.dzgeorgy.auth.data.WebViewClient
import ru.dzgeorgy.auth.data.WebViewClient.Companion.AUTH_URI
import ru.dzgeorgy.auth.data.WebViewClient.Companion.REDIRECT_URI
import ru.dzgeorgy.auth.databinding.FragmentWebBinding

@AndroidEntryPoint
class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WebFragment.viewModel
        }
        viewModel.moveToProgress.observe(viewLifecycleOwner, Observer {
            if (it) findNavController().navigate(R.id.web_to_progress)
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupWebView()
    }

    private fun setupWebView() {
        with(binding.webview) {
            loadUrl(buildUrl())
            val nightModeFlags =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
                    WebSettingsCompat.setForceDarkStrategy(
                        settings,
                        WebSettingsCompat.DARK_STRATEGY_PREFER_WEB_THEME_OVER_USER_AGENT_DARKENING
                    )
                }
            }
            webViewClient = WebViewClient(
                onComplete = { token, id -> viewModel.onLoginSuccess(token, id) },
                onError = { error, description -> viewModel.onLoginFail(error, description) }
            )
        }
    }

    private fun buildUrl(): String {
        val builder = Uri.parse(AUTH_URI).buildUpon()
        for ((key, value) in urlParams()) {
            builder.appendQueryParameter(key, value)
        }
        return builder.build().toString()
    }

    private fun urlParams(): Map<String, String> = mapOf(
        "client_id" to BuildConfig.VK_APP_ID,
        "scope" to "66562",
        "redirect_uri" to REDIRECT_URI,
        "response_type" to "token",
        "display" to "mobile",
        "v" to ru.dzgeorgy.core.BuildConfig.VK_API_VERSION,
        "revoke" to "1"
    )

}