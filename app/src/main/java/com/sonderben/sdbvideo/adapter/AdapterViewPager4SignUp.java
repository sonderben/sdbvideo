package com.sonderben.sdbvideo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sonderben.sdbvideo.ui.sign_up.SignUpPage1Fragment;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage2Fragment;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage3Fragment;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage4Fragment;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage5Fragment;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage6Fragment;

import org.jetbrains.annotations.NotNull;

public class AdapterViewPager4SignUp extends FragmentStateAdapter {
    public AdapterViewPager4SignUp(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }





    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SignUpPage2Fragment();
            case 2:
                return new SignUpPage3Fragment();
            case 3:
                return new SignUpPage4Fragment();
            case 4:
                return new SignUpPage5Fragment();
            case 5:
                return new SignUpPage6Fragment();
            default:
                return new SignUpPage1Fragment();
        }

    }
//https://restcountries.com/
    @Override
    public int getItemCount() {
        return 6;
    }
}
