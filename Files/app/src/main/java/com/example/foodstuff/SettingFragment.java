package com.example.foodstuff;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import AddProduct.AddNewProduct;
import AddProduct.dataHolder;
import Categories.FruitActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    ImageView sProfileImage,sProfileImageEdit;
    TextView sProfileName,sProfilePassword,getsProfileAddress,changeSettings;

    Uri filepath;
    Bitmap bitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        sProfileImage = v.findViewById(R.id.setting_user_profile_image);

        sProfileName = v.findViewById(R.id.setting_username);
        sProfileName.setText(setProfileData.getProfileUsername());
        sProfilePassword = v.findViewById(R.id.setting_phone_number);
        sProfilePassword.setText(setProfileData.getProfilePassword());
        getsProfileAddress = v.findViewById(R.id.settings_address);
        getsProfileAddress.setText(setProfileData.getProfileAddress());

        sProfileImageEdit = v.findViewById(R.id.edit_user_profile_image);
        changeSettings = v.findViewById(R.id.edit_user_settings);

        sProfileImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity((Activity) getContext())
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

        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.setting_layout_content))
                        .setExpanded(true,1000)
                        .create();
                View myview  = dialogPlus.getHolderView();
                EditText editUsername = myview.findViewById(R.id.edit_username);
                EditText editPassword = myview.findViewById(R.id.edit_password);
                EditText editAddress = myview.findViewById(R.id.edit_address);
                Button buttonEdit = myview.findViewById(R.id.button_edit);

                editUsername.setText(setProfileData.getProfileUsername());
                editPassword.setText(setProfileData.getProfilePassword());
                editAddress.setText(setProfileData.getProfileAddress());


                dialogPlus.show();

                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map =  new HashMap<>();
                        map.put("username",editUsername .getText().toString());
                        map.put("password",editPassword.getText().toString());
                        map.put("address",editAddress.getText().toString());


                        FirebaseDatabase.getInstance().getReference("profile").child(setProfileData.getProfilePhoneNumber())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(getContext(),"Content updated successfully",Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(getContext(),"Update unsuccessful... Retry",Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                });

            }
        });
        return v;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath = data.getData();
            try
            {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                sProfileImage.setImageBitmap(bitmap);

                uploadToFirebase();
            }
            catch(Exception ex)
            {

            }
        }
    }

    private void uploadToFirebase() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Uploading file");
        dialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference uploader = storage.getReference("profile1"+ new Random().nextInt(50));
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {

                        dialog.dismiss();

                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Toast.makeText(getContext(),"Image uploaded to firebase",Toast.LENGTH_SHORT).show();


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