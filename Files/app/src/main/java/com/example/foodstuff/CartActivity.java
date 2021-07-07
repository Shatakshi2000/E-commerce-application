package com.example.foodstuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import AddProduct.dataHolder;
import Firebase.Recyclerview.ProductModel;
import Firebase.Recyclerview.RetailerProductHolder;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter cartadapter;
    int i=1;
    TextView calculateTotal;
    double total=0;
    Button payment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recview);
        calculateTotal = findViewById(R.id.calculate_payment);
        payment = findViewById(R.id.payment_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();




        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(database.getReference("cart").child(setProfileData.getProfilePhoneNumber()),CartModel.class)
                        .build();



        cartadapter = new FirebaseRecyclerAdapter<CartModel, CartProductHolder>(options) {
            @Override
            public CartProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_layout, parent, false);

                return new CartProductHolder(view);
            }

            @Override
            protected void onBindViewHolder(CartProductHolder holder, int position, CartModel model) {
                // Bind the Chat object to the ChatHolder
                // ...

                holder.shopName.setText(model.getUsername());
                holder.productName.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
                String p = model.getPrice();
                double d = Double.parseDouble(p.replace("Rs ",""));
                total = total + d;
                calculateTotal.setText("Rs "+total);


                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i++;
                        holder.number.setText(""+i);
                        total = total+d;
                        calculateTotal.setText("Rs "+total);
                    }
                });
                holder.subtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(i<1)
                        {
                            Toast.makeText(CartActivity.this, "quantity cannot be less than one", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            i--;
                            holder.number.setText(""+i);
                            total = total - d;
                            calculateTotal.setText("Rs "+total);
                        }

                    }
                });
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.price.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Do you want to delete this item from the cart");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference("cart").child(setProfileData.getProfilePhoneNumber())
                                        .child(getRef(position).getKey()).removeValue();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();
                    }
                });





            }
        };
        recyclerView.setAdapter(cartadapter);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("cart").child(setProfileData.getProfilePhoneNumber());
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String vendorName = ds.child("username").getValue().toString();

                                Map<String, Object> map = new HashMap<>();
                                map.put("image", ds.child("image").getValue().toString());
                                map.put("name", ds.child("name").getValue().toString());
                                map.put("price", ds.child("price").getValue().toString());
                                map.put("quantity", ds.child("quantity").getValue().toString());
                                map.put("number", +i);
                                map.put("retailerPhoneNumber", setProfileData.getProfilePhoneNumber().toString());
                                map.put("retailerAddress", setProfileData.getProfileAddress());

                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("image", ds.child("image").getValue().toString());
                                map2.put("name", ds.child("name").getValue().toString());
                                map2.put("price", ds.child("price").getValue().toString());
                                map2.put("quantity", ds.child("quantity").getValue().toString());
                                map2.put("number", +i);
                                map2.put("username",ds.child("username").getValue().toString());




                                FirebaseDatabase.getInstance().getReference().child("CurrentOrders").child(vendorName).child(ds.getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(CartActivity.this,"Orders are sent",Toast.LENGTH_SHORT).show();

                                        FirebaseDatabase.getInstance().getReference().child("PlacedOrders").child(setProfileData.getProfilePhoneNumber()).child(ds.getKey()).updateChildren(map2);

                                        FirebaseDatabase.getInstance().getReference("cart").child(setProfileData.getProfilePhoneNumber())
                                                .child(ds.getKey()).removeValue();
                                        calculateTotal.setText("Rs 0");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CartActivity.this,"Orders are not sent",Toast.LENGTH_SHORT).show();
                                    }
                                });





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        cartadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cartadapter.stopListening();
    }


}