package com.example.kenguruexpress

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_change_user_data.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Включение ночной темы
        NightThemeBtn.setOnClickListener {
            NightTheme()
        }

        // когда пользователь нажмёт "Назад"
        cancelBtnSetting.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    private fun NightTheme() {
        // Сохранение значения темы для будущего использования темы при запуске приложения
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs",  0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightTheme", false)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            NightThemeBtn.text = R.string.EnableLightTheme.toString()
            // дополнить изменения цвета
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            NightThemeBtn.text = R.string.nightThemeBtn.toString()
        }

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefsEdit.putBoolean("NightTheme", false)
            sharedPrefsEdit.apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefsEdit.putBoolean("NightTheme", true)
            sharedPrefsEdit.apply()
        }
    }
}