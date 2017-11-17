package br.com.appinfra.appinfra.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.appinfra.appinfra.R;

/**
 * Created by alber on 17/11/2017.
 */

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle,tvCity,tvNeighborhood;

    public MyViewHolder(View itemView) {

        super(itemView);
        tvTitle=(TextView) itemView.findViewById(R.id.tv_cidade);
        tvCity=(TextView) itemView.findViewById(R.id.tv_endereco);
        tvNeighborhood=(TextView) itemView.findViewById(R.id.tv_bairro);

    }
}
