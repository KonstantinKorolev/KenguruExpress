package com.example.kenguruexpress


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kenguruexpress.api.UserApi
import com.example.kenguruexpress.models.phone_activation.PhoneActivationRequest
import com.example.kenguruexpress.models.phone_reqistration.PhoneReqistrationRequest
import com.example.kenguruexpress.models.usersMe.changeUserInfResponse
import kotlinx.android.synthetic.main.activity_change_user_data.*
import kotlinx.android.synthetic.main.fragment_users_lk.*
import kotlinx.android.synthetic.main.phone_activation_dialog.*
import kotlinx.android.synthetic.main.phone_activation_dialog.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeUserDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_data)

        confirmPhoneBtn.setOnClickListener {
            if (changeDataPhone.text.isEmpty()) {
                changeDataPhone.error = "Введите номер телефона"
            } else {
                val phone = changeDataPhone.text.toString().trim()
                registerPhone(phone)

                val cDialogView = LayoutInflater.from(this).inflate(R.layout.phone_activation_dialog, null)
                val cBuilder = AlertDialog.Builder(this)
                        .setView(cDialogView)
                        .setTitle("Введите код из СМС")
                val cAlertDialog = cBuilder.show()

                cDialogView.phonact_cancel_btn.setOnClickListener {
                    cAlertDialog.dismiss()
                }

                cDialogView.phonact_enter_btn.setOnClickListener {
                    if (phoneActivationCode.text.isEmpty()) {
                        phoneActivationCode.error = "Введите код"
                        phoneActivationCode.requestFocus()
                    } else {
                        val code = phoneActivationCode.text.toString().trim()
                        // функция подтверждения телефона
                        activationPhone(phone, code)
                        cAlertDialog.dismiss()
                    }
                }
            }
        }

        cancelBtn.setOnClickListener {
            onBackPressed()
        }

        confirmDataChangesBtn.setOnClickListener {
            val firstName = changeDataName.text.toString().trim()
            val lastName = changeDataLastName.text.toString().trim()
            val patronymic = changeDataPatronymic.text.toString().trim()
            if (firstName.isEmpty()) {
                changeDataName.error = "Введите имя"
                changeDataName.requestFocus()
                return@setOnClickListener
            } else if (lastName.isEmpty()) {
                changeDataLastName.error = "Введите фамилию"
                changeDataLastName.requestFocus()
                return@setOnClickListener
            } else if (patronymic.isEmpty()) {
                changeDataPatronymic.error = "Введите отчество"
                changeDataPatronymic.requestFocus()
                return@setOnClickListener
            } else {
                changeNameInfo(firstName, lastName, patronymic)
            }
        }
    }

    private fun registerPhone(phone: String) {
        val request = PhoneReqistrationRequest()
        request.phone = phone
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.reqistrationPhone(request)
                .enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val code = response.code()
                if (code == 204) {
                    Toast.makeText(applicationContext, "Регистрация телефона прошла успешно",
                        Toast.LENGTH_SHORT).show()

                } else if (code == 503) {
                    Toast.makeText(applicationContext, "Сервис временно недоступен",
                            Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun activationPhone(phone: String, code: String) {
        val request = PhoneActivationRequest()
        request.phone = phone
        request.code = code
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
         retrofitSource.activationPhone(request)
                 .enqueue(object : Callback<ResponseBody>{
         override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
             val result = response.code()
             if (result == 204) {
                 Toast.makeText(applicationContext, "Телефон успешно подтверждён",
                                    Toast.LENGTH_SHORT).show()
                 val i = Intent(applicationContext, MainActivity::class.java)
                 i.putExtra("phone", phone)
             } else if (result == 400) {
                 Toast.makeText(applicationContext, "Неверный код",
                                    Toast.LENGTH_SHORT).show()
             }
         }

         override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
             Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
         }

         })
    }

    private fun changeNameInfo(firstName: String, lastName: String, patronymic: String) {
        val retrofitSource = RetrofitClient().getRetrofitClient(this).create(UserApi::class.java)
        retrofitSource.changeInfo(first_name = firstName, last_name = lastName, patronymic = patronymic).enqueue(object : Callback<changeUserInfResponse>{
            override fun onResponse(call: Call<changeUserInfResponse>, response: Response<changeUserInfResponse>) {
                val code = response.code()
                val res = response.body()
                if (code == 200) {
                    val i = Intent(applicationContext, MainActivity::class.java)
                    i.putExtra("first_name", res?.first_name)
                    i.putExtra("last_name", res?.last_name)
                    i.putExtra("patronymic", res?.patronymic)
                    Toast.makeText(applicationContext, "Данные успешно изменены",
                            Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }

            override fun onFailure(call: Call<changeUserInfResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}