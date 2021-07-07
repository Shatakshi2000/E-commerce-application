package Firebase.Recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstuff.R;

public class CurrentOrderHolder extends RecyclerView.ViewHolder {

    public ImageView productImage;
    public TextView productName;
    public TextView quantity;
    public TextView price;
    public TextView number;
    public TextView address;
    public TextView phoneNumber;
    public Button orderConfirmed;


    public CurrentOrderHolder(@NonNull View itemView) {
        super(itemView);

        productImage = (ImageView)itemView.findViewById(R.id.current_order_product_image);
        productName = (TextView) itemView.findViewById(R.id.current_order_product_name);
        quantity = (TextView) itemView.findViewById(R.id.current_order_quantity);
        price = (TextView) itemView.findViewById(R.id.current_order_price);
        address = (TextView) itemView.findViewById(R.id.current_orders_address);
        phoneNumber = (TextView) itemView.findViewById(R.id.current_orders_phone_number);
        number = (TextView) itemView.findViewById(R.id.current_order_number);
        orderConfirmed=(Button)itemView.findViewById(R.id.confirm_order);
    }
}
