package com.sonderben.sdbvideo.ui.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.adapter.AdapterProfile4ChooseProfileActivity;
import com.sonderben.sdbvideo.adapter.AdapterProfile4MeFragmen;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.databinding.FragmentMeBinding;
import com.sonderben.sdbvideo.repository.ProfileRepository;
import com.sonderben.sdbvideo.ui.choose_profile.ChooseProfileActivity;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
        // Required empty public constructor
    }


    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        binding=FragmentMeBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        TextView signOut= binding.logout;
        recyclerView=binding.recyclerview;

        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.HORIZONTAL,false));
        Preferences preferences=Preferences.getPreferenceInstance(MeFragment.this.getContext());
        getAllProfiles(preferences.getEmailUser(), preferences.getToken());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.signOut(MeFragment.this.getContext());
                Intent intent=new Intent(MeFragment.this.getContext(), LoginActivity.class);
                startActivity(intent);
                MeFragment.this.getActivity().finish();
            }
        });
        return root;
    }
    FragmentMeBinding binding;
    RecyclerView recyclerView;
    AdapterProfile4MeFragmen adapter;


    public void getAllProfiles(String email,String token){
        Retrofit retrofit= Utils.getInstanceRetrofit();
        ProfileRepository repository= retrofit.create(ProfileRepository.class);
        Call<List<Profile>> profiles=repository.getProfiles(email,"Bearer "+token);
        profiles.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if(response.isSuccessful()){
                    List<Profile>tempProfiles=response.body();
                    adapter=new AdapterProfile4MeFragmen(tempProfiles,requireActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MeFragment.this.getContext(),LinearLayoutManager.HORIZONTAL,false));
                }else{
                    try {
                        new AlertDialog.Builder(MeFragment.this.getContext())
                                .setMessage(response.errorBody().string() +token+"pa la ")
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });

    }

}