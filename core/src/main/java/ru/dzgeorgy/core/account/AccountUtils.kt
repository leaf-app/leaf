package ru.dzgeorgy.core.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

class AccountUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val ACCOUNT_TYPE = "ru.dzgeorgy.leaf"
        const val TOKEN_TYPE = "default"
    }

    @JsonClass(generateAdapter = true)
    data class AccountInfo(
        val id: Int,
        @Json(name = "first_name") val firstName: String,
        @Json(name = "last_name") val lastName: String,
        val status: String,
        @Json(name = "photo_max") val photo: String
    ) {
        fun getName() = "$firstName $lastName"
    }

    private val am = AccountManager.get(context)

    suspend fun createAccount(id: Int, token: String, data: AccountInfo) =
        withContext(Dispatchers.IO) {
            val account = Account(
                "id$id",
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
            setUserData(this@setData, "name", data.firstName + " " + data.lastName)
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
        val account = getActiveAccount()
        account?.let {
            return@withContext getAccountInfo(it)
        }
        return@withContext null
    }

    private suspend fun getActiveAccount(): Account? = withContext(Dispatchers.IO) {
        val accounts = am.getAccountsByType(ACCOUNT_TYPE)
        var account: Account? = null
        if (accounts.isNotEmpty()) {
            accounts.forEach {
                if (am.getUserData(it, "active") == "true") {
                    account = it
                    return@forEach
                }
            }
            return@withContext account ?: accounts[0].also { it.setActive(false) }
        }
        return@withContext null
    }

    suspend fun getToken(): String? = withContext(Dispatchers.IO) {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
        }
        val account = getActiveAccount()
        account?.let {
            am.peekAuthToken(account, TOKEN_TYPE)
        }
    }

    private suspend fun getAccountInfo(account: Account): AccountInfo =
        withContext(Dispatchers.IO) {
            val name = am.getUserData(account, "name").split(" ", limit = 2)
            AccountInfo(
                am.getUserData(account, "id").toInt(),
                name[0],
                name[1],
                am.getUserData(account, "status"),
                am.getUserData(account, "photo")
            )
        }

}
