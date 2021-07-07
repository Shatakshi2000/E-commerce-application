package AddProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodstuff.R;
import com.example.foodstuff.setProfileData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import Categories.FruitActivity;

public class AddNewProduct extends AppCompatActivity {

    private Button mAddProduct;
    private ImageView mNewProductImage;
    private EditText mNewProductName,mNewPrice,mNewQuantity,mKey;
    private TextView mCategory;
    private String Name,Price,Quantity,Key;
    Uri filepath;
    Bitmap bitmap;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        mAddProduct = (Button) findViewById(R.id.button_add_product);
        mNewProductImage=(ImageView) findViewById(R.id.new_product_image);
        mNewProductName=(EditText) findViewById(R.id.new_product_name);
        mNewPrice=(EditText) findViewById(R.id.new_price);
        mNewQuantity=(EditText) findViewById(R.id.new_quantity);
        mCategory = (TextView) findViewById(R.id.Category);
        mKey = (EditText) findViewById(R.id.new_key);

        Intent intent= getIntent(); // getIntent() is used to recieve the intent
        str = intent.getStringExtra( "message_key");/*getStringExtra is used to get the data(key, here the key is "message_key") which is send by putExtra() method*/

        mCategory.setText(str);



        mNewProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AddNewProduct.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
                            {
                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken)
                            {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = mNewProductName.getText().toString();
                Price = mNewPrice.getText().toString();
                Quantity = mNewQuantity.getText().toString();
                Key = mKey.getText().toString();



                if (TextUtils.isEmpty(Name))
                {
                    Toast.makeText(AddNewProduct.this,"Product name is mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Price))
                {
                    Toast.makeText(AddNewProduct.this,"Product price is mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Quantity))
                {
                    Toast.makeText(AddNewProduct.this,"Product quantity is mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Key))
                {
                    Toast.makeText(AddNewProduct.this,"Product key is mandatory",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadToFirebase();
                }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath = data.getData();
            try
                {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    mNewProductImage.setImageBitmap(bitmap);
                }
            catch(Exception ex)
            {

            }
        }
    }

    private void uploadToFirebase()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading file");
        dialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference uploader = storage.getReference("image1"+ new Random().nextInt(50));
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {

                        dialog.dismiss();

                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                database.getReference("profile").child(setProfileData.getProfilePhoneNumber()).child("category").
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String category = snapshot.child("category").getValue().toString();
                                                DatabaseReference databaseReference = database.getReference("users").child(category).child(str);
                                                dataHolder obj = new dataHolder(Name,Price,Quantity,uri.toString(),"shop name",Key);
                                                databaseReference.child(Key).setValue(obj);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });




                                Toast.makeText(getApplicationContext(),"Image uploaded to firebase",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), FruitActivity.class));

                            }
                        });


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                    {
                        float percent = (100)*snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : " +(int)percent + "%");
                    }
                });
    }

}