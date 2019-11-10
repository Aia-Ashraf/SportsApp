package com.example.sportsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.models.Article
import kotlinx.android.synthetic.main.row_item.view.*

/*class SportsAdapter(var list: List<Article>?) : RecyclerView.Adapter<SportsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun getItemCount(): Int {
        if (list?.size == null) return 0
        else return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = list?.get(position)!!.title
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(textView)

}*/




class SportsAdapter(
    var list: List<Article>?,
    context: Context
) : RecyclerView.Adapter<SportsAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SportsAdapter.ViewHolder, position: Int) {
        holder.bindItems(this!!.list?.get(position)!!)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        if (list?.size != null)
            return list!!.size
        else return 0

    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Article) {
            itemView.item_tv.text = user.title
        }
    }
}