package com.example.foodxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.OrderHolder> {

    Context context;

    public MyAdapter(Context context, ArrayList<UserOrder> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<UserOrder> list;

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item1,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        UserOrder userOrder = list.get(position);
        holder.name.setText(userOrder.getName());
        holder.phonenumber.setText(userOrder.getPhoneNumber());
        holder.timing.setText(userOrder.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderHolder extends RecyclerView.ViewHolder{

        TextView name,phonenumber,timing;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.dispNameToMess);
            phonenumber = itemView.findViewById(R.id.dispPhoneToMess);
            timing = itemView.findViewById(R.id.dispTimimgToMess);

        }
    }
}
