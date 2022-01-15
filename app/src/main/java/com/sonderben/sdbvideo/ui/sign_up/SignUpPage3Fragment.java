package com.sonderben.sdbvideo.ui.sign_up;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.Date;
import java.util.List;

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
        String []a={"SELECT SEX","MAN","WOMAN"};
         adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        datePicker= binding.datePicker;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(SignUpPage3Fragment.this.getContext()," Build.VERSION_CODES.O",Toast.LENGTH_LONG).show();
            datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {

                intent.putExtra("BIRTHDAY",i+" "+i1+" "+i2);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            });
        }else {
            datePicker.getCalendarView().setOnDateChangeListener((calendarView, i, i1, i2) -> {
                Toast.makeText(SignUpPage3Fragment.this.getContext()," menor version",Toast.LENGTH_LONG).show();

                intent.putExtra("BIRTHDAY",i+" "+i1+" "+i2);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            });
        }

        return root;
    }
    FragmentSignUpPage3Binding binding;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    Intent intent=new Intent("SIGN_UP");
   // DatePicker datePicker;



}