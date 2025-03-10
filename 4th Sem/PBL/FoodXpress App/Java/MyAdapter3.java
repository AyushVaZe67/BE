package com.example.foodxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.OrderHolder3> {

    Context context;
    ArrayList<UserOrder3> list;

    public MyAdapter3(Context context, ArrayList<UserOrder3> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item3,parent,false);
        return new OrderHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder3 holder, int position) {
        UserOrder3 userOrder3 = list.get(position);
        holder.name.setText(userOrder3.getName());
        holder.phonenumber.setText(userOrder3.getPhoneNumber());
        holder.timing.setText(userOrder3.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderHolder3 extends RecyclerView.ViewHolder{
        TextView name,phonenumber,timing;

        public OrderHolder3(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dispNameToMess3);
            phonenumber = itemView.findViewById(R.id.dispPhoneToMess3);
            timing = itemView.findViewById(R.id.dispTimimgToMess3);
        }
    }
}
