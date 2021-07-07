package Firebase.Recyclerview;


import androidx.annotation.Keep;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

@Keep
public class ProductModel implements Serializable {

    @ServerTimestamp
    private String name;
    private String price;
    private String quantity;
    private String image;
    private String username;



    public ProductModel() {

    }


    public ProductModel(String image, String name, String price, String quantity, String username,String address) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}


