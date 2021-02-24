package com.example.kenguruexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kenguruexpress.api.UserApi
import com.example.kenguruexpress.db.DbIdManager
import com.example.kenguruexpress.models.users.RegistrationRequest
import com.example.kenguruexpress.models.users.ReqistrationResponse
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    val myDbManger = DbIdManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        cancelRegisterBtn.setOnClickListener {
            onBackPressed()
            finish()
        }
        initAction()
    }

    // отслеживание действий пользователя
    private fun initAction() {
        registerBtn.setOnClickListener {
            val pattern = """\w[a-z]""".toRegex()
            if (emailRegisterEditText.text.isEmpty()) {
                emailRegisterEditText.error = "Введите Email"
                emailRegisterEditText.requestFocus()
                return@setOnClickListener
            } else if (passwordRegisterEditText.text.isEmpty()) {
                passwordRegisterEditText.error = "Введите пароль"
                passwordRegisterEditText.requestFocus()
                return@setOnClickListener
            } else if (passwordRegisterEditText.text.length < 8){
                passwordRegisterEditText.error = "Пароль должен состоять минимум из 8 символов"
                passwordRegisterEditText.requestFocus()
            } else if (!pattern.containsMatchIn(passwordRegisterEditText.text)) {
                passwordRegisterEditText.error = "Пароль должен состоять из одной a-z и A-Z"
                passwordRegisterEditText.requestFocus()
            } else {
                // Вызывает метод регистрации почты пользователя
                registration()
            }
        }
    }

    // регистрация
    private fun registration() {
        val request = RegistrationRequest()
        request.email = emailRegisterEditText.text.toString().trim()
        request.password = passwordRegisterEditText.text.toString().trim()

        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.createUser(request).enqueue(object : Callback<ReqistrationResponse>{
            override fun onResponse(
                    call: Call<ReqistrationResponse>,
                    response: Response<ReqistrationResponse>
            ) {
                val user = response.body()
                if (user == null) {
                    Toast.makeText(applicationContext, response.errorBody().toString(),
                            Toast.LENGTH_SHORT).show()
                }
                if (user != null) {
                    Toast.makeText(applicationContext, "Регистрация прошла успешна", Toast.LENGTH_SHORT).show()
                    // Сохранение email и id в базу данных
                    saveToDb(user.email.toString(), user.id!!)
                    Log.i("email", response.code().toString())
                    Log.i("email", user.email.toString())
                    Log.i("id", user.id.toString())
                    Log.i("password", user.password.toString())
                }
            }

            override fun onFailure(call: Call<ReqistrationResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    // сохранение в базу данных
    private fun saveToDb(email: String, id: Int) {
        myDbManger.openDb()
        myDbManger.insertToDb(email, id)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManger.closeDb()
    }
}


