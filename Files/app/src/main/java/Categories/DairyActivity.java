package Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.foodstuff.R;
import com.example.foodstuff.setProfileData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import AddProduct.AddNewProduct;
import Firebase.Recyclerview.ProductHolder;
import Firebase.Recyclerview.ProductModel;

public class DairyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar dairyToolbar;
    Button AddDairy;
    FirebaseRecyclerAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

        dairyToolbar = (Toolbar) findViewById(R.id.dairy_toolbar);
        setSupportActionBar(dairyToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recview);
        AddDairy = (Button) findViewById(R.id.add_dairy);




        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance().getReference("users").child("wholesaler").child("Dairy")
                .orderByChild("username")
                .equalTo(setProfileData.getProfileUsername());




        FirebaseRecyclerOptions<ProductModel> options =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(query,ProductModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ProductModel, ProductHolder>(options) {
            @Override
            public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.products_layout, parent, false);

                return new ProductHolder(view);
            }

            @Override
            protected void onBindViewHolder(final ProductHolder holder, final int position, ProductModel model) {
                // Bind the Chat object to the ChatHolder
                // ...

                holder.shopName.setText(model.getUsername());
                holder.productName.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.price.getContext())
                                .setContentHolder(new ViewHolder(R.layout.dialog_content))
                                .setExpanded(true,1300)
                                .create();
                        View myview  = dialogPlus.getHolderView();
                        EditText updateImageUrl = myview.findViewById(R.id.update_image_url);
                        EditText updateProductName = myview.findViewById(R.id.update_product_name);
                        EditText updatePrice = myview.findViewById(R.id.update_price);
                        EditText updateQuantity= myview.findViewById(R.id.update_quantity);
                        Button buttonUpdate = myview.findViewById(R.id.button_update);

                        updateImageUrl.setText(model.getImage());
                        updateProductName.setText(model.getName());
                        updatePrice.setText(model.getPrice());
                        updateQuantity.setText(model.getQuantity());

                        dialogPlus.show();

                        buttonUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> map =  new HashMap<>();
                                map.put("image",updateImageUrl.getText().toString());
                                map.put("name",updateProductName.getText().toString());
                                map.put("price",updatePrice.getText().toString());
                                map.put("quantity",updateQuantity .getText().toString());

                                FirebaseDatabase.getInstance().getReference("users").child("wholesaler").child("Dairy")
                                        .child(getRef(position).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialogPlus.dismiss();
                                                Toast.makeText(getApplicationContext(),"Content updated successfully",Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();
                                                Toast.makeText(getApplicationContext(),"Update unsuccessful... Retry",Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        });

                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.price.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Do you want to delete this item");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference("users").child("wholesaler").child("Fruits")
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
        recyclerView.setAdapter(adapter);




        AddDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), AddNewProduct.class);
                intent.putExtra("message_key","Fruits");// putExtra() is used for sending the data, in the key and the value can be float, string etc.
                startActivity(intent);

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
