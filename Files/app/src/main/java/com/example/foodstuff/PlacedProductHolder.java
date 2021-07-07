package com.example.foodstuff;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlacedProductHolder extends RecyclerView.ViewHolder
{

    public ImageView productImage;
    public TextView shopName;
    public TextView productName;
    public TextView quantity;
    public TextView price;
    public TextView number;
    Button status;
    ImageButton cancelOrder;

    public PlacedProductHolder(@NonNull View itemView) {
        super(itemView);
        productImage = (ImageView)itemView.findViewById(R.id.placed_order_product_image);
        shopName = (TextView) itemView.findViewById(R.id.placed_order_shop_name);
        productName = (TextView) itemView.findViewById(R.id.placed_order_product_name);
        quantity = (TextView) itemView.findViewById(R.id.placed_order_quantity);
        price = (TextView) itemView.findViewById(R.id.placed_order_price);
        number = (TextView) itemView.findViewById(R.id.placed_order_number);
        status = (Button) itemView.findViewById(R.id.button_status);
    }
}
