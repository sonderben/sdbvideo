package com.sonderben.sdbvideo.ui.sign_up;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.User;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage6Binding;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.ui.me.MeFragment;
import com.sonderben.sdbvideo.utils.Utils;

import java.text.DateFormat;
import java.util.Locale;


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

        signUpViewModel= new ViewModelProvider(this.getActivity()).get(SignUpViewModel.class);

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        }
        else
            locale = getResources().getConfiguration().locale;
        DateFormat dateFormat=DateFormat.getDateInstance(DateFormat.MEDIUM,locale);

        signUpViewModel.getUserLiveData().observe(this.getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                listView= binding.listviewSummarize;
                String[]keys={"Plan","Email","First name","Last name","Sex: ","Birthday","Tel:","Country: "
                        ,"State","City: ","Direction: ","Postal code: ","Password"};

                String[]values={user.getAccess().getName(),user.getEmail(),user.getFirstName(),
                        user.getLastName(),user.getSex(), "null"/*dateFormat.format( user.getBirthday().getTime() )*/,user.getTelephone(),
                user.getCountry(),user.getDepartment(),user.getCity(),user.getRegion(),
                        user.getPostalCode(),user.getPassword()};

                listView.setAdapter(new Adapter(keys, values,"$"+user.getAccess().getPrice()+"/Month"));
            }
        });




       return root;
    }

    FragmentSignUpPage6Binding binding;
    ListView listView;
    SignUpViewModel signUpViewModel;






    private class Adapter extends BaseAdapter {

        String []keys;
        String []values;
        final int SIGN_OUT=3;
        String price;
        public Adapter(String keys[], String []values,String price){
            this.keys=keys;
            this.values=values;
            this.price=price;
        }

        @Override
        public int getCount() {
            return keys.length;
        }

        @Override
        public Object getItem(int i) {
            return values[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            convertView=getLayoutInflater().inflate(R.layout.custum_listview_signup_summarize,parent,false);
            TextView key=convertView.findViewById(R.id.key);
            TextView value=convertView.findViewById(R.id.value);
            TextView value2=convertView.findViewById(R.id.value2);
            key.setText(keys[position]);
            value.setText(values[position]);
            if(position==0){
                value2.setText(price);
            }
            else {
                value2.setVisibility(View.GONE);
            }
            if(position==keys.length-1){
                value.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

            convertView.setOnClickListener(x->{

            });
            return convertView;
        }
    }
}