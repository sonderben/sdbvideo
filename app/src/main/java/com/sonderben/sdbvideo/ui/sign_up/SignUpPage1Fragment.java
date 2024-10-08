package com.sonderben.sdbvideo.ui.sign_up;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.ErrorMessage;
import com.sonderben.sdbvideo.data.model.UserLogin;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage1Binding;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage4Binding;
import com.sonderben.sdbvideo.repository.UserSignUpRepository;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUpPage1Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int NUM_ROW = 5;
    private static final int NUM_ColUMN=4;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SignUpPage1Fragment() {
        // Required empty public constructor
    }


    public static SignUpPage1Fragment newInstance(String param1, String param2) {
        SignUpPage1Fragment fragment = new SignUpPage1Fragment();
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
        page1Binding=FragmentSignUpPage1Binding.inflate(getLayoutInflater());
        View view= page1Binding.getRoot();
        free=page1Binding.free;
        standard=page1Binding.rbStandard;
        premium=page1Binding.rbPremium;
         layout =  page1Binding.tab;

        signUpViewModel=new ViewModelProvider(this.getActivity()).get(SignUpViewModel.class);

        setRadioButtonEnabled(false);
        free.setOnClickListener(x->{
            standard.setChecked(false);
            premium.setChecked(false);
            signUpViewModel.setUserAccess(accesses.get(0));
            checkedPlan(1,oldSelectedRadiobutton);
            oldSelectedRadiobutton=1;
        });
        standard.setOnClickListener(x->{
            free.setChecked(false);
            premium.setChecked(false);
            signUpViewModel.setUserAccess(accesses.get(1));
            checkedPlan(2,oldSelectedRadiobutton);
            oldSelectedRadiobutton=2;
        });
        premium.setOnClickListener(x->{
            free.setChecked(false);
            standard.setChecked(false);
            signUpViewModel.setUserAccess(accesses.get(2));
            checkedPlan(3,oldSelectedRadiobutton);
            oldSelectedRadiobutton=3;
        });

        getAccess();
       /* TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                intent.putExtra("EMAIL",mEmail.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        };
        mEmail.addTextChangedListener(textWatcher);


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Utils.isEmailValid(mEmail.getText().toString())){
                    mInfoEmail.setVisibility(View.INVISIBLE);
                }
                else {
                    mInfoEmail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEmail.addTextChangedListener(afterTextChangedListener);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //float price=accessList.get(i).getPrice()==null?0.0:accessList.get(i).getPrice();
                if(i>0){
                    info.setVisibility(View.VISIBLE);
                    String pub=accessList.get(i-1).getPrice()>0?"No":"Yes";
                    info.setText("Name: "+accessList.get(i-1).getName().toUpperCase()+
                            "\nNum. of profile: "+accessList.get(i-1).getNumOfScreen()+
                            "\nPrice: "+accessList.get(i-1).getPrice()+"USD"+
                            "\nPub: "+pub);

                    intent.putExtra("ID_ACCESS",accessList.get(i-1).getId());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
                else {
                    info.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/






        return  view;
    }

    FragmentSignUpPage1Binding page1Binding;
    RadioButton free,standard,premium;
    SignUpViewModel signUpViewModel;
    ArrayAdapter<String> adapter ;
    TextView mInfoEmail;
    Spinner spinner;
    EditText info,mEmail;
    List<Access>accesses;
    ProgressBar progressBar;
    TableLayout layout;
    ColorStateList defaultColor;
    int oldSelectedRadiobutton=-1;

    private void getAccess() {
        Retrofit retrofit =Utils.getInstanceRetrofit();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Access>> call = repository.getAccesses();
        call.enqueue(new Callback<List<Access>>() {
            @Override
            public void onResponse(Call<List<Access>> call, Response<List<Access>> response) {
                if(response.isSuccessful()){
                     layout =  page1Binding.tab;
                    accesses=response.body();

                    for (int i = 1; i < 6; i++) {
                        View row = layout.getChildAt(i);

                        if (row instanceof TableRow) {
                            TableRow tempRow = (TableRow) row;

                            for (int x = 1; x < accesses.size()+1; x++) {
                                View view = tempRow.getChildAt(x);
                                if (view instanceof TextView) {
                                   TextView textView= (TextView) view;
                                   if(i==1) {
                                       textView.setText(accesses.get(x - 1).getName());
                                   }
                                    if(i==2) {
                                        textView.setText(accesses.get(x - 1).getNumOfScreen()+"");
                                    }
                                    if(i==3) {//pub}
                                        textView.setText(accesses.get(x - 1).getName().equalsIgnoreCase("free") ? "Yes" : "No");
                                    }
                                    if(i==4) {
                                        textView.setText(accesses.get(x - 1).getPrice() + "");
                                    }
                                    if(i==5) {
                                        textView.setText(accesses.get(x - 1).getQualityImage());
                                    }
                                }

                            }
                        }
                    }
                    setRadioButtonEnabled(true);

                }
                else {
                    Gson gson=new Gson();
                    ErrorMessage errorMessage;
                   try {
                        info.setText(response.errorBody().string());
                        JSONObject jsonError=new JSONObject(response.errorBody().string());
                        String message= jsonError.getString("error");
                       //errorMessage= gson.fromJson(response.errorBody().string(), ErrorMessage.class);
                        Toast.makeText(SignUpPage1Fragment.this.getContext(),"error: "+message,Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(SignUpPage1Fragment.this.getContext(),"error io: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Toast.makeText(SignUpPage1Fragment.this.getContext(),"error json: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    System.out.println(response.message());
                    info.setText(response.message());

                }
            }

            @Override
            public void onFailure(Call<List<Access>> call, Throwable t) {
                Toast.makeText(SignUpPage1Fragment.this.getContext(),"error failure",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setRadioButtonEnabled(boolean b){
        free.setEnabled(b);
        standard.setEnabled(b);
        premium.setEnabled(b);
    }
    private void checkedPlan(int x,int old){
        for (int i = 1; i < 6; i++) {
            View row = layout.getChildAt(i);

            if (row instanceof TableRow) {
                TableRow tempRow = (TableRow) row;

                    View view = tempRow.getChildAt(x);
                    if (view instanceof TextView) {
                        TextView textView= (TextView) view;
                        defaultColor=textView.getTextColors();
                       textView.setTextColor(getResources().getColor(R.color.teal_200));
                       textView.setTypeface(Typeface.DEFAULT_BOLD);
                    }

            }

            if (row instanceof TableRow && old >= 0) {
                TableRow tempRow = (TableRow) row;

                View view = tempRow.getChildAt(old);
                if (view instanceof TextView) {
                    TextView textView= (TextView) view;
                    textView.setTextColor(defaultColor);
                    textView.setTypeface(Typeface.DEFAULT);
                }

            }
        }
    }
}