package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sonderben.sdbvideo.data.model.UserLogin;

public class EditProfileViewModel extends ViewModel {
    private MutableLiveData<String> urlPicture = new MutableLiveData<>();

    LiveData<String> getUrLiveData() {
        return urlPicture;
    }




    public void setUrlPicture(String url) {
        urlPicture.setValue(url);

    }
}
