package com.example.kenguruexpress.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kenguruexpress.MainActivity
import com.example.kenguruexpress.R
import kotlinx.android.synthetic.main.cargo_dialog.view.*
import kotlinx.android.synthetic.main.documents_dialog.view.*
import kotlinx.android.synthetic.main.fragment_purse.*
import kotlinx.android.synthetic.main.fragment_purse.view.*

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

        // Нажать на кнопку рассчитать, чтобы появилась диалоговое окно
        countBtn.setOnClickListener {
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
                    val cargoHeight = mDialogView.dialog_cargo_height.text.toString()
                    val cargoWidth = mDialogView.dialog_cargo_width.text.toString()
                    val cargoWeight = mDialogView.dialog_cargo_width.text.toString()
                    val cargoLenght = mDialogView.dialog_cargo_lenght.text.toString()
                    val similarGoods = mDialogView.similarGoods.text.toString()
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
                    val documentsWeight = mDialogView.dialog_documents_weight.text.toString()
                    val similarGoods = mDialogView.similarGoods.text.toString()
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