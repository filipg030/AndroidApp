package com.example.jwtapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.jwtapp.R;
import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.FragmentLoginBinding;
import com.example.jwtapp.model.LoginSuccessResponse;
import com.example.jwtapp.model.User;
import com.example.jwtapp.viewmodel.LoggedUserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends Fragment {
    private LoggedUserViewModel loggedUserViewModel;
    private FragmentLoginBinding fragmentLoginBinding;
    public void replaceFragment(Fragment fragment) {

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(getLayoutInflater());
        View view = fragmentLoginBinding.getRoot();
        fragmentLoginBinding.registerButton.setOnClickListener(v -> {
            Register fragment3 = new Register();
            replaceFragment(fragment3);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.55.110:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);

        loggedUserViewModel = new LoggedUserViewModel();


        fragmentLoginBinding.loginButton.setOnClickListener(v -> {
            if(fragmentLoginBinding.passwordEditText.getText().length() >= 3) {
                User user = new User(fragmentLoginBinding.emailEditText.getText().toString(), fragmentLoginBinding.passwordEditText.getText().toString());

                Call<LoginSuccessResponse> call = (Call<LoginSuccessResponse>) projectAPI.postLoginData(user);
                call.enqueue(new Callback<LoginSuccessResponse>() {
                    @Override
                    public void onResponse(Call<LoginSuccessResponse> call, Response<LoginSuccessResponse> response) {
                        if (response.isSuccessful()) {
                            LoginSuccessResponse loginResponse = response.body();
                            if (loginResponse != null) {
                                String message = loginResponse.getMessage();
                                String token = loginResponse.getToken();
                                if (message.equals("User nie zarejestrowany")) {
                                    fragmentLoginBinding.infobox.setText("User not registered.");

                                } else if (message.equals("Niepoprawne hasło")) {
                                    fragmentLoginBinding.infobox.setText("Incorrect login details.");
                                } else if (message.equals("user nie potwierdzony")) {
                                    fragmentLoginBinding.infobox.setText("Account not confirmed.");
                                } else if (message.equals("user zalogowany!!!")) {
                                    // go to app
                                    loggedUserViewModel.setupLoggedUser(fragmentLoginBinding.emailEditText.getText().toString());
                                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                                    intent.putExtra("user", fragmentLoginBinding.emailEditText.getText().toString());
                                    intent.putExtra("token", loginResponse.getToken().toString());
                                    startActivity(intent);
                                } else if (message.equals("Wszedł user nie istnieje")){
                                    fragmentLoginBinding.infobox.setText("User not confirmed");
                                } else {
                                    fragmentLoginBinding.infobox.setText("Something went wrong.");
                                }

                            }
                        } else {
                            Log.d("xxx", "fail" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccessResponse> call, Throwable throwable) {

                    }
                });
            } else {
                fragmentLoginBinding.infobox.setText("Password must be at least 3 characters long.");
            }
        });

        return view;
    }
}