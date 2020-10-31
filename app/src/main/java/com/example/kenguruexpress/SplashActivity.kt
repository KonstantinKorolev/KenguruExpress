package com.example.kenguruexpress

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScreenMove() // // Функция для показа splash screen и progress bar
    }

    private fun splashScreenMove() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
            {
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)

                progressBar.max = 10
                val currentProgress = 10

                ObjectAnimator.ofInt(progressBar, "progress", currentProgress) // Анимация для progress bar
                    .setDuration(splashScreenDuration)
                    .start()
            },
            splashScreenDuration
        )
        Handler().postDelayed(
            {
                routeToMainActivity()
            }, splashScreenDuration) // Используем splashScreenDuration для перехода в момент конца анимации progress bar'a
    }

    private fun getSplashScreenDuration(): Long { // Функция для вычисления, через сколько секунд нужно переходить на mainActivity
        val pref = getPreferences(Context.MODE_PRIVATE)
        val prefKeyFirstLaunch = "pref_first_launch"

        return when(pref.getBoolean(prefKeyFirstLaunch, true)) {
            true -> {
                // Первый запуск приложение, ставим таймер 3с
                pref.edit().putBoolean(prefKeyFirstLaunch, false).apply()
                3000
            }
            false -> {
                // Не первый запуск приложения, ставим таймер на 1с
                1000
            }
        }
    }
    private fun routeToMainActivity() { // Функция для перехода на mainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}