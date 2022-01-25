package com.sonderben.sdbvideo.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage5Binding;


public class SignUpPage5Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SignUpPage5Fragment() {
        // Required empty public constructor
    }


    public static SignUpPage5Fragment newInstance(String param1, String param2) {
        SignUpPage5Fragment fragment = new SignUpPage5Fragment();
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
        // Inflate the layout for this fragment
        binding=FragmentSignUpPage5Binding.inflate(getLayoutInflater());
        password= binding.password;

        signUpViewModel=new ViewModelProvider(this.getActivity()).get(SignUpViewModel.class);


        confirmPassword= binding.confirmPassword;

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(password.getText().toString().trim().length()>3){
                    if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                       signUpViewModel.setUserPassword(password.getText().toString());
                    }

                }
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(password.getText().toString().trim().length()>3){
                    if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){

                        signUpViewModel.setUserPassword(password.getText().toString());
                    }

                }
            }
        });

        View root=binding.getRoot();
        return root;
    }

    FragmentSignUpPage5Binding binding;
    EditText password,confirmPassword;
    CheckBox isAllProfileCanCreateProfiles;
    SignUpViewModel signUpViewModel;
}