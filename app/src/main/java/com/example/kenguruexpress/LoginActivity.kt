package com.example.kenguruexpress

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.kenguruexpress.api.UserApi
import com.example.kenguruexpress.models.email_activation.emailActivationRequest
import com.example.kenguruexpress.models.login.LoginRequest
import com.example.kenguruexpress.models.login.LoginResponse
import com.example.kenguruexpress.models.resend_email.ResendRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.resend_email_layout.*
import kotlinx.android.synthetic.main.resend_email_layout.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager : SessionManager

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        // Проверяем, сохранены ли данные о пользователе
        if (pref?.getString("email", null) != null
                && pref?.getString("password", null) != null) {
            login(pref?.getString("email", null)!!,
                    pref?.getString("password", null)!!, false)
        }

        toRegisterBtn.setOnClickListener {  // Переход к RegisterActivity
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        loginBtn.setOnClickListener { // Если пользователь нажмёт на кнопку "Войти"
            val email = emailLoginEditText.text.toString().trim()
            val password = passwordLoginEditText.text.toString().trim()
            if (email.isEmpty()) {
                emailLoginEditText.error = "Введите Email"
                emailLoginEditText.requestFocus()
            } else if (password.isEmpty()) {
                passwordLoginEditText.error = "Введите пароль"
                passwordLoginEditText.requestFocus()
            } else {
                if (checkBox.isActivated) {
                    login(email, password, true)
                    finish()
                } else {
                    login(email, password, false) // Вызовется функция login отвечающая за процесс входа в аккаунт
                    finish()
                }
            }
        }

        acceptEmailBtn.setOnClickListener {
            // Вызывает метод активации почты пользователя, программно изменяются аргументы для подтверждения
            activationEmail("MjI", "5mg-91ccb7a636df5c86c9a7")
        }

        resendEmail.setOnClickListener { // При нажатии кнопки показывет диалог с введением Email
            val lkDialogView = LayoutInflater.from(this).inflate(R.layout.resend_email_layout, null)
            val lkBuilder = AlertDialog.Builder(this)
                .setView(lkDialogView)
                .setTitle("Введите Email")
            val lkAlertDialog = lkBuilder.show()

            lkDialogView.resendEmail_enter_btn.setOnClickListener { // Если нажата кнопка Ввод, то...
                val email = lkDialogView.resendEmailText.text.toString().trim()
                if (email.isEmpty()) { // Если поле email пусто, то выводим ошибку в поле
                    resendEmailText.error = "Введите Email"
                    resendEmailText.requestFocus()
                } else {
                    resendEmail(email) // Вызывается функция отвечающая за Пересылку письма пользователю
                    lkAlertDialog.dismiss()
                }
            }

            lkDialogView.resendEmail_cancel_btn.setOnClickListener {
                lkAlertDialog.dismiss()
            }
        }
    }

    private fun resendEmail(email: String) {
        val request = ResendRequest()
        request.email = email
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.resendEmail(request).enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val code = response.code()
                Log.i("code", code.toString())
                if (code == 204) {
                    Toast.makeText(applicationContext, "Вам повторно выслано письмо на почту",
                        Toast.LENGTH_SHORT).show()
                } else if (code == 400) {
                    Toast.makeText(applicationContext, "Почта уже активирована",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun login(email: String, password: String, rememberMe: Boolean) { // функция для входа в личный кабинет
        var userLoginBool = false
        val request = LoginRequest()
        request.email = email
        request.password = password
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.login(request).enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val res = response.body()
                val code = response.code()
                if (code == 400) {
                    Toast.makeText(applicationContext, "Проверьте правильно ли введены пароль или логин",
                            Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(i)
                }
                if (res == null) {
                    Toast.makeText(applicationContext, response.errorBody().toString(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    if (rememberMe) {
                        saveEmailAndPass(email, password)
                    }
                    userLoginBool = true
                    val i = Intent(applicationContext, MainActivity::class.java)
                    i.putExtra("userLogging", userLoginBool)
                    i.putExtra("email", email)

                    // Сохраняем токен
                    sessionManager.saveAuthToken(res.auth_token.toString())
                    Log.i("token", res.auth_token.toString())

                    startActivity(i)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun activationEmail(uid: String, token: String) {
        val request = emailActivationRequest()
        request.uid = uid
        request.token = token
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.activationEmail(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when (response.code()) {
                    204 -> Toast.makeText(applicationContext, "Почта успешно активирована",
                            Toast.LENGTH_SHORT).show()
                    403 -> Toast.makeText(applicationContext, "Почта уже активирована",
                            Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(applicationContext, "Введены неверные данные",
                            Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveEmailAndPass(email: String, password: String) {
        val editor = pref?.edit()
        editor?.putString("email", email)
        editor?.putString("password", password)
        editor?.apply()
    }
}