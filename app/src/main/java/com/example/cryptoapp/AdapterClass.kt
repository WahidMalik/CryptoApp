package com.example.cryptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass (var context : Context,var array : ArrayList<Model>) :RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    class ViewHolderClass (itemView : View) : RecyclerView.ViewHolder(itemView){
        val symbol = itemView.findViewById<TextView>(R.id.symbol)
        val price = itemView.findViewById<TextView>(R.id.price)
        val name = itemView.findViewById<TextView>(R.id.name)

        fun bind(model : Model){
            symbol.text = model.symbol
            price.text = model.price
            name.text = model.name
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_layout,parent,false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = array[position]
        holder.bind(item)
    }


}