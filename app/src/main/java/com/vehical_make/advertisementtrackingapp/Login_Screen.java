package com.vehical_make.advertisementtrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vehical_make.advertisementtrackingapp.databinding.ActivityLoginScreenBinding;

public class Login_Screen extends AppCompatActivity {
    ActivityLoginScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Screen.this, Sign_Up_Screen.class);
                startActivity(intent);
            }
        });

    }
}