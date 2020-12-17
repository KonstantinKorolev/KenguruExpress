package com.example.kenguruexpress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenguruexpress.adapters.AddressAdapter
import com.example.kenguruexpress.api.AddressBookApi
import com.example.kenguruexpress.db.DbIdClass
import com.example.kenguruexpress.db.DbIdManager
import com.example.kenguruexpress.models.contacts.PostContactsRequest
import com.example.kenguruexpress.models.contacts.PostContactsResponse
import com.example.kenguruexpress.models.contacts.getContactsResponse
import kotlinx.android.synthetic.main.activity_address_show.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressActivityShow : AppCompatActivity() {

    private val myBdManager = DbIdManager(this)

    var id: Int? = null

    var adapter: AddressAdapter? = null
    var contactList: ArrayList<ListItemAddress> = arrayListOf()

    var pref: SharedPreferences? = null

    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_show)

        val i = intent
        var email = i.getStringExtra("email")

        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        if (email != null) {
            saveEmail(email)
        } else {
            email = pref?.getString("email", null)
        }

        // получаем все данные для функции postContact
        val cityId = i.getIntExtra("cityId", -1)
        val street = i.getStringExtra("street")
        val house = i.getStringExtra("house")
        val flat = i.getStringExtra("flat")
        val index = i.getStringExtra("index")
        val surname = i.getStringExtra("surname")
        val name = i.getStringExtra("name")
        val patronymic = i.getStringExtra("patronymic")
        val company = i.getStringExtra("company")
        val phone = i.getStringExtra("phone")
        val phone_extension = i.getStringExtra("phone_extension")
        val comment = i.getStringExtra("comment")

        userId = getIdInDb(email!!)

        if (userId != null) {
            getContacts(userId!!)
        }

        addrFloatingButton.setOnClickListener {
            val intent = Intent(applicationContext, AddOrChangeAddressActivity::class.java)
            startActivity(intent)
        }

        if (street != null && house != null && flat != null && index != null && surname !=
                null && name != null && patronymic != null && company != null && phone != null &&
                phone_extension != null && comment != null) {
            postContact(cityId, street, house, flat, index, surname, name, patronymic, company,
                    phone, phone_extension, comment, userId!!)
        }

        if (contactList.isEmpty()) {
            return
        } else {
            addrBookEmpty.visibility = View.INVISIBLE
            rcViewAddr.hasFixedSize()
            rcViewAddr.layoutManager = LinearLayoutManager(this)
            adapter = AddressAdapter(contactList, this)
            rcViewAddr.adapter = adapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myBdManager.closeDb()
    }

    private fun getIdInDb(email: String): Int {
        var id = 0

        myBdManager.openDb()
        val selection = "${DbIdClass.COLUMN_NAME_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        val dataList = myBdManager.readDbData(selection, selectionArgs)

        for (item in dataList) {
            id = item.id
        }
        return id
    }

    private fun postContact(locality: Int, street: String, house: String, flat: String,
                            index: String, surname: String, name: String, patronymic: String,
                            company: String, phone: String, phone_extension: String, comment: String, profile_id: Int) {
        val request = PostContactsRequest()
        request.locality = locality
        request.street = street
        request.house = house
        request.flat = flat
        request.index_number = index
        request.surname = surname
        request.name = name
        request.patronymic = patronymic
        request.company = company
        request.phone = phone
        request.phone_extension = phone_extension
        request.comment = comment
        RetrofitClient().getRetrofitClient(this).create(AddressBookApi::class.java)
                .postContacts(profile_id.toString(), request).enqueue(object : Callback<PostContactsResponse> {
                    override fun onResponse(
                            call: Call<PostContactsResponse>,
                            response: Response<PostContactsResponse>
                    ) {
                        val code = response.code()
                        val res = response.body()
                        if (code == 201) {
                            if (res != null) {
                                contactList.add(ListItemAddress(res.id!!.toString(), res.locality!!, res.street!!,
                                        res.house!!, res.flat!!, res.index_number!!, res.surname!!, res.name!!,
                                        res.patronymic!!, res.company!!, res.phone!!, res.phone_extension!!, res.comment!!))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PostContactsResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getContacts(profile_id: Int) {
        RetrofitClient().getRetrofitClient(this).create(AddressBookApi::class.java)
                .getUserContacts(profile_id.toString(), 1, 10).enqueue(object : Callback<ArrayList<getContactsResponse>>{
                    override fun onResponse(call: Call<ArrayList<getContactsResponse>>, response: Response<ArrayList<getContactsResponse>>) {
                        val code = response.code()
                        val res = response.body()
                        if (code == 200) {
                            if (res != null) {
                                for (item in res) {
                                    contactList.add(ListItemAddress(item.results?.id.toString(),
                                            item.results?.locality?.id!!, item.results?.street!!,
                                            item.results?.house!!, item.results?.flat!!,
                                            item.results?.index_number!!, item.results?.surname!!,
                                            item.results?.name!!, item.results?.patronymic!!,
                                            item.results?.company!!, item.results?.phone!!,
                                            item.results?.phone_extension!!, item.results?.comment!!))
                                }
                            } else {
                                return
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<getContactsResponse>>, t: Throwable) {
                        Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                    }

                })

    }

    private fun saveEmail(email: String) {
        val editor = pref?.edit()
        editor?.putString("email", email)
        editor?.apply()
    }

    private fun saveData(cityId: Int, street: String, house: String, flat: String, index: String,
                         surname: String, name: String, patronymic: String, company: String,
                         phone: String, phone_extension: String, comment: String) {
        val editor = pref?.edit()
        editor?.putInt("cityId", cityId)
        editor?.putString("street", street)
        editor?.putString("house", house)
        editor?.putString("flat", flat)
        editor?.putString("index", index)
        editor?.putString("surname", surname)
        editor?.putString("name", name)
        editor?.putString("patronymic", patronymic)
        editor?.putString("company", company)
        editor?.putString("phone", phone)
        editor?.putString("phone_extension", phone_extension)
        editor?.putString("comment", comment)
        editor?.apply()
    }

}