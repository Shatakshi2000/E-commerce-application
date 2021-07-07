package com.example.foodstuff;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{

    public ImageView FoodItem;
    public TextView CategoryName;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        FoodItem = (ImageView) itemView.findViewById(R.id.food_item);
        CategoryName = (TextView) itemView.findViewById(R.id.category_name);
    }
}
