package com.sonderben.sdbvideo.ui.dashboard;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.adapter.AdapterFilm4MainActivity;
import com.sonderben.sdbvideo.adapter.AdapterFilm4SearchFragment;
import com.sonderben.sdbvideo.data.model.Video;
import com.sonderben.sdbvideo.databinding.FragmentDashboardBinding;
import com.sonderben.sdbvideo.repository.GeneralRepository;
import com.sonderben.sdbvideo.ui.home.HomeFragment;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    AdapterFilm4SearchFragment adapter;
    SearchView searchView;
    private ProgressBar progressBar;
    private final Preferences preferences = Preferences.getPreferenceInstance(this.getContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView search = binding.search;
        recyclerView = binding.recyclerview;
        searchView = binding.searchView;
        recyclerView = binding.recyclerview;
        progressBar = binding.progressBar;

        searchView.setIconified(false);
        search.setOnClickListener(x -> {
            progressBar.setVisibility(View.VISIBLE);
            getVideosByDescription(searchView.getQuery().toString());
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    getVideosByDescription(searchView.getQuery().toString());
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1)
                    search.setEnabled(true);
                else
                    search.setEnabled(false);
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getVideosByDescription(String description) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        GeneralRepository repository = retrofit.create(GeneralRepository.class);
        Call<List<Video>> categoryCall = repository.getVideoByDescription(description, preferences.getIdProfile(), 0, "Bearer " + preferences.getToken());
        categoryCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {

                if (response.isSuccessful()) {
                    List<Video> videos = response.body();
                    adapter = new AdapterFilm4SearchFragment(videos,DashboardFragment.this.getParentFragmentManager());
                    recyclerView.setLayoutManager(new LinearLayoutManager(DashboardFragment.this.getContext(), RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);


                } else {
                    try {
                        String a = response.errorBody().string();
                        new AlertDialog.Builder(DashboardFragment.this.getContext()).setMessage(a + "no cool").show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                new AlertDialog.Builder(DashboardFragment.this.getContext()).setMessage("failure: " + t.getMessage()).show();
            }
        });
    }
}