package com.sonderben.sdbvideo.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.UserLogin;

import retrofit2.Call;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<UserLogin> loginFormState = new MutableLiveData<>();
   // private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();




    LiveData<UserLogin> getLoginFormState() {
        return loginFormState;
    }




    public void loginDataChanged(String username, String password) {
            loginFormState.setValue(new UserLogin(username,password));

    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}