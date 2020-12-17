package com.example.kenguruexpress.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kenguruexpress.R
import kotlinx.android.synthetic.main.dispatch_dialog.view.*
import kotlinx.android.synthetic.main.fragment_dispatch.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DispatchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DispatchFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_dispatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dispatchFloatingButton.setOnClickListener {
            val dDialogView = LayoutInflater.from(context).inflate(R.layout.dispatch_dialog, null)
            val dBuilder = AlertDialog.Builder(context)
                    .setView(dDialogView)
                    .setTitle("Дополнительные данные")
            val dAlertDialog = dBuilder.show()

            dDialogView.dispatch_enter_btn.setOnClickListener {
                val dTrackNum = dDialogView.dispatch_track_num.text.toString().trim()
                dAlertDialog.dismiss()
            }
            dDialogView.dispatch_cancel_btn.setOnClickListener {
                dAlertDialog.dismiss()
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
         * @return A new instance of fragment DispatchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DispatchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
}