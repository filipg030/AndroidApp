package com.example.jwtapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jwtapp.R;
import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.FragmentRegisterBinding;
import com.example.jwtapp.model.RegisterResponse;
import com.example.jwtapp.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends Fragment {

    private FragmentRegisterBinding fragmentRegisterBinding;
    public void replaceFragment(Fragment fragment) {

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View view = fragmentRegisterBinding.getRoot();
        fragmentRegisterBinding.loginButton.setOnClickListener(v -> {
            Login fragment4 = new Login();
            replaceFragment(fragment4);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.55.111:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);

        fragmentRegisterBinding.registerbutton.setOnClickListener(v -> {
            if(fragmentRegisterBinding.passwordedittext.getText().length() >= 3) {
                User user = new User(fragmentRegisterBinding.emailedittext.getText().toString(), fragmentRegisterBinding.passwordedittext.getText().toString());
                Call<RegisterResponse> call = projectAPI.postRegisterData(user);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("xxx", String.valueOf(response.code()));
                            return;
                        } else {
                            RegisterResponse registerResponse = response.body();
                            Log.d("xxx", registerResponse.getMessage());

                            if (!Objects.equals(registerResponse.getMessage().toString(), "User już istnieje")) {
                                fragmentRegisterBinding.infobox.setText("Success! Click below to verify your account.");
                                fragmentRegisterBinding.infobox.setTextColor(Color.parseColor("#008000"));
                                fragmentRegisterBinding.loginButton.setText("Click here to verify!");
                                fragmentRegisterBinding.loginButton.setOnClickListener(v -> {
                                    String url = registerResponse.getMessage();
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                    Login fragment4 = new Login();
                                    replaceFragment(fragment4);
                                });
                            } else {
                                fragmentRegisterBinding.infobox.setText("User already exists. Please log in.");

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                        Log.d("xxx", throwable.getMessage());
                    }
                });
            } else {
                fragmentRegisterBinding.infobox.setText("Password must be at least 3 characters long");
            }

        });
        return view;
    }
}