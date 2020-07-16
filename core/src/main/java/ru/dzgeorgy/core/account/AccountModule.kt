package ru.dzgeorgy.core.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    //Constants
    @Suppress("MemberVisibilityCanBePrivate")
    const val ACCOUNT_TYPE = "ru.dzgeorgy.leaf"
    const val TOKEN_TYPE = "default"

    //Providers
    @Provides
    fun provideActiveUser(am: AccountManager): User? {
        val account = am.getActiveAccount()
        account?.let {
            return User(
                am.getUserData(it, "id").toInt(),
                am.getUserData(it, "name"),
                am.getUserData(it, "status"),
                am.getUserData(it, "photo")
            )
        }
        return null
    }

    @Provides
    internal fun provideActiveUserWithToken(am: AccountManager, user: User?): UserWithToken? =
        user?.let {
            UserWithToken(
                user,
                with(am) {
                    peekAuthToken(getActiveAccount(), TOKEN_TYPE)
                }
            )
        }

    @Provides
    @Singleton
    fun provideAccountManager(@ApplicationContext context: Context): AccountManager =
        AccountManager.get(context)

    //Helper methods
    fun AccountManager.createAccount(id: Int, token: String, data: AccountData) {
        val account = Account(
            "id$id",
            ACCOUNT_TYPE
        )
        addAccountExplicitly(account, null, null)
        setActive(account)
        setData(
            account, data
        )
        setAuthToken(account, TOKEN_TYPE, token)
    }

    private fun AccountManager.getActiveAccount(): Account? {
        val accounts = getAccountsByType(ACCOUNT_TYPE)
        var account: Account? = null
        if (accounts.isNotEmpty()) {
            accounts.forEach {
                if (getUserData(it, "active") == "true") {
                    account = it
                    return@forEach
                }
            }
            return account ?: accounts[0].also { setActive(it, false) }
        }
        return null
    }

    private fun AccountManager.setActive(account: Account, newAccount: Boolean = true) {
        if (newAccount) setUserData(getActiveAccount(), "active", "false")
        setUserData(account, "active", "true")
    }

    private fun AccountManager.setData(account: Account, data: AccountData) {
        setUserData(account, "id", data.id.toString())
        setUserData(account, "name", "${data.firstName} ${data.lastName}")
        setUserData(account, "status", data.status)
        setUserData(account, "photo", data.photo)
    }

}

//Data class for representing existing user in application
data class User(
    val id: Int,
    val name: String,
    val status: String,
    val photo: String
)

//Data class for transferring account data received from server
@JsonClass(generateAdapter = true)
data class AccountData(
    val id: Int,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val status: String,
    @Json(name = "photo_max") val photo: String
) {
    fun toUser() = User(
        id,
        "$firstName $lastName",
        status,
        photo
    )
}

//Data class for representing user with its token for Network module
internal data class UserWithToken(
    val user: User,
    val token: String?
)