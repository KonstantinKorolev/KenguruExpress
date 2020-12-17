package com.example.kenguruexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kenguruexpress.api.AddressBookApi
import com.example.kenguruexpress.api.GeographyApi
import com.example.kenguruexpress.models.contacts.PostContactsRequest
import com.example.kenguruexpress.models.contacts.PostContactsResponse
import com.example.kenguruexpress.models.locality.CityLocal
import com.example.kenguruexpress.models.locality.HouseLocal
import com.example.kenguruexpress.models.locality.StreetLocal
import kotlinx.android.synthetic.main.activity_add_or_change_address.*
import kotlinx.android.synthetic.main.activity_add_or_change_address.view.*
import kotlinx.android.synthetic.main.fragment_purse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AddOrChangeAddressActivity : AppCompatActivity() {

    private var cities = mutableListOf<String>()
    private lateinit var cityAdapter : ArrayAdapter<String>
    private var cityKladr : String = ""
    private var cityId = 0

    private var streets = mutableListOf<String>()
    private lateinit var streetAdapter : ArrayAdapter<String>
    private var streetFias : String = ""

    private var houses = mutableListOf<String>()
    private lateinit var housesAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_change_address)

        cityAuthoCompleteAddr.threshold = 1
        streetAuthoCompleteAddr.threshold = 1
        houseAuthoCompleteAddr.threshold = 1

        cityAuthoCompleteAddr.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            // Записываем выбранный город
            val selectedItem = parent.getItemAtPosition(position).toString()
            getCityKladr(selectedItem)



        }

        streetAuthoCompleteAddr.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            // Записываем выбранную улицу
            val selectedStreet = parent.getItemAtPosition(position).toString()
            // передаём название улицы, kladr в функцию
            getStreetFias(cityKladr, selectedStreet)


        }

        // Ввод города и получение ответа сервера с 5 названиями городов
        cityAuthoCompleteAddr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                retrieveCityData(s.toString())
            }
        })

        // Ввод названия улицы и получение ответа сервера с названиями улиц
        streetAuthoCompleteAddr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                retrieveStreetData(cityKladr, s.toString())
            }

        })

        // Ввод номера дома и получение ответа сервера с номерами домов
        houseAuthoCompleteAddr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                retrieveHouseData(streetFias, s.toString())
            }

        })

        addresCancelBtnAddr.setOnClickListener {
            val i = Intent(applicationContext, AddressActivityShow::class.java)
            startActivity(i)
            finish()
        }

        addresAgreeBtnAddr.setOnClickListener {
            // Если пользователь введёт данные о контактах и намжёт принять
            if (cityAuthoCompleteAddr.text.isEmpty()) {
                cityAuthoCompleteAddr.error = "Введите город"
                cityAuthoCompleteAddr.requestFocus()
            } else if (streetAuthoCompleteAddr.text.isEmpty()) {
                streetAuthoCompleteAddr.error = "Введите улицу"
                streetAuthoCompleteAddr.requestFocus()
            } else if (houseAuthoCompleteAddr.text.isEmpty()) {
                houseAuthoCompleteAddr.error = "Введите номер дома"
                houseAuthoCompleteAddr.requestFocus()
            } else if (editFlat.text.isEmpty()) {
                editFlat.error = "Введите квартирку(офис)"
                editFlat.requestFocus()
            } else if (editIndex.text.isEmpty()) {
                editIndex.error = "Введите индекс"
                editFlat.requestFocus()
            } else if (editSurname.text.isEmpty()) {
                editSurname.error = "Введите фамилию"
                editSurname.requestFocus()
            } else if (editName.text.isEmpty()) {
                editName.error = "Введите имя"
                editName.requestFocus()
            } else if (editPatronymic.text.isEmpty()) {
                editPatronymic.error = "Введите отчество"
                editPatronymic.requestFocus()
            } else if (editCompany.text.isEmpty()) {
                editCompany.error = "Введите название компании"
                editCompany.requestFocus()
            } else if (editPhone.text.isEmpty()) {
                editPhone.error = "Введите телефон"
                editPhone.requestFocus()
            } else if (editDopPhone.text.isEmpty()) {
                editDopPhone.error = "Введите доп.телефон"
                editDopPhone.requestFocus()
            } else if (editComment.text.isEmpty()) {
                editComment.error = "Введите комментарий"
                editComment.requestFocus()
            } else {
                val street = streetAuthoCompleteAddr.text.toString()
                val house = houseAuthoCompleteAddr.text.toString()
                val flat = editFlat.text.toString()
                val index = editIndex.text.toString()
                val surname = editSurname.text.toString()
                val name = editName.text.toString()
                val patronymic = editPatronymic.text.toString()
                val company = editCompany.text.toString()
                val phone = editPhone.text.toString()
                val phone_extension = editDopPhone.text.toString()
                val comment = editComment.text.toString()
                val intent = Intent(applicationContext, AddressActivityShow::class.java)
                intent.putExtra("cityId", cityId)
                intent.putExtra("street", street)
                intent.putExtra("house", house)
                intent.putExtra("flat", flat)
                intent.putExtra("index", index)
                intent.putExtra("surname", surname)
                intent.putExtra("name", name)
                intent.putExtra("patronymic", patronymic)
                intent.putExtra("company", company)
                intent.putExtra("phone", phone)
                intent.putExtra("phone_extension", phone_extension)
                intent.putExtra("comment", comment)
                startActivity(intent)
            }
        }
    }

    private fun retrieveCityData(s: String) {

        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java)
            .getCityLocality(s, 3).enqueue(object : Callback<ArrayList<CityLocal>> {
                override fun onResponse(call: Call<ArrayList<CityLocal>>, response: Response<ArrayList<CityLocal>>) {
                    val res = response.body()
                    val cngCitiesList = mutableListOf<String>()
                    for (item in res!!) {
                        cngCitiesList.add(item.locality.toString())
                    }
                    cities = cngCitiesList
                    cityAdapter = ArrayAdapter(this@AddOrChangeAddressActivity, android.R.layout.simple_dropdown_item_1line, cities)
                    cityAuthoCompleteAddr.setAdapter(cityAdapter)
                    cityAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                    Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getCityKladr(city: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java)
            .getCityLocality(city, 1).enqueue(object : Callback<ArrayList<CityLocal>>{
                override fun onResponse(call: Call<ArrayList<CityLocal>>, response: Response<ArrayList<CityLocal>>) {
                    val res = response.body()
                    for (item in res!!) {
                        cityKladr = item.kladr.toString()
                        cityId = item.id!!
                        break
                    }
                }

                override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                    Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getStreetFias(kladr: String, street: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java).getStreetLocality(kladr, street, 2).enqueue(object : Callback<ArrayList<StreetLocal>>{
            override fun onResponse(call: Call<ArrayList<StreetLocal>>, response: Response<ArrayList<StreetLocal>>) {
                val res = response.body()
                for (item in res!!) {
                    streetFias = item.street_fias_id.toString()
                    break
                }
            }

            override fun onFailure(call: Call<ArrayList<StreetLocal>>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun retrieveStreetData(kladr: String, s: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java)
            .getStreetLocality(kladr_id = kladr, term = s, count = 3).enqueue(object : Callback<ArrayList<StreetLocal>>{
                override fun onResponse(call: Call<ArrayList<StreetLocal>>, response: Response<ArrayList<StreetLocal>>) {
                    val res = response.body()
                    val code = response.code()
                    val cngStreetList = mutableListOf<String>()
                    if (code == 200) {
                        for (item in res!!) {
                            cngStreetList.add(item.street.toString())
                        }
                        streets = cngStreetList
                        streetAdapter = ArrayAdapter(this@AddOrChangeAddressActivity, android.R.layout.simple_dropdown_item_1line, streets)
                        streetAuthoCompleteAddr.setAdapter(streetAdapter)
                        streetAdapter.notifyDataSetChanged()
                    } else if (code == 503) {
                        Toast.makeText(applicationContext, "Сервис автозаполнения временно недоступен",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<StreetLocal>>, t: Throwable) {
                    Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun retrieveHouseData(fias: String, s: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java)
            .getHouseLocality(street_fias = fias, term = s, count = 3).enqueue(object : Callback<ArrayList<HouseLocal>>{
                override fun onResponse(call: Call<ArrayList<HouseLocal>>, response: Response<ArrayList<HouseLocal>>) {
                    val res = response.body()
                    val code = response.code()
                    val cngHouseList = mutableListOf<String>()
                    if (code == 200) {
                        for (item in res!!) {
                            cngHouseList.add(item.house.toString())
                        }
                        houses = cngHouseList
                        housesAdapter = ArrayAdapter(this@AddOrChangeAddressActivity, android.R.layout.simple_dropdown_item_1line, houses)
                        houseAuthoCompleteAddr.setAdapter(housesAdapter)
                        housesAdapter.notifyDataSetChanged()
                    } else if (code == 503) {
                        Toast.makeText(applicationContext, "Сервис автозаполнения временно недоступен",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<HouseLocal>>, t: Throwable) {
                    Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                }

            })
    }



}