package com.example.foodstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Customer.CustomerHomeActivity;
import Retailer.RetailerHomeActivity;


public class ExistingActivity extends AppCompatActivity {

    private EditText existing_mPhone, existing_mPassword;
    private Button existing_button_login;
    private TextView do_not_have_an_account;
    private FirebaseAuth fAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing);

        existing_mPhone = (EditText) findViewById(R.id.text_exsisting_phone);
        existing_mPassword = (EditText) findViewById(R.id.text_exsisting_password);

        existing_button_login = (Button) findViewById(R.id.button_login);
        do_not_have_an_account = (TextView)findViewById(R.id.text_signup);

        fAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        do_not_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        existing_button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exsisting_phone = existing_mPhone.getText().toString().trim();
                String exsisting_password = existing_mPassword.getText().toString().trim();




                if (TextUtils.isEmpty(exsisting_phone)) {
                    existing_mPhone.setError("Email cannot be empty");
                    return;
                }
                else if (TextUtils.isEmpty(exsisting_password)) {
                    existing_mPassword.setError("Password cannot be empty");
                    return;
                }
                else if (exsisting_password.length() < 6) {
                    existing_mPassword.setError("password should contain minimum of 6 characters");
                    return;
                }
                else {
                    loadingBar.setTitle("Loging into your account ");
                    loadingBar.setMessage("please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    if (fAuth.getCurrentUser() != null) {

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("profile").child(exsisting_phone);


                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ProfileHolder holder = dataSnapshot.getValue(ProfileHolder.class);
                                if(holder.getPassword().equals(exsisting_password)) {

                                    new setProfileData(exsisting_phone, holder.getCategory(), holder.getUsername(), holder.getPassword(), holder.getAddress());

                                    if (holder.getCategory().equals("wholesaler")) {
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    } else if (holder.getCategory().equals("retailer"))
                                        startActivity(new Intent(getApplicationContext(), RetailerHomeActivity.class));
                                    else
                                        startActivity(new Intent(getApplicationContext(), CustomerHomeActivity.class));
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"data not uploaded in the firebase",Toast.LENGTH_SHORT).show();

                            }
                        });

                        loadingBar.dismiss();
                        Toast.makeText(getApplicationContext(),"Logged in",Toast.LENGTH_SHORT).show();

                    } else
                        {
                            Toast.makeText(getApplicationContext(), "Account does not exsist ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                }
            }
        });
    }
}