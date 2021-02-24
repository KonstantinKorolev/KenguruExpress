package com.example.kenguruexpress

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

// Запись токена
class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Если токен был сохранен, добавляем его в запрос
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Token $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}