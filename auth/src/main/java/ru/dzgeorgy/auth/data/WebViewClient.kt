package ru.dzgeorgy.auth.data

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature

class WebViewClient(
    private val onComplete: (token: String, id: Int) -> Unit,
    private val onError: (description: String) -> Unit,
    private val onPageLoad: () -> Unit
) : WebViewClientCompat() {

    companion object {
        const val AUTH_URI = "https://oauth.vk.com/authorize"
        const val REDIRECT_URI = "https://oauth.vk.com/blank.html"
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        onPageLoad.invoke()
    }

    @SuppressLint("RequiresFeature")
    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceErrorCompat
    ) {
        super.onReceivedError(view, request, error)
        if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_RESOURCE_ERROR_GET_CODE) && WebViewFeature.isFeatureSupported(
                WebViewFeature.WEB_RESOURCE_ERROR_GET_DESCRIPTION
            )
        ) {
            onError.invoke(error.description.toString())
        } else onError.invoke("")
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        var url = request.url
        return if (url.toString().startsWith(REDIRECT_URI)) {
            url = Uri.parse(url.toString().replace("#", "?"))

            val token = url.getQueryParameter("access_token")
            val id = url.getQueryParameter("user_id")?.toInt()
            val error = url.getQueryParameter("error")

            return if (id != null && token != null) {
                onComplete.invoke(token, id)
                true
            } else if (error != null) {
                onError.invoke(error)
                true
            } else false

        } else false
    }

}