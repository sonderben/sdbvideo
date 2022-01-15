package com.sonderben.sdbvideo.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage2Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPage2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPage2Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SignUpPage2Fragment() {
        // Required empty public constructor
    }



    public static SignUpPage2Fragment newInstance(String param1, String param2) {
        SignUpPage2Fragment fragment = new SignUpPage2Fragment();
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
        binding=FragmentSignUpPage2Binding.inflate(getLayoutInflater());
        View root=binding.getRoot();

        firstName= binding.firstName;
        TextWatcher textWatcherFirstName=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                intent.putExtra("FIRST_NAME",firstName.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        };
        firstName.addTextChangedListener(textWatcherFirstName);

        lastName= binding.lastName;
        TextWatcher textWatcherLastName=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                intent.putExtra("LAST_NAME",lastName.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        };
        lastName.addTextChangedListener(textWatcherLastName);
        return root;
    }
    FragmentSignUpPage2Binding binding;
    EditText firstName,lastName;
    Intent intent=new Intent("SIGN_UP");
}