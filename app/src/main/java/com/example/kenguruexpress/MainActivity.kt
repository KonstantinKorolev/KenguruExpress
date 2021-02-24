package com.example.kenguruexpress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kenguruexpress.db.DbIdManager
import com.example.kenguruexpress.fragments.DispatchFragment
import com.example.kenguruexpress.fragments.PurseFragment
import com.example.kenguruexpress.fragments.UsersLkFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myBdManager = DbIdManager(this)

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

        // отправляем email в AddressActivityShow
        val i = Intent(applicationContext, AddressActivityShow::class.java)
        i.putExtra("email", userEmail)

        val firstName = intent.getStringExtra("first_name")
        val lastName = intent.getStringExtra("last_name")
        val patronymic = intent.getStringExtra("patronymic")

        if (firstName == null && lastName == null && patronymic == null
                && pref?.getString("username", userName) == null) {
            userName = "-"
            saveDataUserName(userName!!)
        } else if (firstName != null && lastName != null && patronymic != null
                && pref?.getString("username", userName) == "-") {
            saveDataUserName(userName!!)
        } else if (firstName != null && lastName != null && patronymic != null
                && pref?.getString("username", userName) != "-") {
            saveDataUserName(userName!!)
        }

        if (phone == null && pref?.getString("phone", phone) == null) {
            phone = "-"
            saveDataPhone(phone!!)
        } else if (phone != null && pref?.getString("phone", phone) == "-") {
            saveDataPhone(phone!!)
        } else if (phone != null && pref?.getString("phone", phone) != "-") {
            saveDataPhone(phone!!)
        }

        // переключение между фрагментами путём нажатия на BottomNavBar
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

    // Функция перехода с одного фрагмента на другой с помощью, bottom bar'a
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    // функция для передачи в другой фрагмент
    private fun newInstanceLk(email: String, phone:String, userName: String): UsersLkFragment {
        val fr = usersLkFragment
        val args = Bundle()
        args.putString("email", email)
        args.putString("phone", phone)
        args.putString("userName", userName)
        fr.arguments = args
        return fr
    }

    private fun saveDataUserName(userName: String) {
        val editor = pref?.edit()
        editor?.putString("username", userName)
        editor?.apply()
    }

    private fun saveDataPhone(phone: String) {
        val editor = pref?.edit()
        editor?.putString("phone", phone)
        editor?.apply()
    }

    override fun onResume() {
        super.onResume()
        myBdManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myBdManager.closeDb()
    }
}