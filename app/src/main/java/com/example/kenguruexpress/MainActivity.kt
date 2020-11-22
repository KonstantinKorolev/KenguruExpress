package com.example.kenguruexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kenguruexpress.fragments.DispatchFragment
import com.example.kenguruexpress.fragments.PurseFragment
import com.example.kenguruexpress.fragments.UsersLkFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_purse.*

class MainActivity : AppCompatActivity() {

    private val purseFragment = PurseFragment()
    private val dispatchFragment = DispatchFragment()
    private val usersLkFragment = UsersLkFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(purseFragment)

        val intent = intent
        val userLoginBool = intent.getBooleanExtra("userLogging", false)
        val userEmail = intent.getStringExtra("email")

        if (!userLoginBool) {
            bottom_navigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_purse -> replaceFragment(purseFragment)
                    R.id.ic_dispatch -> replaceFragment(dispatchFragment)
                    R.id.ic_lka -> startActivity(Intent(this, LoginActivity::class.java))
                }
                true
            }
        } else if (userLoginBool) {
            bottom_navigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_purse -> replaceFragment(purseFragment)
                    R.id.ic_dispatch -> replaceFragment(dispatchFragment)
                    R.id.ic_lka -> replaceFragment(newInstance(userEmail.toString()))
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

    fun newInstance(email: String): UsersLkFragment {
        val fr = usersLkFragment
        val args = Bundle()
        args.putString("email", email)
        fr.arguments = args
        return fr
    }
}