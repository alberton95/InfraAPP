package br.com.appinfra.appinfra.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.Reclamacao
import de.hdodenhof.circleimageview.CircleImageView


class ReclamacoesAdapter(
        private val context: Context,
        private val reclamacaos: List<Reclamacao>) :
        RecyclerView.Adapter<ReclamacoesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int) : ReclamacoesAdapter.ViewHolder {

        val v = LayoutInflater
                .from(context)
                .inflate(R.layout.item_question, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData( reclamacaos[position] )
    }

    override fun getItemCount(): Int {
        return reclamacaos.size
    }

    // Montagem da RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImagem: CircleImageView
        var tvEndereco: TextView
        var tvBairro: TextView
        var tvCidade: TextView
        var tvStatus: TextView
        var ivStatus: ImageView

        init {
            ivImagem = itemView.findViewById(R.id.iv_imagem) as CircleImageView
            tvEndereco = itemView.findViewById(R.id.tv_endereco) as TextView
            tvBairro = itemView.findViewById(R.id.tv_bairro) as TextView
            tvCidade = itemView.findViewById(R.id.tv_cidade) as TextView
            tvStatus = itemView.findViewById(R.id.tv_data) as TextView
            ivStatus = itemView.findViewById(R.id.ivStatus) as ImageView
        }

        fun setData(reclamacao: Reclamacao) {
            ivImagem.setImageResource(reclamacao.imagem)
            tvEndereco.text = reclamacao.endereco
            tvCidade.text = reclamacao.cidade
            tvBairro.text = reclamacao.bairro
            tvStatus.text = reclamacao.status.toString()


            // Checagem de Status - Resolvido ou Pendente
            if(reclamacao.status.equals(false)){
                tvStatus.text = "Pendente"
                ivStatus.setImageResource(R.drawable.ic_error)
                tvStatus.setTextColor(Color.RED)
            }else{
                tvStatus.text = "Resolvido"
                ivStatus.setImageResource(R.drawable.ic_checked)
                tvStatus.setTextColor(Color.GREEN)
            }

        }
    }
}