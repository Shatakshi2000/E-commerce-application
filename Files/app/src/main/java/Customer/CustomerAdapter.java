package Customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstuff.Model;
import com.example.foodstuff.MyViewHolder;
import com.example.foodstuff.R;

import java.util.ArrayList;

import Retailer.RetailerDairyActivity;
import Retailer.RetailerFruitActivity;
import Retailer.RetailerSpiceActivity;
import Retailer.RetailerVegetableActivity;

public class CustomerAdapter extends RecyclerView.Adapter<MyViewHolder>{

    ArrayList<Model> data;
    Context context;

    public CustomerAdapter(ArrayList<Model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Model temp = data.get(position);

        holder.CategoryName.setText(data.get(position).getCategoryName());
        holder.FoodItem.setImageResource(data.get(position).getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(temp.getCategoryName() == "Vegetable")
                {
                    Intent intent = new Intent(context, CustomerVegetableActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(temp.getCategoryName() == "Fruits")
                {
                    Intent intent = new Intent(context, CustomerFruitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(temp.getCategoryName() == "Spices")
                {
                    Intent intent = new Intent(context, CustomerSpicesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, CustomerDairyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
