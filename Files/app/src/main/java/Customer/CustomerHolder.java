package Customer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstuff.R;

public class CustomerHolder extends RecyclerView.ViewHolder {
    public ImageView productImage;
    public TextView shopName;
    public TextView productName;
    public TextView quantity;
    public TextView price;
    public TextView address;
    public TextView reviews;

    public CustomerHolder(@NonNull View itemView) {
        super(itemView);
        productImage = (ImageView)itemView.findViewById(R.id.customer_product_image);
        shopName = (TextView) itemView.findViewById(R.id.customer_shop_name);
        productName = (TextView) itemView.findViewById(R.id.customer_product_name);
        quantity = (TextView) itemView.findViewById(R.id.customer_quantity);
        price = (TextView) itemView.findViewById(R.id.customer_price);
        address = (TextView) itemView.findViewById(R.id.customer_address);
        reviews= (TextView) itemView.findViewById(R.id.reviews);
    }
}
