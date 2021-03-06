package com.example.kenguruexpress.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kenguruexpress.R
import com.example.kenguruexpress.RetrofitClient
import com.example.kenguruexpress.ShowTariffsActivity
import com.example.kenguruexpress.api.GeographyApi
import com.example.kenguruexpress.api.ProductApi
import com.example.kenguruexpress.models.departure.postDepartureRequest
import com.example.kenguruexpress.models.departure.postDepartureResponse
import com.example.kenguruexpress.models.locality.CityLocal
import com.example.kenguruexpress.models.products.CreateProductRequest
import com.example.kenguruexpress.models.products.CreateProductResponse
import kotlinx.android.synthetic.main.cargo_dialog.view.*
import kotlinx.android.synthetic.main.documents_dialog.view.*
import kotlinx.android.synthetic.main.fragment_purse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var cities = mutableListOf<String>()
    private lateinit var cityAdapter : ArrayAdapter<String>

    var cityIdReceiving : Int? = null
    var cityIdDispatch : Int? = null

    var cargoWidth : String = ""
    var cargoWeight : String = ""
    var cargoHeight : String = ""
    var cargoLenght : String = ""
    var similarGoods : String = ""

    var pickup = false
    var delivery = false

    var documentsWeight : String = ""

    var cargoesList: ArrayList<Int> = arrayListOf()

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
        return inflater.inflate(R.layout.fragment_purse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityDispatchCompleteTextView.threshold = 1
        cityReceivingCompleteTextView.threshold = 1

        cityDispatchCompleteTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                retrieveCityData(s.toString(), context!!, true)
            }

        })

        cityReceivingCompleteTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                retrieveCityData(s.toString(), context!!, false)
            }

        })


        // Нажать на кнопку рассчитать, чтобы появилось диалоговое окно
        countBtn.setOnClickListener {
            if (cityDispatchCompleteTextView.text.isEmpty()) {
                cityDispatchCompleteTextView.error = "Введите город отправки"
                cityDispatchCompleteTextView.requestFocus()
            } else if (cityReceivingCompleteTextView.text.isEmpty()) {
                cityReceivingCompleteTextView.error = "Введите город получения"
                cityReceivingCompleteTextView.requestFocus()

            } else if (similarGoodsAdd.text.isEmpty()) {
                similarGoodsAdd.error = "Введите количество товаров"
                similarGoodsAdd.requestFocus()
            } else if (similarGoodsAdd.text.toString() == "0" || similarGoodsAdd.text.isEmpty()) {
                similarGoodsAdd.error = "Товаров может быть минимум 1"
                similarGoodsAdd.requestFocus()
            } else {
                getIdData(cityReceivingCompleteTextView.text.toString(), context!!, true)
                getIdData(cityDispatchCompleteTextView.text.toString(), context!!, false)
                similarGoods = similarGoodsAdd.text.toString()
                cargoesList.add(similarGoods.toInt())
                showDialog()
            }
        }

        switchToDoor.setOnClickListener {
            if (switchToDoor.isChecked) {
                // Если переключатель включен
                switchToDoor2.isClickable = false
                switchToDoor2.isEnabled = false
                pickup = true
            } else {
                switchToDoor2.isClickable = true
                switchToDoor2.isEnabled = true
                delivery = true
            }
        }

        switchToDoor2.setOnClickListener {
            if (switchToDoor2.isChecked) {
                // Если переключатель включен
                switchToDoor.isClickable = false
                switchToDoor.isEnabled = false
            } else {
                switchToDoor.isClickable = true
                switchToDoor.isEnabled = true
            }
        }
    }

    // получение данных о городе
    private fun retrieveCityData(s: String, context: Context, isAdapter: Boolean) {

        RetrofitClient().getRetrofitClient(context).create(GeographyApi::class.java)
                .getCityLocality(s, 3).enqueue(object : Callback<ArrayList<CityLocal>> {
                    override fun onResponse(call: Call<ArrayList<CityLocal>>, response: Response<ArrayList<CityLocal>>) {
                        val res = response.body()
                        val cngCitiesList = mutableListOf<String>()
                        for (item in res!!) {
                            cngCitiesList.add(item.locality.toString())
                        }
                        cities = cngCitiesList
                        cityAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, cities)
                        if (isAdapter) {
                            cityDispatchCompleteTextView.setAdapter(cityAdapter)
                            cityAdapter.notifyDataSetChanged()
                        } else {
                            cityReceivingCompleteTextView.setAdapter(cityAdapter)
                            cityAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                        Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    // получение id города
    private fun getIdData(city: String, context: Context, isCity: Boolean) {
        RetrofitClient().getRetrofitClient(context).create(GeographyApi::class.java)
            .getCityLocality(city, 1).enqueue(object : Callback<ArrayList<CityLocal>>{
                override fun onResponse(
                    call: Call<ArrayList<CityLocal>>,
                    response: Response<ArrayList<CityLocal>>
                ) {
                    if (isCity) {
                        val res = response.body()
                        for (item in res!!) {
                            cityIdReceiving = item.id
                        }
                    } else if (!isCity) {
                        val res = response.body()
                        for (item in res!!) {
                            cityIdDispatch = item.id
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<CityLocal>>, t: Throwable) {
                    Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show()
                }

            })
    }

    // показ диалога
    private fun showDialog() {
        val selectedSpinnerItem = spinner.selectedItem.toString()

        if (selectedSpinnerItem == "Груз") { // Если выбран "Груз", то выбираем диалоговую форму - cargo_dialog
            // Встраиваем в диалог наше кастомное view диалога
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.cargo_dialog, null)
            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                    .setTitle("Дополнительные данные")
            // Показываем диалог
            val mAlertDialog = mBuilder.show()
            // Нажатие на кнопку Ввод
            mDialogView.cargo_enter_btn.setOnClickListener {
                // записываем данные
                cargoHeight = mDialogView.dialog_cargo_height.text.toString()
                cargoWidth = mDialogView.dialog_cargo_width.text.toString()
                cargoWeight = mDialogView.dialog_cargo_width.text.toString()
                cargoLenght = mDialogView.dialog_cargo_lenght.text.toString()
                createProduct("Груз", context!!)
                createDeparture(context!!)
                // скрываем диалог
                mAlertDialog.dismiss()
            }
            // Нажатие на кнопку Отмена
            mDialogView.cargo_cancel_btn.setOnClickListener {
                // скрываем диалог
                mAlertDialog.dismiss()
            }
        } else if (selectedSpinnerItem == "Документы") {
            // Если выбраны "Документы", то выбираем диалоговую форму - documents_dialog
            // Встраиваем в диалог наше кастомное view диалога
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.documents_dialog, null)
            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                    .setTitle("Дополнительные данные")
            // Показываем диалог
            val mAlertDialog = mBuilder.show()
            // Нажатие на кнопку Ввод
            mDialogView.documents_enter_btn.setOnClickListener {
                // записываем данные
                documentsWeight = mDialogView.dialog_documents_weight.text.toString()
                // скрываем диалог
                mAlertDialog.dismiss()
            }
            // Нажатие на кнопку Отмена
            mDialogView.documents_cancel_btn.setOnClickListener {
                // скрываем диалог
                mAlertDialog.dismiss()
            }
        }
    }

    // создать груз
    private fun createProduct(product: String, context: Context) {
        if (product == "Груз") {
            val request = CreateProductRequest()
            request.delivery_type = "cargo"
            request.height = cargoHeight
            request.length = cargoLenght
            request.width = cargoWidth
            request.weight = cargoWeight
            RetrofitClient().getRetrofitClient(context).create(ProductApi::class.java)
                .postProduct(request).enqueue(object : Callback<CreateProductResponse>{
                    override fun onResponse(
                        call: Call<CreateProductResponse>,
                        response: Response<CreateProductResponse>
                    ) {
                        val code = response.code()
                        if (code == 201) {
                            Toast.makeText(context, "Груз создан успешно", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<CreateProductResponse>, t: Throwable) {
                        Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    // создать перевозку
    private fun createDeparture(context: Context) {
        val request = postDepartureRequest()
        request.receiver_city = cityIdReceiving
        request.sender_city = cityIdDispatch
        request.cargoes = cargoesList
        request.delivery = delivery
        request.pickup = pickup
        RetrofitClient().getRetrofitClient(context).create(ProductApi::class.java)
            .postDeparture(request).enqueue(object : Callback<postDepartureResponse>{
                override fun onResponse(
                    call: Call<postDepartureResponse>,
                    response: Response<postDepartureResponse>
                ) {
                    val res = response.body()
                    val code = response.code()
                    if (code == 201) {
                        val i = Intent(context, ShowTariffsActivity::class.java)
                        // передаём id груза в ShowTariffsActivity
                        i.putExtra("id", res!!.id)
                        startActivity(i)
                    }
                }

                override fun onFailure(call: Call<postDepartureResponse>, t: Throwable) {
                    Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment PurseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}