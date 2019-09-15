package com.example.sportsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.models.Article

class SportsAdapter(var list: Array<Article>) : RecyclerView.Adapter<SportsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun getItemCount(): Int {
        if (list.size == null) return 0
        else return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = list[position].title
    }

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}