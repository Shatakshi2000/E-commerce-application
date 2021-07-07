package com.example.foodstuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.hbb20.CountryCodePicker;

import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.*;


public class Login extends AppCompatActivity  {

    private EditText mUsername, mPhoneNumber, mPassword, mRetypePassword, mAddress;
    private Button mRegisterBtn;
    private TextView mAlreadyAccount, currentLocation;
    private ProgressDialog loadingBar;
    Spinner spn;
    String text;
    private CountryCodePicker countryCodePicker;
    private FirebaseAuth fAuth;
    TextView location1;

    LocationManager locationManager;
    private double latitude, longitude;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUsername = (EditText) findViewById(R.id.username);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mPassword = (EditText) findViewById(R.id.password);
        mRegisterBtn = (Button) findViewById(R.id.button_register);
        mRetypePassword = (EditText) findViewById(R.id.retype_password);
        mAlreadyAccount = (TextView) findViewById(R.id.text_existing_account);
        loadingBar = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance(); // connecting user to the firebase authentication
        spn = findViewById(R.id.category_spinner);
        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(mPhoneNumber);
        mAddress = (EditText) findViewById(R.id.address);
        location1 = findViewById(R.id.get_location);


        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spinner_adapter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                Login.this
        );
        //Runtime permissions


        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(Login.this
                        , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }


            }
        });


        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = spn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();

            }
        });

        mAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExistingActivity.class);
                startActivity(intent);
            }
        });
    }


    private void CreateAccount() {
        String username = mUsername.getText().toString().trim();
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String retypePassword = mRetypePassword.getText().toString().trim();
        String address = mAddress.getText().toString().trim();


        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumber.setError("Phone number cannot be empty");
            return;
        } else if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password cannot be empty");
            return;
        } else if (!retypePassword.equals(password)) {
            Toast.makeText(Login.this, "Retype password and password are not same", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6) {
            mPassword.setError("password should contain minimum of 6 characters");
            return;
        } else if (TextUtils.isEmpty(address)) {
            mPassword.setError("Address cannot be empty cannot be empty");
            return;
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            new setProfileData(phoneNumber, text, username, password, address);
            StoreInfo(phoneNumber, password, username, address);
        }
    }


    private void StoreInfo(String phoneNumber, String password, String username, String address) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("profile");

        ProfileHolder obj1 = new ProfileHolder(text, "#", username, password, address);
        databaseReference.child(phoneNumber.replace(" ", "")).setValue(obj1);

        Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
        loadingBar.dismiss();


        Intent intent = new Intent(getApplicationContext(), ManageOTP.class);
        intent.putExtra("mobile", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));

        startActivity(intent);

    }


    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                LocationCallback locationCallback;
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        System.out.println(longitude);
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                double latitude = location1.getLatitude();
                                double longitude = location1.getLongitude();
                                System.out.println(longitude);

                            }
                        };

                        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });


        };
    }
}






