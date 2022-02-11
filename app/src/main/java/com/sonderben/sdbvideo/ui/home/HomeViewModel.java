package com.sonderben.sdbvideo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> currentFragment = new MutableLiveData<>();

    public HomeViewModel() {

        //currentFragment.setValue(ListVideosFragment.class.toString());
    }

    public LiveData<String> getCurrentFragment() {
        return currentFragment;
    }
    public void setCurrentFragment(String name) {
        currentFragment.setValue(name);
    }
}