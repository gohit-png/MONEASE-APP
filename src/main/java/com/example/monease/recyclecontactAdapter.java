package com.example.monease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monease.Models.Transactions;

import java.util.ArrayList;

public class recyclecontactAdapter extends RecyclerView.Adapter<recyclecontactAdapter.ViewHolder> {
    Context context;
    ArrayList<contact_model> arrcontacts;
    recyclecontactAdapter(Context context, ArrayList<contact_model> arrcontacts) {
      this.context=context;
      this.arrcontacts=arrcontacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.date.setText(arrcontacts.get(position).date);
    holder.name.setText(arrcontacts.get(position).name);
    holder.amount.setText(arrcontacts.get(position).amount.toString());
    holder.type.setText(arrcontacts.get(position).type);
    }

    @Override
    public int getItemCount() {
        return arrcontacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date,amount,name,type;

        public ViewHolder( @NonNull  View itemView){
            super(itemView);

            date=itemView.findViewById(R.id.date);
            name=itemView.findViewById(R.id.name);
            amount=itemView.findViewById(R.id.rs);
            type=itemView.findViewById(R.id.type);
        }
    }
}
