package com.example.foodstuff;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rey.material.widget.ImageButton;

public class CartProductHolder extends RecyclerView.ViewHolder {
    public ImageView productImage;
    public TextView shopName;
    public TextView productName;
    public TextView quantity;
    public TextView price;
    public ImageButton add,subtract;
    public TextView number;
    ImageView delete;


    public CartProductHolder(@NonNull View itemView) {
        super(itemView);

        productImage = (ImageView)itemView.findViewById(R.id.cart_product_image);
        shopName = (TextView) itemView.findViewById(R.id.cart_shop_name);
        productName = (TextView) itemView.findViewById(R.id.cart_product_name);
        quantity = (TextView) itemView.findViewById(R.id.cart_quantity);
        price = (TextView) itemView.findViewById(R.id.cart_price);
        add = (ImageButton)itemView.findViewById(R.id.cart_add);
        subtract = (ImageButton)itemView.findViewById(R.id.cart_subtract);
        number = (TextView) itemView.findViewById(R.id.number);
        delete = (ImageView) itemView.findViewById(R.id.cart_delete);



    }
}
