package com.example.kenguruexpress

import android.content.Context
import android.content.SharedPreferences

// Диспетчер сеансов для сохранения и извлечения данных из SharedPreferences
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),
            Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    // Функция сохранения токена
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    // Функция для получения токена
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    // Функция для удаления токена
    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }
}