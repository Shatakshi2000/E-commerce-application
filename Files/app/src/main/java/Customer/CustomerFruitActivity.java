package Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodstuff.CartActivity;
import com.example.foodstuff.R;
import com.example.foodstuff.ReviewFragment;
import com.example.foodstuff.setProfileData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import AddProduct.AddNewProduct;
import Firebase.Recyclerview.ProductHolder;
import Firebase.Recyclerview.ProductModel;

public class CustomerFruitActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar fruitsToolbar;
    Button AddFruit;
    FirebaseRecyclerAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fruit);


        fruitsToolbar = (Toolbar) findViewById(R.id.fruits_toolbar);


        recyclerView = findViewById(R.id.recview);
        FloatingActionButton cart = findViewById(R.id.cart);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance().getReference("users").child("retailer").child("Fruits");



        FirebaseRecyclerOptions<CustomerProductModel> options =
                new FirebaseRecyclerOptions.Builder<CustomerProductModel>()
                        .setQuery(query, CustomerProductModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CustomerProductModel, CustomerHolder>(options) {
            @Override
            public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.customer_products_layout, parent, false);

                return new CustomerHolder(view);
            }

            @Override
            protected void onBindViewHolder(final CustomerHolder holder, final int position, CustomerProductModel model) {
                // Bind the Chat object to the ChatHolder
                // ...

                holder.shopName.setText(model.getUsername());
                holder.productName.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
                holder.address.setText(model.getAddress());

                holder.reviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getSupportFragmentManager();
                        Fragment temp = new ReviewFragment();
                        fm.beginTransaction().replace(R.id.customer_container,temp).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("key",getRef(position).getKey());
                        temp.setArguments(bundle);

                    }
                });

            }


        };
        recyclerView.setAdapter(adapter);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}