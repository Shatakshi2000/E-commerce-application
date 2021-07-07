package com.example.foodstuff;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.foodstuff.R.id.review_ratingbar;

public class ReviewProductHolder  extends RecyclerView.ViewHolder {


    public RatingBar ratings;
    public TextView username;
    public TextView review;

    public ReviewProductHolder(@NonNull View itemView) {
        super(itemView);

        ratings = itemView.findViewById(review_ratingbar);
        username = itemView.findViewById(R.id.review_username);
        review = itemView.findViewById(R.id.review_text);
    }
}
