package com.sonderben.sdbvideo.ui.sign_up;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.Country;
import com.sonderben.sdbvideo.data.model.UserLogin;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage3Binding;
import com.sonderben.sdbvideo.repository.UserSignUpRepository;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUpPage3Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SignUpPage3Fragment() {
        // Required empty public constructor
    }


    public static SignUpPage3Fragment newInstance(String param1, String param2) {
        SignUpPage3Fragment fragment = new SignUpPage3Fragment();
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

        binding=FragmentSignUpPage3Binding.inflate(getLayoutInflater());
        View root=binding.getRoot(); //inflater.inflate(R.layout.fragment_sign_up_page4, container, false);
        birthday=binding.birthday;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        }
        else
             locale = getResources().getConfiguration().locale;

        datePickerDialog=new DatePickerDialog(getContext(),
                R.style.Theme_MaterialComponents_Light_Dialog_MinWidth,onDateSetListener,
                2014,12,12);
        signUpViewModel=new ViewModelProvider(this.getActivity()).get(SignUpViewModel.class);


        birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                    datePickerDialog.show();
                    birthday.clearFocus();

                    /*InputMethodManager imgr= (InputMethodManager) SignUpPage3Fragment.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    imgr.hideSoftInputFromWindow(birthday.getApplicationWindowToken(),0);*/
                }
            }
        });


/*
        DatePicker datePicker= binding.datePicker;
        Long adult18=18*360*24*60*60*1000L;
        datePicker.setMaxDate(new Date().getTime()-adult18);

        spinner= binding.spinnerGender;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("SEX",(String) spinner.getSelectedItem());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            });
        }*/

        String[] type = new String[] {"Man", "Woman"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this.getContext(),
                        R.layout.dropdown_menu_popup_item,
                        type);

        AutoCompleteTextView editTextFilledExposedDropdown =
                root.findViewById(R.id.autoComplete);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String textView= (String) adapterView.getItemAtPosition(position);
                signUpViewModel.setUserSex(textView);
            }
        });



        return root;
    }
    FragmentSignUpPage3Binding binding;
    SignUpViewModel signUpViewModel;
    //Spinner spinner;
    ArrayAdapter<String> adapter;
    Intent intent=new Intent("SIGN_UP");
    TextInputEditText birthday;
    //DatePicker datePicker;
    Locale locale;
    DateFormat dateFormat;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar calendar=new GregorianCalendar(year,month,day);
            dateFormat=DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            String stringDate= dateFormat.format(calendar.getTime());

            birthday.setText(stringDate);
            signUpViewModel.setUserBirthday(null);

            //Toast.makeText(SignUpPage3Fragment.this.getContext(),"edittext click",Toast.LENGTH_SHORT).show();
        }
    };



}