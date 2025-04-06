package com.example.jwtapp.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jwtapp.R;
import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.ActivityPhotoBinding;
import com.example.jwtapp.model.Post;
import com.example.jwtapp.model.PostResponse;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoActivity extends AppCompatActivity {

    private ActivityPhotoBinding activityPhotoBinding;
    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                    Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE};

    private int PERMISSIONS_REQUEST_CODE = 100;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private PreviewView previewView;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture = new ImageCapture.Builder()
                .setTargetRotation(activityPhotoBinding.preview.getDisplay().getRotation())
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

        activityPhotoBinding.postbutton.setOnClickListener(v -> {
            File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photos");
            if (!dir.exists() && !dir.mkdirs()) {
                Log.e("PhotoActivity", "Failed to create directory");
                return;
            }

            File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");

            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            Log.d("PhotoActivity", "Photo saved at: " + photoFile.getAbsolutePath());
                            Toast.makeText(PhotoActivity.this, "Photo saved!", Toast.LENGTH_SHORT).show();
                            if (photoFile.exists()) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://192.168.55.112:8080")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);


                                RequestBody fileRequest =
                                        RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

                                MultipartBody.Part body =
                                        MultipartBody.Part.createFormData("file", photoFile.getName(), fileRequest);

                                RequestBody album =
                                        RequestBody.create(MultipartBody.FORM, activityPhotoBinding.caption.getText().toString());

                                Call<PostResponse> call = projectAPI.sendImage("Bearer " + token, body, album);
                                call.enqueue(new Callback<PostResponse>() {
                                    @Override
                                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                        if(!response.isSuccessful()){
                                            Log.d("xxx", "shit fuck piss" + response.message());
                                        } else {
                                            Log.d("xxx", "sigma");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PostResponse> call, Throwable throwable) {

                                    }
                                });
                                Intent intent = new Intent(PhotoActivity.this, SecondActivity.class);
                                intent.putExtra("user", user);
                                intent.putExtra("token", token);
                                startActivity(intent);
                            } else {
                                Log.e("PhotoActivity", "Photo not found!");
                            }
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            Log.e("PhotoActivity", "Photo capture failed: " + exception.getMessage());
                        }
                    });



        });

        activityPhotoBinding.flipCamera.setOnClickListener(v -> {
            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();
            } else {
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
            }

            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    cameraProvider.unbindAll(); // Unbind all before rebinding
                    bindPreview(cameraProvider);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, ContextCompat.getMainExecutor(this));
        });

    }



}
