package com.sonderben.sdbvideo.ui.sign_up;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage6Binding;


public class SignUpPage6Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpPage6Fragment() {
        // Required empty public constructor
    }


    public static SignUpPage6Fragment newInstance(String param1, String param2) {
        SignUpPage6Fragment fragment = new SignUpPage6Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSignUpPage6Binding.inflate(getLayoutInflater());
        View root=binding.getRoot();

       return root;
    }
    FragmentSignUpPage6Binding binding;

}