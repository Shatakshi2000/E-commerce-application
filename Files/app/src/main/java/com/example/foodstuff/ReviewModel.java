package com.example.foodstuff;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    @ServerTimestamp
    private String ratings;
    private String review;


    public ReviewModel() {
    }

    public ReviewModel(String ratings, String review) {
        this.ratings = ratings;
        this.review = review;

    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}
