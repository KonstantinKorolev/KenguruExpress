package com.example.kenguruexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kenguruexpress.api.GeographyApi
import com.example.kenguruexpress.models.locality.CityLocal
import com.example.kenguruexpress.models.locality.HouseLocal
import com.example.kenguruexpress.models.locality.StreetLocal
import kotlinx.android.synthetic.main.activity_receiving.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DispatchActivity : AppCompatActivity() {
    private var cities = mutableListOf<String>()
    private lateinit var cityAdapter : ArrayAdapter<String>
    private var cityKladr : String = ""

    private var streets = mutableListOf<String>()
    private lateinit var streetAdapter : ArrayAdapter<String>
    private var streetFias : String = ""

    private var houses = mutableListOf<String>()
    private lateinit var housesAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving)

        addresCancelBtn.setOnClickListener {
            onBackPressed()
        }

        cityAuthoComplete.threshold = 1
        streetAuthoComplete.threshold = 1
        houseAuthoComplete.threshold = 1

        cityAuthoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            // Записываем выбранный город
            val selectedItem = parent.getItemAtPosition(position).toString()
            getCityKladr(selectedItem)



        }

        streetAuthoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            // Записываем выбранную улицу
            val selectedStreet = parent.getItemAtPosition(position).toString()
            // передаём название улицы, kladr в функцию
            getStreetFias(cityKladr, selectedStreet)


        }

        // Ввод города и получение ответа сервера с 5 названиями городов
        cityAuthoComplete.addTextChangedListener(object : TextWatcher {
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
        streetAuthoComplete.addTextChangedListener(object : TextWatcher {
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
        houseAuthoComplete.addTextChangedListener(object : TextWatcher {
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

        addresAgreeBtn.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.putExtra("cityDispatch", cityAuthoComplete.text.toString())
            startActivity(i)
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
                        cityAdapter = ArrayAdapter(this@DispatchActivity, android.R.layout.simple_dropdown_item_1line, cities)
                        cityAuthoComplete.setAdapter(cityAdapter)
                        cityAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                        Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getCityKladr(city: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java)
                .getCityLocality(city, 1).enqueue(object : Callback<ArrayList<CityLocal>> {
                    override fun onResponse(call: Call<ArrayList<CityLocal>>, response: Response<ArrayList<CityLocal>>) {
                        val res = response.body()
                        for (item in res!!) {
                            cityKladr = item.kladr.toString()
                            break
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                        Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getStreetFias(kladr: String, street: String) {
        RetrofitClient().getRetrofitClient(this).create(GeographyApi::class.java).getStreetLocality(kladr, street, 2).enqueue(object : Callback<ArrayList<StreetLocal>> {
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
                .getStreetLocality(kladr_id = kladr, term = s, count = 3).enqueue(object : Callback<ArrayList<StreetLocal>> {
                    override fun onResponse(call: Call<ArrayList<StreetLocal>>, response: Response<ArrayList<StreetLocal>>) {
                        val res = response.body()
                        val code = response.code()
                        val cngStreetList = mutableListOf<String>()
                        if (code == 200) {
                            for (item in res!!) {
                                cngStreetList.add(item.street.toString())
                            }
                            streets = cngStreetList
                            streetAdapter = ArrayAdapter(this@DispatchActivity, android.R.layout.simple_dropdown_item_1line, streets)
                            streetAuthoComplete.setAdapter(streetAdapter)
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
                .getHouseLocality(street_fias = fias, term = s, count = 3).enqueue(object : Callback<ArrayList<HouseLocal>> {
                    override fun onResponse(call: Call<ArrayList<HouseLocal>>, response: Response<ArrayList<HouseLocal>>) {
                        val res = response.body()
                        val code = response.code()
                        val cngHouseList = mutableListOf<String>()
                        if (code == 200) {
                            for (item in res!!) {
                                cngHouseList.add(item.house.toString())
                            }
                            houses = cngHouseList
                            housesAdapter = ArrayAdapter(this@DispatchActivity, android.R.layout.simple_dropdown_item_1line, houses)
                            houseAuthoComplete.setAdapter(housesAdapter)
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
