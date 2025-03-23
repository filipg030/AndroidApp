package com.example.jwtapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jwtapp.R;
import com.example.jwtapp.databinding.FragmentRegisterBinding;

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
        return view;
    }
}