package com.example.crudapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapplication.R

class OrdersAdapter (
    val context: Context,
    val orderIdList: ArrayList<String>,
    val orderDateList: ArrayList<String>,
    val orderAmountList: ArrayList<String>,
    val orderMassList: ArrayList<String>,
    val orderStatusList: ArrayList<String>,
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderId: TextView = itemView.findViewById(R.id.lblOrderId)
        var orderDate: TextView = itemView.findViewById(R.id.lblOrderDate)
        var orderAmount: TextView = itemView.findViewById(R.id.lblOrderAmount)
        var orderMass: TextView = itemView.findViewById(R.id.lblOrderMass)
        var orderStatus: TextView = itemView.findViewById(R.id.lblOrderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.order_component, parent, false)
        return OrdersViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orderIdList.size
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.orderId.text = orderIdList[position]
        holder.orderDate.text = orderDateList[position]
        holder.orderAmount.text = orderAmountList[position]
        holder.orderMass.text = orderMassList[position]
        holder.orderStatus.text = orderStatusList[position]
    }
}