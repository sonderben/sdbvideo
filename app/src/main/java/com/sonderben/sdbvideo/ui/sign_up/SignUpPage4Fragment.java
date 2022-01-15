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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sonderben.sdbvideo.data.model.Country;
import com.sonderben.sdbvideo.databinding.FragmentSignUpPage4Binding;
import com.sonderben.sdbvideo.repository.UserSignUpRepository;
import com.sonderben.sdbvideo.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpPage4Fragment extends Fragment  {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SignUpPage4Fragment() {

    }


    public static SignUpPage4Fragment newInstance(String param1, String param2) {
        SignUpPage4Fragment fragment = new SignUpPage4Fragment();
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
        binding=FragmentSignUpPage4Binding.inflate(getLayoutInflater());

        View root=binding.getRoot();
         spinnerCountry = binding.spinCountry;
         spinnerState=binding.spinState;
         spinnerCity= binding.spinCity;
         progressBar= binding.pregressBar;
         phoneCode= binding.phoneCode;
         telephoneCode= binding.phoneCode;
         telephone= binding.telephone;
         postalCode=binding.postalCode;

         spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if(i>0) {
                     spinnerState.setEnabled(true);
                     getCountryDetails( countryList.get(i - 1).getIso2() );
                     progressBar.setVisibility(View.VISIBLE);
                     getStatesWithinCountry( countryList.get(i - 1).getIso2() );

                     intent.putExtra("COUNTRY",(String)spinnerCountry.getSelectedItem());
                     LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                 }
                 else{
                     spinnerState.setEnabled(false);
                     spinnerCity.setEnabled(false);
                     phoneCode.setText("+");
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){

                    spinnerCity.setEnabled(true);
                    getCitiesWithinCountryAndState(
                            countryList.get( spinnerCountry.getSelectedItemPosition()-1).getIso2(),
                            stateList.get(i-1).getIso2()
                    );
                    intent.putExtra("DEPARTMENT",(String)spinnerCountry.getSelectedItem());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                }
                else {
                    spinnerCity.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    intent.putExtra("CITY",(String)spinnerCountry.getSelectedItem());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        region= binding.region;
        region.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                intent.putExtra("REGION",region.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        telephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(telephoneCode.getText().toString().trim().length()>0 &&
                telephone.getText().toString().trim().length()>0){
                    String tel=telephoneCode.getText().toString().replace("+","").trim()+
                    telephone.getText().toString().trim();
                    intent.putExtra("TELEPHONE",tel);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        telephoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(telephoneCode.getText().toString().trim().length()>0 &&
                        telephone.getText().toString().trim().length()>0){

                    String tel=telephoneCode.getText().toString().replace("+","").trim()+
                            telephone.getText().toString().trim();
                    intent.putExtra("TELEPHONE",tel);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        postalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(postalCode.getText().toString().trim().length()>0 ){
                    intent.putExtra("POSTAL_CODE",postalCode.getText().toString());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });

        getCountries();
        return root;
    }


    FragmentSignUpPage4Binding binding;
    Spinner spinnerCountry,spinnerState,spinnerCity;
    ArrayAdapter<String> adapterSpinnerCountry,adapterSpinnerState,adapterSpinnerCity;
    List<Country> countryList;
    List<Country.State> stateList;
    List<Country.City> cityList;
    EditText phoneCode,region,telephone,telephoneCode,postalCode;
    //Country.Detail countryDetail;
    ProgressBar progressBar;
    Intent intent=new Intent("SIGN_UP");

    private void getCountries() {
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Country>> call = repository.getCountries();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {


                if(response.isSuccessful()){

                    List<Country> accesses=response.body();
                    countryList =accesses;
                    String []accessName=new String[accesses.size()+1];
                    accessName[0]="Choose a Country".toUpperCase();
                    for (int a=0;a<accesses.size();a++){
                        accessName[a+1]=accesses.get(a).getName().toUpperCase();
                    }
                    adapterSpinnerCountry =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,accessName);
                    spinnerCountry.setAdapter(adapterSpinnerCountry);
                    adapterSpinnerCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    try {
                        Toast.makeText(SignUpPage4Fragment.this.getContext(),"not Succes "
                                +response.errorBody().string(),Toast.LENGTH_LONG).show();

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(SignUpPage4Fragment.this.getContext(),"on failure "
                        +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getStatesWithinCountry(String iso2Country) {
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Country.State>> call = repository.getStatesWithinCountry(iso2Country);

        call.enqueue(new Callback<List<Country.State>>() {
            @Override
            public void onResponse(Call<List<Country.State>> call, Response<List<Country.State>> response) {


                if(response.isSuccessful()){

                    List<Country.State> states=response.body();
                    stateList=states;
                    String []accessName=new String[states.size()+1];
                    accessName[0]="Choose a State".toUpperCase();
                    for (int a=0;a<states.size();a++){
                        accessName[a+1]=states.get(a).getName().toUpperCase();
                    }
                    adapterSpinnerState =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,accessName);
                    spinnerState.setAdapter(adapterSpinnerState);
                    adapterSpinnerState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    try {
                        Toast.makeText(SignUpPage4Fragment.this.getContext(),"not Success "
                                +response.errorBody().string(),Toast.LENGTH_LONG).show();

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<List<Country.State>> call, Throwable t) {
                Toast.makeText(SignUpPage4Fragment.this.getContext(),"on failure "
                        +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getCitiesWithinCountryAndState(String iso2Country,String iso2State) {
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Country.City>> call = repository.getCityWithinCountryAndState(iso2Country,iso2State);

        call.enqueue(new Callback<List<Country.City>>() {
            @Override
            public void onResponse(Call<List<Country.City>> call, Response<List<Country.City>> response) {


                if(response.isSuccessful()){

                    List<Country.City> states=response.body();
                    cityList=states;
                    String []accessName=new String[states.size()+1];
                    accessName[0]="Choose a City".toUpperCase();
                    for (int a=0;a<states.size();a++){
                        accessName[a+1]=states.get(a).getName().toUpperCase();
                    }
                    adapterSpinnerCity =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,accessName);
                    spinnerCity.setAdapter(adapterSpinnerCity);
                    adapterSpinnerCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    try {
                        Toast.makeText(SignUpPage4Fragment.this.getContext(),"not Success "
                                +response.errorBody().string(),Toast.LENGTH_LONG).show();

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<List<Country.City>> call, Throwable t) {
                Toast.makeText(SignUpPage4Fragment.this.getContext(),"on failure "
                        +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getCountryDetails(String iso2Country) {
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<Country.Detail> call = repository.getDetailsCountry(iso2Country);

        call.enqueue(new Callback<Country.Detail>() {
            @Override
            public void onResponse(Call<Country.Detail> call, Response<Country.Detail> response) {


                if(response.isSuccessful()){

                    Country.Detail countryDetail=response.body();
                    if(countryDetail!=null){
                        String tempPhoneCode[]=countryDetail.getPhonecode().toLowerCase().split("and");


                        if(tempPhoneCode.length>1) {
                            phoneCode.setText(tempPhoneCode[0].trim());
                            phoneCode.setEnabled(true);
                            if(!tempPhoneCode[0].contains("+"))
                                phoneCode.setText("+"+tempPhoneCode[0].trim());
                        }
                        else if(tempPhoneCode.length==1){
                            phoneCode.setText(tempPhoneCode[0].trim());
                            if(!tempPhoneCode[0].contains("+"))
                                phoneCode.setText("+"+tempPhoneCode[0].trim());
                            phoneCode.setEnabled(false);

                        }
                    }



                }
                else {
                    try {
                        Toast.makeText(SignUpPage4Fragment.this.getContext(),"not Success "
                                +response.errorBody().string(),Toast.LENGTH_LONG).show();

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<Country.Detail> call, Throwable t) {
                Toast.makeText(SignUpPage4Fragment.this.getContext(),"on failure "
                        +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


}