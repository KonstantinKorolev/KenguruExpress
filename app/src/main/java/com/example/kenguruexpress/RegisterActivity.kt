package com.example.kenguruexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kenguruexpress.api.UserApi
import com.example.kenguruexpress.fragments.LkFragment
import com.example.kenguruexpress.models.users.RegistrationRequest
import com.example.kenguruexpress.models.users.ReqistrationResponse
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        cancelRegisterBtn.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        initAction()
    }

    private fun initAction() {
        registerBtn.setOnClickListener {
            if (emailRegisterEditText.text.isEmpty()) {
                emailRegisterEditText.error = "Введите Email"
                emailRegisterEditText.requestFocus()
                return@setOnClickListener
            } else if (passwordRegisterEditText.text.isEmpty()) {
                passwordRegisterEditText.error = "Введите пароль"
                passwordRegisterEditText.requestFocus()
                return@setOnClickListener
            } else {
                registration()
            }
        }
    }

    private fun registration() {
        val request = RegistrationRequest()
        request.email = emailRegisterEditText.text.toString().trim()
        request.password = passwordRegisterEditText.text.toString().trim()

        val retrofitSource = RetrofitClient().getRetrofitClient().create(UserApi::class.java)
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
                    Toast.makeText(applicationContext, "Регистрация прошла успешна, вам " +
                            "пришло письмо на электронную почту", Toast.LENGTH_SHORT).show()
                    Log.i("email", response.code().toString())
                    Log.i("email", user.email.toString())
                    Log.i("id", user.id.toString())
                    Log.i("password", user.password.toString())
                }
            }

            override fun onFailure(call: Call<ReqistrationResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}


