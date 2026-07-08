package com.dicoding.kisahrasa.core.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SecretsManager @Inject constructor(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveSecret(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getSecret(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clearSecret(key: String) {
        sharedPreferences.edit { remove(key) }
    }
}
