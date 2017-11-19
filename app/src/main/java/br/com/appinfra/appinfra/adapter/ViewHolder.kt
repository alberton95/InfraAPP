package br.com.appinfra.appinfra.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import br.com.appinfra.appinfra.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvTitle: TextView
    var tvCity: TextView
    var tvNeighborhood: TextView
    var ivResult: ImageView
    var tvStatus: String = ""

    init {
        tvTitle = itemView.findViewById(R.id.tv_cidade) as TextView
        tvCity = itemView.findViewById(R.id.tv_endereco) as TextView
        tvNeighborhood = itemView.findViewById(R.id.tv_bairro) as TextView
        ivResult = itemView.findViewById(R.id.iv_imagem) as ImageView
    }
}
