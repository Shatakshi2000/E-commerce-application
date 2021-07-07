package AddProduct;

public class dataHolder
{
     String name;
     String price;
     String quantity;
     String image;
     String username;
     String key;

    dataHolder()
    {

    }

    public dataHolder(String name, String price, String quantity, String image,String username,String key) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;
        this.key=key;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public  String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public  String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public  String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
