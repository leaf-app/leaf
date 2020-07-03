package ru.dzgeorgy.core.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

class AccountUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val ACCOUNT_TYPE = "ru.dzgeorgy.leaf"
        const val TOKEN_TYPE = "default"
    }

    @Serializable
    data class AccountInfo(
        val id: Int,
        val first_name: String,
        val last_name: String,
        val status: String,
        @SerialName("photo_max") val photo: String
    ) {
        fun getName() = "$first_name $last_name"
    }

    private val am = AccountManager.get(context)

    suspend fun createAccount(id: Int, token: String, data: AccountInfo) =
        withContext(Dispatchers.IO) {
            val account = Account(
                "${data.first_name} ${data.last_name}",
                ACCOUNT_TYPE
            )
            am.addAccountExplicitly(account, null, null)
            account.setActive()
            account.setData(id, data)
            am.setAuthToken(account, TOKEN_TYPE, token)
        }

    private fun Account.setActive(shouldClear: Boolean = true) {
        am.apply {
            if (shouldClear) clearActive()
            setUserData(this@setActive, "active", "true")
        }
    }

    private fun Account.setData(id: Int, data: AccountInfo) {
        am.apply {
            setUserData(this@setData, "id", id.toString())
            setUserData(this@setData, "photo", data.photo)
            setUserData(this@setData, "status", data.status)
        }
    }

    private fun clearActive() {
        am.getAccountsByType(ACCOUNT_TYPE).forEach {
            if (am.getUserData(it, "active") == "true")
                am.setUserData(it, "active", "false")
        }
    }

    suspend fun getActive(): AccountInfo? = withContext(Dispatchers.IO) {
        val accounts = am.getAccountsByType(ACCOUNT_TYPE)
        var account: Account? = null
        if (accounts.isNotEmpty()) {
            accounts.forEach {
                if (am.getUserData(it, "active") == "true") {
                    account = it
                    return@forEach
                }
            }
            getAccountInfo(account ?: accounts[0].also { it.setActive(false) })
        }
        null
    }

    private suspend fun getAccountInfo(account: Account): AccountInfo =
        withContext(Dispatchers.IO) {
            val name = account.name.split(" ", limit = 2)
            AccountInfo(
                am.getUserData(account, "id").toInt(),
                name[0],
                name[1],
                am.getUserData(account, "status"),
                am.getUserData(account, "photo")
            )
        }

}
