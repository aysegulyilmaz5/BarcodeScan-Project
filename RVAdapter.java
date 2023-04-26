package com.aysegulyilmaz.barkodproje;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RVModal> RVModalArrayList;

    public RVAdapter(Context context, ArrayList<RVModal> RVModalArrayList) {

        this.context = context;
        this.RVModalArrayList = RVModalArrayList;
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.ViewHolder holder, int position) {

        RVModal modal = RVModalArrayList.get(position);
        holder.nameTV.setText(modal.getName());
        holder.currencyTV.setText(modal.getCurrency()+"$");
        holder.countryTV.setText(modal.getCountry());
        holder.priceTV.setText(modal.getPrice()+"$");



    }

    @Override
    public int getItemCount() {
        return RVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV,currencyTV,countryTV,priceTV;

        //private ImageView condition;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            currencyTV = itemView.findViewById(R.id.currencyTV);
            countryTV = itemView.findViewById(R.id.countryTV);
            priceTV = itemView.findViewById(R.id.priceTV);



        }
    }
}