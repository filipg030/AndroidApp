package com.example.jwtapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jwtapp.databinding.FragmentLoginRegisterBinding;

public class Login_register extends Fragment {
    private FragmentLoginRegisterBinding fragmentLoginRegisterBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentLoginRegisterBinding = FragmentLoginRegisterBinding.inflate(getLayoutInflater());
        View view = fragmentLoginRegisterBinding.getRoot();
        return view;
    }
}