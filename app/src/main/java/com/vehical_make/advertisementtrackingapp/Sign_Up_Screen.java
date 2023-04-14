package com.vehical_make.advertisementtrackingapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.vehical_make.advertisementtrackingapp.databinding.ActivitySignUpScreenBinding;

public class Sign_Up_Screen extends AppCompatActivity {
    ActivitySignUpScreenBinding binding;
ActivityResultLauncher<String> launcherFront, launcherBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       frontLauncherFunction();
       backLauncherFunction();

    // add image btn listner
        binding.frontAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherFront.launch("image/*");
            }
        });
        binding.backAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherBack.launch("image/*");
            }
        });

    // sign up btn click
    binding.signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Sign_Up_Screen.this, Permission_Activity.class));
        }
    });

    }
    void frontLauncherFunction(){
        launcherFront = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.cnicFront.setImageURI(result);
            }
        });
    }
    void backLauncherFunction(){
        launcherBack = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.cnicback.setImageURI(result);
            }
        });
    }
}