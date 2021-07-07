package com.example.foodstuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import Customer.CustomerHomeActivity;
import Retailer.RetailerHomeActivity;

public class ManageOTP extends AppCompatActivity
{


    EditText mEnterOTP;
    Button mVerify;
    String phoneNumber,mCategory;
    private FirebaseAuth mAuth;
    String otpId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_o_t_p);

        mVerify = findViewById(R.id.button_verify);
        mEnterOTP = findViewById(R.id.enter_otp);

        phoneNumber=getIntent().getStringExtra("mobile").toString();



        mAuth = FirebaseAuth.getInstance();

        initiateotp();


        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEnterOTP.getText().toString().isEmpty())
                    Toast.makeText(ManageOTP.this, "Blank Field cannot be processed", Toast.LENGTH_SHORT).show();
                else if(mEnterOTP.getText().toString().length()!=6)
                {
                    Toast.makeText(ManageOTP.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId,mEnterOTP.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void initiateotp()
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                otpId = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                            {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(ManageOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(ManageOTP.this, "OTP is correct", Toast.LENGTH_SHORT).show();
                            if(setProfileData.getProfileCategory().equals("wholesaler"))
                                startActivity(new Intent(ManageOTP.this,HomeActivity.class));
                            else if(setProfileData.getProfileCategory().equals("retailer"))
                                startActivity(new Intent(ManageOTP.this, RetailerHomeActivity.class));
                            else
                                startActivity(new Intent(ManageOTP.this, CustomerHomeActivity.class));


                            // Update UI
                        }
                        else
                        {
                            Toast.makeText(ManageOTP.this, "Verification code was invalid", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}