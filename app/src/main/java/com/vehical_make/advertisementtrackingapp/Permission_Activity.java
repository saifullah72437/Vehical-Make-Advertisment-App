package com.vehical_make.advertisementtrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Permission_Activity extends AppCompatActivity {
    private Button btnRequestPermissions;
    LocationManager locationManager;
    private Switch switchLocation, switchReadExternalStorage, switchCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        switchLocation = findViewById(R.id.switch_location);
        switchReadExternalStorage = findViewById(R.id.switch_read_external_storage);
        switchCamera = findViewById(R.id.switch_camera);
        btnRequestPermissions = findViewById(R.id.btn_request_permissions);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnRequestPermissions.setEnabled(false);

        if (checkAllPermissionsGranted()) {
            btnRequestPermissions.setEnabled(true);// Enable the button
             locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }

        requestPermissions();


        btnRequestPermissions.setOnClickListener(view -> {
            startActivity( new Intent(Permission_Activity.this, MainActivity.class));

        });
    }
    private boolean checkAllPermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // All permissions are granted
                            switchCamera.setChecked(true);
                            switchLocation.setChecked(true);
                            switchReadExternalStorage.setChecked(true);
                            btnRequestPermissions.setEnabled(true);
                            if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                            Toast.makeText(Permission_Activity.this, "Now click on next button!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Some permissions are not granted
                            List<PermissionDeniedResponse> deniedResponses = report.getDeniedPermissionResponses();
                            for (PermissionDeniedResponse response : deniedResponses) {
                                if (response.getPermissionName().equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                    // Location permission is not granted
                                    switchLocation.setChecked(false);
                                } else if (response.getPermissionName().equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                    // Read External Storage permission is not granted
                                    switchReadExternalStorage.setChecked(false);
                                } else if (response.getPermissionName().equals(Manifest.permission.CAMERA)) {
                                    // Camera permission is not granted
                                    switchCamera.setChecked(false);
                                }
                            }
                            Toast.makeText(Permission_Activity.this, "Some permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
}