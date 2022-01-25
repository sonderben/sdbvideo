package com.sonderben.sdbvideo.ui.sign_up;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sonderben.sdbvideo.R;
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
        signUpViewModel=new ViewModelProvider(this.getActivity()).get(SignUpViewModel.class);







         spinnerCountry = binding.country;
         spinnerState=binding.department;
         spinnerCity= binding.city;
         progressBar= binding.progressBar;
         phoneCode= binding.phoneCode;
         phoneCode.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 String tel=phoneCode.getText().toString().trim()
                         +telephone.getText().toString().trim();
                 tel.replace("+","");
                 signUpViewModel.setUserTelephone(tel);
             }
         });

         direction=binding.direction;
        direction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                signUpViewModel.setUserRegion(direction.getText().toString());
            }
        });

         postalCode= binding.postal;
        postalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                signUpViewModel.setUserPostalCode(postalCode.getText().toString());
            }
        });

         telephone= binding.telephone;
        telephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                signUpViewModel.setUserTelephone(telephone.getText().toString());
            }
        });



        spinnerCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String textView= (String) adapterView.getItemAtPosition(position);
                signUpViewModel.setUserCountry(textView);
                positionSelectedItemCountry=position;
                getStatesWithinCountry( countryList.get( position ).getIso2() );
                getCountryDetails( countryList.get(position).getIso2() );
            }
        });

        spinnerState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String textView= (String) adapterView.getItemAtPosition(position);
                getCitiesWithinCountryAndState(countryList.get(positionSelectedItemCountry).getIso2(), stateList.get(position).getIso2() );
                signUpViewModel.setUserDepartment(textView);
            }
        });

        spinnerCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String textView= (String) adapterView.getItemAtPosition(position);
                signUpViewModel.setUserCity(textView);
            }
        });


getCountries();
        return root;
    }


    FragmentSignUpPage4Binding binding;
    AutoCompleteTextView spinnerCountry,spinnerState,spinnerCity;

    List<Country> countryList;
    List<Country.State> stateList;
    List<Country.City> cityList;

    ProgressBar progressBar;
    SignUpViewModel signUpViewModel;
    EditText phoneCode;
    TextInputEditText direction,postalCode,telephone;
    int positionSelectedItemCountry;


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
                    String []accessName=new String[accesses.size()];

                    for (int a=0;a<accesses.size();a++){
                        accessName[a]=accesses.get(a).getName().toUpperCase();
                    }


                    ArrayAdapter<String> adapterCountry =
                            new ArrayAdapter<>(
                                    SignUpPage4Fragment.this.getContext(),
                                    R.layout.dropdown_menu_popup_item,
                                    accessName);
                    spinnerCountry.setAdapter(adapterCountry);

                    Toast.makeText(SignUpPage4Fragment.this.getContext(),"success ssss",Toast.LENGTH_LONG).show();

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
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Country.State>> call = repository.getStatesWithinCountry(iso2Country);

        call.enqueue(new Callback<List<Country.State>>() {
            @Override
            public void onResponse(Call<List<Country.State>> call, Response<List<Country.State>> response) {


                if(response.isSuccessful()){

                    List<Country.State> states=response.body();
                    stateList=states;
                    String []accessName=new String[states.size()];

                    for (int a=0;a<states.size();a++){
                        accessName[a]=states.get(a).getName().toUpperCase();
                    }
                    ArrayAdapter<String> adapterState =
                            new ArrayAdapter<>(
                                    SignUpPage4Fragment.this.getContext(),
                                    R.layout.dropdown_menu_popup_item,
                                    accessName);
                    spinnerState.setAdapter(adapterState);

                    Toast.makeText(SignUpPage4Fragment.this.getContext(),"success state",Toast.LENGTH_LONG).show();
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
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = Utils.getInstanceRetrofitCountryStateCity();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<List<Country.City>> call = repository.getCityWithinCountryAndState(iso2Country,iso2State);

        call.enqueue(new Callback<List<Country.City>>() {
            @Override
            public void onResponse(Call<List<Country.City>> call, Response<List<Country.City>> response) {


                if(response.isSuccessful()){

                    List<Country.City> states=response.body();
                    cityList=states;
                    String []accessName=new String[states.size()];
                    Toast.makeText(SignUpPage4Fragment.this.getContext(),"size: "
                            +accessName.length,Toast.LENGTH_LONG).show();
                    for (int a=0;a<states.size();a++){
                        accessName[a]=states.get(a).getName().toUpperCase();
                    }
                    ArrayAdapter<String> adapterCity =
                            new ArrayAdapter<>(
                                    SignUpPage4Fragment.this.getContext(),
                                    R.layout.dropdown_menu_popup_item,
                                    accessName);
                    spinnerCity.setAdapter(adapterCity);

                    Toast.makeText(SignUpPage4Fragment.this.getContext(),"success city",Toast.LENGTH_LONG).show();
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