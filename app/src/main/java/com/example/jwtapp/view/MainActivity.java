package com.example.jwtapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.jwtapp.R;
import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.ActivityMainBinding;
import com.example.jwtapp.databinding.FragmentLoginBinding;
import com.example.jwtapp.databinding.FragmentLoginRegisterBinding;
import com.example.jwtapp.model.RegisterResponse;
import com.example.jwtapp.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private Button registerbutton;
    private Button loginbutton;
    private EditText registerEmail;
    private EditText registerPass;
    private FragmentLoginRegisterBinding fragmentLoginRegisterBinding;
    private FragmentLoginBinding fragmentLoginBinding;

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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


        Login_register fragment1 = new Login_register();
        replaceFragment(fragment1);





//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.119.102:8080")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);



//        fragmentLoginRegisterBinding.registerbutton.setOnClickListener(v -> {
//            User user = new User(fragmentLoginRegisterBinding.registerbutton.getText().toString(), fragmentLoginRegisterBinding.registerbutton.getText().toString());
//            Call<RegisterResponse> call = projectAPI.postRegisterData(user);
//            call.enqueue(new Callback<RegisterResponse>() {
//                @Override
//                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                    if (!response.isSuccessful()) {
//                        Log.d("xxx", String.valueOf(response.code()));
//                        return;
//                    }
//                    else
//                    {
//                        Log.d("xxx", response.body().toString());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
//                    Log.d("xxx", throwable.getMessage());
//                }
//            });
//
//        });







    }





}