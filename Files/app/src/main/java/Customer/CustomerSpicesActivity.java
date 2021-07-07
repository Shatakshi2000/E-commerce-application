package Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.foodstuff.CartActivity;
import com.example.foodstuff.R;
import com.example.foodstuff.ReviewFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CustomerSpicesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar fruitsToolbar;
    Button AddFruit;
    FirebaseRecyclerAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_spices);

        fruitsToolbar = (Toolbar) findViewById(R.id.fruits_toolbar);
        setSupportActionBar(fruitsToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recview);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance().getReference("users").child("retailer").child("Spices");



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
        FloatingActionButton cart = findViewById(R.id.cart);
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
