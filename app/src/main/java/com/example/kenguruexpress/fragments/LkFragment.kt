package com.example.kenguruexpress.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kenguruexpress.MainActivity
import com.example.kenguruexpress.R
import com.example.kenguruexpress.RegisterActivity
import com.example.kenguruexpress.RetrofitClient
import com.example.kenguruexpress.api.UserApi
import com.example.kenguruexpress.models.resend_activation.ResendRequest
import kotlinx.android.synthetic.main.dispatch_dialog.view.*
import kotlinx.android.synthetic.main.fragment_dispatch.*
import kotlinx.android.synthetic.main.fragment_lk.*
import kotlinx.android.synthetic.main.resend_email_layout.*
import kotlinx.android.synthetic.main.resend_email_layout.resendEmail_enter_btn
import kotlinx.android.synthetic.main.resend_email_layout.view.*
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toRegisterBtn.setOnClickListener {  // Переход к RegisterActivity
            val i = Intent(context, RegisterActivity::class.java)
            startActivity(i)
        }

        resendEmail.setOnClickListener { // При нажатии кнопки показывет диалог с введением Email
            val lkDialogView = LayoutInflater.from(context).inflate(R.layout.resend_email_layout, null)
            val lkBuilder = AlertDialog.Builder(context)
                    .setView(lkDialogView)
                    .setTitle("Введите Email")
            val lkAlertDialog = lkBuilder.show()

            lkDialogView.resendEmail_enter_btn.setOnClickListener {
                val email = lkDialogView.resendEmailText.text.toString().trim()
                if (email.isEmpty()) {
                    resendEmailText.error = "Введите Email"
                    resendEmailText.requestFocus()
                } else {
                    resendEmail(email)
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
        val retrofitSource = RetrofitClient().getRetrofitClient().create(UserApi::class.java)
        retrofitSource.resendEmail(request).enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val code = response.code()
                Log.i("code", code.toString())
                if (code == 204) {
                    Toast.makeText(context, "Вам повторно выслано письмо на почту",
                            Toast.LENGTH_SHORT).show()
                } else if (code == 400) {
                    Toast.makeText(context, "Почта уже активирована",
                            Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}