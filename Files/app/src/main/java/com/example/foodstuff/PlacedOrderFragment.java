package com.example.foodstuff;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacedOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacedOrderFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlacedOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlacedOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlacedOrderFragment newInstance(String param1, String param2) {
        PlacedOrderFragment fragment = new PlacedOrderFragment();
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
        View v = inflater.inflate(R.layout.fragment_placed_order, container, false);
        recyclerView = v.findViewById(R.id.placed_order_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        FirebaseRecyclerOptions<PlacedOrderModel> options =
                new FirebaseRecyclerOptions.Builder<PlacedOrderModel>()
                        .setQuery(database.getReference("PlacedOrders").child(setProfileData.getProfilePhoneNumber()), PlacedOrderModel.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<PlacedOrderModel, PlacedProductHolder>(options) {
            @Override
            public PlacedProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.placed_order_layout, parent, false);

                return new PlacedProductHolder(view);
            }

            @Override
            protected void onBindViewHolder(PlacedProductHolder holder, int position, PlacedOrderModel model) {
                // Bind the Chat object to the ChatHolder
                // ...

                holder.shopName.setText(model.getUsername());
                holder.productName.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
                holder.number.setText(""+model.getNumber());


                holder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("status").child(setProfileData.getProfilePhoneNumber());
                        final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                                .setContentHolder(new ViewHolder(R.layout.status_content_layout))
                                .setExpanded(true, 1300)
                                .create();
                        View myview = dialogPlus.getHolderView();

                        CheckBox checkbox1 = myview.findViewById(R.id.checkBox1);
                        CheckBox checkbox2 = myview.findViewById(R.id.checkBox2);
                        CheckBox checkbox3 = myview.findViewById(R.id.checkBox3);
                        CheckBox checkbox4 = myview.findViewById(R.id.checkBox4);
                        Map<String,Object> map2 =  new HashMap<>();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("status").child(setProfileData.getProfilePhoneNumber()).child(getRef(position).getKey());
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    System.out.println("Reading data");
                                    checkbox1.setChecked((Boolean) snapshot.child("cb1").getValue());
                                    checkbox2.setChecked((Boolean) snapshot.child("cb2").getValue());
                                    checkbox3.setChecked((Boolean) snapshot.child("cb3").getValue());
                                    checkbox4.setChecked((Boolean) snapshot.child("cb4").getValue());
                                }

                                if (snapshot.child("cb4").getValue().equals(true))
                                {



                                    Intent intent = new Intent(getContext(), ReviewActivity.class);
                                    intent.putExtra("key",getRef(position).getKey());
                                    startActivity(intent);







                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialogPlus.show();

                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        return v;
    }
        @Override
        public void onStart() {
            super.onStart();
            adapter.startListening();
        }

        @Override
        public void onStop () {
            super.onStop();
            adapter.stopListening();
        }
    }
