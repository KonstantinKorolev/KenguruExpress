package com.example.kenguruexpress.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kenguruexpress.OrderingActivity
import com.example.kenguruexpress.R
import com.example.kenguruexpress.models.websocket.WebSocketResponse
import kotlinx.android.synthetic.main.item_traffic_layout.view.*

class TarrifsAdapter (listArray: ArrayList<WebSocketResponse>, context: Context): RecyclerView.Adapter<TarrifsAdapter.ViewHolder>() {
    var listArrayR = listArray
    var contextR = context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tarrifTitle = view.findViewById<TextView>(R.id.trafficItemTitle)
        val tarrifDispatch = view.findViewById<TextView>(R.id.trafficItemDispatch)
        val tarrifReit = view.findViewById<TextView>(R.id.trafficItemReit)
        val tarrifBtn = view.findViewById<Button>(R.id.trafficItemBtn)

        @SuppressLint("SetTextI18n")
        fun bind(listItem: WebSocketResponse, context: Context) {
            val textTarrifBtn = context.resources.getString(R.string.buyFor)
            tarrifTitle.text = listItem.data?.operator
            tarrifDispatch.text = listItem.data?.delivery_day
            tarrifReit.text = listItem.data?.rating
            tarrifBtn.text = "$textTarrifBtn ${listItem.data?.common_price}"
            itemView.trafficItemBtn.setOnClickListener {
                    context.startActivity(Intent(context, OrderingActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.item_traffic_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = listArrayR[position]
        holder.bind(listItem, contextR)
    }

    override fun getItemCount(): Int {
        return listArrayR.size
    }

    fun updateAdapter(listArray: ArrayList<WebSocketResponse>) {
        listArrayR.addAll(listArray)
        notifyDataSetChanged()
    }

    private fun routeToOrdering() {
        val i = Intent(contextR, OrderingActivity::class.java)
    }

}
