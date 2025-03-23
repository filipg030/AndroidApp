package com.example.jwtapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jwtapp.R;
import com.example.jwtapp.databinding.FragmentLoginBinding;


public class Login extends Fragment {

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
        return view;
    }
}