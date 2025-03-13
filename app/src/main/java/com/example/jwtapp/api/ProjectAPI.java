package com.example.jwtapp.api;

import com.example.jwtapp.model.AuthResponse;
import com.example.jwtapp.model.LoginResponse;
import com.example.jwtapp.model.RegisterResponse;
import com.example.jwtapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProjectAPI {
    @POST("api/register")
    Call<RegisterResponse> postRegisterData(@Body User user);

    @POST("api/login")
    Call<LoginResponse> postLoginData(@Body User user);

    @POST("api/auth")
    Call<AuthResponse> postAuthData(@Header("Authorization") String token);

}
