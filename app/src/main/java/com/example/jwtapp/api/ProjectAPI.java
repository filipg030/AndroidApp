package com.example.jwtapp.api;

import com.example.jwtapp.model.AuthResponse;
import com.example.jwtapp.model.LoginSuccessResponse;
import com.example.jwtapp.model.LogoutResponse;
import com.example.jwtapp.model.RegisterResponse;
import com.example.jwtapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectAPI {
    @POST("api/register")
    Call<RegisterResponse> postRegisterData(@Body User user);

    @POST("api/login")
    Call<LoginSuccessResponse> postLoginData(@Body User user);

    @POST("api/auth")
    Call<AuthResponse> postAuthData(@Header("Authorization") String token);

    @GET("api/logout/{token}")
    Call<LogoutResponse> logout(@Path("token") String token);

}
