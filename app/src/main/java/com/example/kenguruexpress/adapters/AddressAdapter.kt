package com.example.kenguruexpress.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kenguruexpress.ListItemAddress
import com.example.kenguruexpress.R

class AddressAdapter (listArray: ArrayList<ListItemAddress>, context: Context): RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    var listArrarR = listArray
    var contextR = context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNameCompany = view.findViewById<TextView>(R.id.addrCompanyName)
        val tvPhoneCompany = view.findViewById<TextView>(R.id.addrCompanyPhone)

        fun bind(listItem: ListItemAddress, context: Context) {
            tvNameCompany.text = listItem.company
            tvPhoneCompany.text = listItem.phone
            itemView.setOnClickListener {
                Toast.makeText(context, "Pressed : ${tvPhoneCompany.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.item_address_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = listArrarR[position]
        holder.bind(listItem, contextR)
    }

    override fun getItemCount(): Int {
        return listArrarR.size
    }

    fun updateAdapter(listArray: List<ListItemAddress>) {
        listArrarR.clear()
        listArrarR.addAll(listArray)
        notifyDataSetChanged()
    }
}