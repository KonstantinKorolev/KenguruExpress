package com.example.kenguruexpress


import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.kenguruexpress.fragments.DispatchFragment
import com.example.kenguruexpress.fragments.LkFragment
import com.example.kenguruexpress.fragments.PurseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cargo_dialog.view.*
import kotlinx.android.synthetic.main.fragment_purse.*

class MainActivity : AppCompatActivity() {

    private val purseFragment = PurseFragment()
    private val dispatchFragment = DispatchFragment()
    private val lkFragment = LkFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(purseFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_purse -> replaceFragment(purseFragment)
                R.id.ic_dispatch -> replaceFragment(dispatchFragment)
                R.id.ic_lka -> replaceFragment(lkFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) { // Функция перехода с одного фрагмента на другой с помощью, bottom bar'a
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}