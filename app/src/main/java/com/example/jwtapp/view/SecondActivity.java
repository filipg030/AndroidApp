package com.example.jwtapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jwtapp.R;
import com.example.jwtapp.api.ProjectAPI;
import com.example.jwtapp.databinding.ActivitySecondBinding;
import com.example.jwtapp.model.LoginSuccessResponse;
import com.example.jwtapp.model.LogoutResponse;
import com.example.jwtapp.model.Post;
import com.example.jwtapp.model.PostAdapter;
import com.example.jwtapp.viewmodel.LoggedUserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding activitySecondBinding;
    private LoggedUserViewModel loggedUserViewModel;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        activitySecondBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = activitySecondBinding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user").toString();
        this.token = bundle.getString("token").toString();
        loggedUserViewModel = new LoggedUserViewModel();
        loggedUserViewModel.setupLoggedUser(user);
        activitySecondBinding.setLoggedUserViewModel(loggedUserViewModel);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy Data
        postList = new ArrayList<>();
        postList.add(new Post("First Post", "This is the first post content."));
        postList.add(new Post("Second Post", "Here is some more text for the second post."));
        postList.add(new Post("Another Post", "More details about another post."));

        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);

        Toolbar toolbar = activitySecondBinding.topToolbar;
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionlogout) {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.55.110:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);

            Call<LogoutResponse> call = (Call<LogoutResponse>) projectAPI.logout(this.token);
            call.enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    if(!response.isSuccessful()){
                        Log.d("xxx", "wrong lol");
                    }
                    else {
                        LogoutResponse logoutResponse = response.body();
                        Log.d("xxx", logoutResponse.getMessage().toString());
                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable throwable) {

                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}