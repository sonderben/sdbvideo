package com.sonderben.sdbvideo.ui.sign_up;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.User;

import java.util.Calendar;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();

    LiveData<User> getUserLiveData() {
        return user;
    }




    public void setUserAccess(Access access) {
        User tempUser= init();
        tempUser.setAccess(access);
       user.setValue(tempUser);
    }
    public void setUserEmail(String email) {
        User tempUser= init();
        tempUser.setEmail(email);
        user.setValue(tempUser);
    }
    public void setUserFirstName(String firstName) {
        User tempUser= init();
        tempUser.setFirstName(firstName);
        user.setValue(tempUser);
    }
    public void setUserLastName(String lastName) {
        User tempUser= init();
        tempUser.setLastName(lastName);
        user.setValue(tempUser);
    }
    public void setUserBirthday(Calendar birthday) {
        User tempUser= init();
        tempUser.setBirthday(birthday);
        user.setValue(tempUser);
    }
    public void setUserTelephone(String telephone) {
        User tempUser= init();
        tempUser.setTelephone(telephone);
        user.setValue(tempUser);
    }
    public void setUserCountry(String country) {
        User tempUser= init();
        tempUser.setCountry(country);
        user.setValue(tempUser);
    }
    public void setUserDepartment(String department) {
        User tempUser= init();
        tempUser.setDepartment(department);
        user.setValue(tempUser);
    }
    public void setUserCity(String city) {
        User tempUser= init();
        tempUser.setCity(city);
        user.setValue(tempUser);
    }
    public void setUserRegion(String region) {
        User tempUser= init();
        tempUser.setRegion(region);
        user.setValue(tempUser);
    }
    public void setUserSex(String sex) {
        User tempUser= init();
        tempUser.setSex(sex);
        user.setValue(tempUser);
    }
    public void setUserPostalCode(String postalCode) {
        User tempUser= init();
        tempUser.setPostalCode(postalCode);
        user.setValue(tempUser);
    }
    public void setUserPassword(String password) {
        User tempUser= init();
        tempUser.setPassword(password);
        user.setValue(tempUser);
    }

    public User init (){
        return  user.getValue()==null?new User(): user.getValue();
    }
}
