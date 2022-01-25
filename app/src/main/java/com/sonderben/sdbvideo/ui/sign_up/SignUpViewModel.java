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
        User tempUser= user.getValue()==null?new User(): user.getValue();
        tempUser.setAccess(access);
       user.setValue(tempUser);
    }
    public void setUserEmail(String email) {
        User tempUser= user.getValue();
        tempUser.setEmail(email);
        user.setValue(tempUser);
    }
    public void setUserFirstName(String firstName) {
        User tempUser= user.getValue();
        tempUser.setFirstName(firstName);
        user.setValue(tempUser);
    }
    public void setUserLastName(String lastName) {
        User tempUser= user.getValue();
        tempUser.setLastName(lastName);
        user.setValue(tempUser);
    }
    public void setUserBirthday(Calendar birthday) {
        User tempUser= user.getValue();
        tempUser.setBirthday(birthday);
        user.setValue(tempUser);
    }
    public void setUserTelephone(String telephone) {
        User tempUser= user.getValue();
        tempUser.setTelephone(telephone);
        user.setValue(tempUser);
    }
    public void setUserCountry(String country) {
        User tempUser= user.getValue();
        tempUser.setCountry(country);
        user.setValue(tempUser);
    }
    public void setUserDepartment(String department) {
        User tempUser= user.getValue();
        tempUser.setDepartment(department);
        user.setValue(tempUser);
    }
    public void setUserCity(String city) {
        User tempUser= user.getValue();
        tempUser.setCity(city);
        user.setValue(tempUser);
    }
    public void setUserRegion(String region) {
        User tempUser= user.getValue();
        tempUser.setRegion(region);
        user.setValue(tempUser);
    }
    public void setUserSex(String sex) {
        User tempUser= user.getValue();
        tempUser.setSex(sex);
        user.setValue(tempUser);
    }
    public void setUserPostalCode(String postalCode) {
        User tempUser= user.getValue();
        tempUser.setPostalCode(postalCode);
        user.setValue(tempUser);
    }
    public void setUserPassword(String password) {
        User tempUser= user.getValue();
        tempUser.setPassword(password);
        user.setValue(tempUser);
    }
}
