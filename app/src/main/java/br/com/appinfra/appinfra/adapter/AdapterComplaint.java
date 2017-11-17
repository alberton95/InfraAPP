package br.com.appinfra.appinfra.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.appinfra.appinfra.R;
import br.com.appinfra.appinfra.models.beans.Complaint;

public class AdapterComplaint extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<Complaint> complaints;

    public AdapterComplaint(Context c, ArrayList<Complaint> complaints) {
        this.c = c;
        this.complaints = complaints;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(c).inflate(R.layout.item_question,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(complaints.get(position).getAdress());
        holder.tvCity.setText(complaints.get(position).getTitle());
        holder.tvNeighborhood.setText(complaints.get(position).getDescription());
    }
    @Override
    public int getItemCount() {
        return complaints.size();
    }
}