package com.example.jwtapp.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jwtapp.R;
import com.example.jwtapp.databinding.ActivityPhotoBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class PhotoActivity extends AppCompatActivity {

    private ActivityPhotoBinding activityPhotoBinding;
    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO"};

    private int PERMISSIONS_REQUEST_CODE = 100;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private PreviewView previewView;


    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(activityPhotoBinding.preview.getDisplay().getRotation())
                        .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(activityPhotoBinding.preview.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    checkIfPermissionsGranted();
                    break;
                }
            }
            if (allPermissionsGranted) {
                Log.d("xxx", "Storage permissions granted");
                cameraProviderFuture = ProcessCameraProvider.getInstance(PhotoActivity.this);
                cameraProviderFuture.addListener(() -> {
                    try {
                        ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                        bindPreview(cameraProvider);
                    } catch (InterruptedException | ExecutionException e) {
                        // No errors need to be handled for this Future. This should never be reached.
                    }
                }, ContextCompat.getMainExecutor(this));
            } else {
                Log.d("xxx", "Storage Permissions denied");
            }
        }
    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_photo);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;




        activityPhotoBinding = ActivityPhotoBinding.inflate(getLayoutInflater());
        View view = activityPhotoBinding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user").toString();
        String token = bundle.getString("token").toString();
        if (!checkIfPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        } else {
            cameraProviderFuture = ProcessCameraProvider.getInstance(PhotoActivity.this);
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (InterruptedException | ExecutionException e) {
                    // No errors need to be handled for this Future. This should never be reached.
                }
            }, ContextCompat.getMainExecutor(this));
        }


        activityPhotoBinding.cancelbutton.setOnClickListener(v -> {
            cameraProviderFuture.addListener(() -> {
                        try {
                            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                            cameraProvider.unbindAll();
                            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace(); // Log any errors
                        }
                    }, ContextCompat.getMainExecutor(this));
        });


    }



}
