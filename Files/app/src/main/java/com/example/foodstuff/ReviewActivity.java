package com.example.foodstuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {


    private RatingBar ratingbar;
    private EditText reviewEt;
    private FloatingActionButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //initial ui views

        ratingbar = findViewById(R.id.ratingbar);
        reviewEt = findViewById(R.id.reviewEt);
        submitBtn =findViewById(R.id.submitBtn);

        String key = getIntent().getStringExtra("key").toString();







        //input data
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ratings = ""+ ratingbar.getRating();
                String review = reviewEt.getText().toString().trim();

                //for time of review


                //set up data in hashmap
                HashMap<String, Object> hashMap= new HashMap();
                hashMap.put("ratings",""+ratings);
                hashMap.put("review",""+review);

                // To database: DB>user>Ratings
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                database.getReference().child("reviews").child(key)
                        .child(setProfileData.getProfileUsername()).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>(){
                            @Override
                            public void onSuccess(Void aVoid){
                                Toast.makeText(ReviewActivity.this, "Review published successfully...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener(){
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //failed adding reciew to DB
                                Toast.makeText(ReviewActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }



}