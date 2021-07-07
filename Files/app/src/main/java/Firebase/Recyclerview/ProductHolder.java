package Firebase.Recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstuff.R;

public class ProductHolder extends RecyclerView.ViewHolder
    {
        public ImageView productImage;
        public TextView shopName;
        public TextView productName;
        public TextView quantity;
        public TextView price;
        public ImageView delete;
        public ImageView edit;



        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            productImage = (ImageView)itemView.findViewById(R.id.wholesaler_product_image);
            shopName = (TextView) itemView.findViewById(R.id.wholesaler_shop_name);
            productName = (TextView) itemView.findViewById(R.id.wholesaler_product_name);
            quantity = (TextView) itemView.findViewById(R.id.wholesaler_quantity);
            price = (TextView) itemView.findViewById(R.id.wholesaler_price);
            delete = (ImageView)itemView.findViewById(R.id.delete);
            edit = (ImageView)itemView.findViewById(R.id.edit);



        }
    }

