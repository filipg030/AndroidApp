package com.example.jwtapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.ActivityMainBinding;
import com.example.jwtapp.model.RegisterResponse;
import com.example.jwtapp.model.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private EditText et1;
    private Button btSave;
    private Button btRegister;
    private Button btLogin;
    private EditText registerEmail;
    private EditText registerPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.119.102:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
//
//        activityMainBinding.btSave.setOnClickListener(v -> {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://" + activityMainBinding.et1.getText().toString() + "/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
//
//
//            });

        activityMainBinding.btRegister.setOnClickListener(v -> {
            User user = new User(activityMainBinding.registerEmail.getText().toString(), activityMainBinding.registerPass.getText().toString());
            Call<RegisterResponse> call = projectAPI.postRegisterData(user);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("xxx", String.valueOf(response.code()));
                        return;
                    }
                    else
                    {
                        Log.d("xxx", response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                    Log.d("xxx", throwable.getMessage());
                }
            });

        });







    }





}