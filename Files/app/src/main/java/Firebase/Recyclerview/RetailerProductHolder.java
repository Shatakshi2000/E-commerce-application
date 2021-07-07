package Firebase.Recyclerview;

import android.location.Address;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstuff.R;
import com.example.foodstuff.setProfileData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import AddProduct.dataHolder;

public class RetailerProductHolder extends RecyclerView.ViewHolder {

    public ImageView productImage;
    public TextView shopName;
    public TextView productName;
    public TextView quantity;
    public TextView price;
    public Button addProduct;
    public TextView review;
    public TextView address;


    public RetailerProductHolder(@NonNull View itemView) {
        super(itemView);

        productImage = (ImageView)itemView.findViewById(R.id.retailer_product_image);
        shopName = (TextView) itemView.findViewById(R.id.retailer_shop_name);
        productName = (TextView) itemView.findViewById(R.id.retailer_product_name);
        quantity = (TextView) itemView.findViewById(R.id.retailer_quantity);
        price = (TextView) itemView.findViewById(R.id.retailer_price);
        addProduct = (Button) itemView.findViewById(R.id.retailer_add_product);
        review = (TextView) itemView.findViewById(R.id.reviews);
        address = (TextView) itemView.findViewById(R.id.retailer_address);



    }
}
