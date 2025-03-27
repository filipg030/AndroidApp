package com.example.jwtapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoggedUserViewModel extends ViewModel {
    public MutableLiveData<String> loggedUserLiveData;

    public LoggedUserViewModel(){
        loggedUserLiveData = new MutableLiveData<>();
    }
    public void setupLoggedUser(String name){
        this.loggedUserLiveData.setValue(name);
    }
    public MutableLiveData<String> getObservedLoggedUser(){
        return loggedUserLiveData;
    }
    public void changeLoggedUser(String username, String token){
        loggedUserLiveData.setValue(username);
    }

}
