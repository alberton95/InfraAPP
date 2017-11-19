package br.com.appinfra.appinfra.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.beans.Complaint
import com.squareup.picasso.Picasso
import java.util.*

class AdapterComplaint(internal var c: Context, internal var complaints: ArrayList<Complaint>) :
        RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.item_question, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = complaints[position].adress
        holder.tvCity.text = complaints[position].title
        holder.tvNeighborhood.text = complaints[position].description
        holder.tvStatus = complaints[position].image

        Picasso.with(c)
                .load(holder.tvStatus)
                .into(holder.ivResult)
    }



    override fun getItemCount(): Int {
        return complaints.size
    }

    //             Checagem de Status - Resolvido ou Pendente
//            if(reclamacao.status.equals(false)){
//                tvStatus.text = "Pendente"
//                ivStatus.setImageResource(R.drawable.ic_error)
//                tvStatus.setTextColor(Color.RED)
//            }else{
//                tvStatus.text = "Resolvido"
//                ivStatus.setImageResource(R.drawable.ic_checked)
//                tvStatus.setTextColor(Color.GREEN)
//            }



}