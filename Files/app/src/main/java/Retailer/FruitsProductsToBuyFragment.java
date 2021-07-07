package Retailer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import AddProduct.CartDataHolder;
import AddProduct.dataHolder;
import Firebase.Recyclerview.ProductHolder;
import Firebase.Recyclerview.ProductModel;
import Firebase.Recyclerview.RetailerProductHolder;


public class FruitsProductsToBuyFragment extends Fragment {


    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    ImageView cart;
    String ad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FruitsProductsToBuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsOnSaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FruitsProductsToBuyFragment newInstance(String param1, String param2) {
        FruitsProductsToBuyFragment fragment = new FruitsProductsToBuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fruits_products_to_buy, container, false);
        recyclerView = view.findViewById(R.id.recview_fragment);
        cart = view.findViewById(R.id.cart);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();




        FirebaseRecyclerOptions<ProductModel> options =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(database.getReference("users").child("wholesaler").child("Fruits"),ProductModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ProductModel, RetailerProductHolder>(options) {
            @Override
            public RetailerProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.retailer_products_layout, parent, false);


                return new RetailerProductHolder(view);
            }

            @Override
            protected void onBindViewHolder(RetailerProductHolder holder, int position, ProductModel model) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            if((ds.child("username").getValue(String.class)).equals(model.getUsername())) {
                                ad =  ds.child("address").getValue().toString();
                                Toast.makeText(getContext(),"Address added successfully",Toast.LENGTH_SHORT).show();

                                break;
                            }

                        }
                        holder.shopName.setText(model.getUsername());
                        holder.productName.setText(model.getName());
                        holder.quantity.setText(model.getQuantity());
                        holder.price.setText(model.getPrice());
                        Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
                        String s= getRef(position).getKey();
                        holder.address.setText(ad);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                // Bind the Chat object to the ChatHolder
                // ...

                holder.shopName.setText(model.getUsername());
                holder.productName.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
                String s= getRef(position).getKey();
                holder.address.setText(ad);

                holder.addProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("cart").child(setProfileData.getProfilePhoneNumber());
                        CartDataHolder obj = new CartDataHolder(model.getName(),model.getPrice(),model.getQuantity(),model.getImage(),model.getUsername(),ad);
                        db.child(s).setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(),"Item added Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Item is not added ",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                holder.review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("key",s);
                        Fragment temp = new ReviewFragment();
                        temp.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.retailer_viewpager,temp).commit();

                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}