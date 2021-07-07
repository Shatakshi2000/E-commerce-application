package com.example.foodstuff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Retailer.RetailerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailHomeFragment extends Fragment {

    RecyclerView recyclerView;

    RetailerAdapter myadapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RetailHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailHomeFragment newInstance(String param1, String param2) {
        RetailHomeFragment fragment = new RetailHomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_retail_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rclview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myadapter = new RetailerAdapter(dataqueue(), getContext());
        recyclerView.setAdapter(myadapter);
        return view;
    }
    public ArrayList<Model> dataqueue() {
        ArrayList<Model> holder = new ArrayList<>();

        Model obj1 = new Model();
        obj1.setCategoryName("Fruits");
        obj1.setImg(R.drawable.fruits);
        holder.add(obj1);

        Model obj2 = new Model();
        obj2.setCategoryName("Vegetables");
        obj2.setImg(R.drawable.vegetable);
        holder.add(obj2);

        Model obj3 = new Model();
        obj3.setCategoryName("Spices");
        obj3.setImg(R.drawable.spices);
        holder.add(obj3);

        Model obj4 = new Model();
        obj4.setCategoryName("Dairy");
        obj4.setImg(R.drawable.dairy);
        holder.add(obj4);

        return holder;
    }
}