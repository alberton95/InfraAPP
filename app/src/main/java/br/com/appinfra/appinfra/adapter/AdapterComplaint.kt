package br.com.appinfra.appinfra.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.beans.Complaint
import com.bumptech.glide.Glide
import java.util.*

class AdapterComplaint(internal var c: Context, internal var complaints: ArrayList<Complaint>) :
        RecyclerView.Adapter<MyViewHolder>() {

    // Set Layout Recycler View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.item_question, parent, false)
        return MyViewHolder(v)
    }

    // Set Values from Data Firebase
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = complaints[position].adress + ", " + complaints[position].neighborhood + ", " + complaints[position].city
        holder.tvCity.text = complaints[position].title
        holder.tvNeighborhood.text = complaints[position].description
        holder.tvImage = complaints[position].image
        holder.tvStatus.text = complaints[position].status

        Glide.with(c).load(holder.tvImage).into(holder.ivResult);

        // Check Status - Resolved/Pending
            if(holder.tvStatus.text.equals("true")){
                holder.tvStatus.text = "Pendente"
                holder.ivStatus.setImageResource(R.drawable.ic_error)
                holder.tvStatus.setTextColor(Color.RED)
            }else{
                holder.tvStatus.text = "Resolvido"
                holder.ivStatus.setImageResource(R.drawable.ic_checked)
                holder.tvStatus.setTextColor(Color.GREEN)
            }


    }

    // Get Size List
    override fun getItemCount(): Int {
        return complaints.size
    }


}