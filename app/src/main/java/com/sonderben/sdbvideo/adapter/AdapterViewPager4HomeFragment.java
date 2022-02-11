package com.sonderben.sdbvideo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterViewPager4HomeFragment extends FragmentStateAdapter {
    List<Fragment>fragments;

    public AdapterViewPager4HomeFragment(@NonNull @NotNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments=fragments;
    }





    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return fragments.get(1);
            default:
                return fragments.get(0);
        }

    }
//https://restcountries.com/
    @Override
    public int getItemCount() {
        return 2;
    }
}
