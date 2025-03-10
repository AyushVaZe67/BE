package com.example.foodxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.OrderHolder2>{

    Context context;

    public MyAdapter2(Context context, ArrayList<UserOrder2> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<UserOrder2> list;

    @NonNull
    @Override
    public OrderHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item2,parent,false);
        return new OrderHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder2 holder, int position) {
        UserOrder2 userOrder2 = list.get(position);
        holder.name.setText(userOrder2.getName());
        holder.phonenumber.setText(userOrder2.getPhoneNumber());
        holder.timing.setText(userOrder2.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderHolder2 extends RecyclerView.ViewHolder{

        TextView name,phonenumber,timing;
        public OrderHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.dispNameToMess2);
            phonenumber = itemView.findViewById(R.id.dispPhoneToMess2);
            timing = itemView.findViewById(R.id.dispTimimgToMess2);

        }
    }
}
