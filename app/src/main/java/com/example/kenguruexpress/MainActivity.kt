package com.example.kenguruexpress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kenguruexpress.fragments.DispatchFragment
import com.example.kenguruexpress.fragments.PurseFragment
import com.example.kenguruexpress.fragments.UsersLkFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val purseFragment = PurseFragment()
    private val dispatchFragment = DispatchFragment()
    private val usersLkFragment = UsersLkFragment()

    var phone: String? = null
    var userName: String? = null
    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        replaceFragment(purseFragment)

        val intent = intent
        val userLoginBool = intent.getBooleanExtra("userLogging", false)
        val userEmail = intent.getStringExtra("email").toString()
        phone = intent.getStringExtra("phone")

        val firstName = intent.getStringExtra("first_name")
        val lastName = intent.getStringExtra("last_name")
        val patronymic = intent.getStringExtra("patronymic")
        userName = "$firstName" + "$lastName" + "$patronymic"

        if (phone == null) {
            phone = "-"
        }

        if (firstName == null && lastName == null && patronymic == null) {
            userName = "-"
        }

        saveData(phone!!, userName!!)

        if (userLoginBool) {
            bottom_navigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_purse -> replaceFragment(purseFragment)
                    R.id.ic_dispatch -> replaceFragment(dispatchFragment)
                    R.id.ic_lka -> replaceFragment(newInstanceLk(userEmail, phone = pref?.getString("phone", phone)!!,
                            userName = pref?.getString("username", userName)!!))
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) { // Функция перехода с одного фрагмента на другой с помощью, bottom bar'a
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    fun newInstanceLk(email: String, phone:String, userName: String): UsersLkFragment {
        val fr = usersLkFragment
        val args = Bundle()
        args.putString("email", email)
        args.putString("phone", phone)
        args.putString("userName", userName)
        fr.arguments = args
        return fr
    }

    fun saveData(res: String, userName: String) {
        val editor = pref?.edit()
        editor?.putString("phone", res)
        editor?.putString("username", userName)
        editor?.apply()
    }
}