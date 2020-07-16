package ru.dzgeorgy.auth.data

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.dzgeorgy.auth.R
import ru.dzgeorgy.auth.ui.activity.LoginActivity
import ru.dzgeorgy.core.account.AccountModule.TOKEN_TYPE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeafAuthenticator @Inject constructor(@ApplicationContext private val context: Context) :
    AbstractAccountAuthenticator(context) {

    override fun getAuthTokenLabel(p0: String?) = context.getString(R.string.app_name)

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle? = null

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle? = null

    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle {
        val am = AccountManager.get(context)
        val token = am.peekAuthToken(account, authTokenType)
        if (!token.isNullOrEmpty()) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, token)
            return result
        }
        val intent = Intent(context, LoginActivity::class.java)
        intent.apply {
            putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name)
            putExtra(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            putExtra(TOKEN_TYPE, authTokenType)
        }
        return Bundle().also { it.putParcelable(AccountManager.KEY_INTENT, intent) }
    }

    override fun hasFeatures(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Array<out String>?
    ): Bundle? = null

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle? = null

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://leaf/login"))
        intent.apply {
            putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType)
            putExtra(TOKEN_TYPE, authTokenType)
        }
        return Bundle().also { it.putParcelable(AccountManager.KEY_INTENT, intent) }
    }
}