package com.example.jwtapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jwtapp.R;
import com.example.jwtapp.databinding.FragmentLoginRegisterBinding;

public class Login_register extends Fragment {
    private FragmentLoginRegisterBinding fragmentLoginRegisterBinding;

    public void replaceFragment(Fragment fragment) {

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentLoginRegisterBinding = FragmentLoginRegisterBinding.inflate(getLayoutInflater());
        View view = fragmentLoginRegisterBinding.getRoot();
        fragmentLoginRegisterBinding.loginbutton.setOnClickListener(v -> {
            Login fragment2 = new Login();
            replaceFragment(fragment2);
        });
        fragmentLoginRegisterBinding.registerbutton.setOnClickListener(v -> {
            Register fragment5 = new Register();
            replaceFragment(fragment5);
        });
        return view;


    }
}