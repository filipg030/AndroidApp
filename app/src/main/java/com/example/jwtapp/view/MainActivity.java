package com.example.jwtapp.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jwtapp.R;
import com.example.jwtapp.databinding.ActivityMainBinding;
import com.example.jwtapp.viewmodel.LoggedUserViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private Button registerbutton;
    private Button loginbutton;
    private LoggedUserViewModel loggedUserViewModel;


    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    public static class ThemeUtils {
        public static boolean isDarkModeEnabled(Context context) {
            int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ThemeUtils.isDarkModeEnabled(this)) {
            System.out.println("Dark mode is ON");
        } else {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);

        loggedUserViewModel = new LoggedUserViewModel();
        loggedUserViewModel.setupLoggedUser("none");


        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        activityMainBinding.setLoggedUserViewModel(loggedUserViewModel);

        Login_register fragment1 = new Login_register();
        replaceFragment(fragment1);

    }

}