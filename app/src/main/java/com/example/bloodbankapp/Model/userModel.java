package com.example.bloodbankapp.Model;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.bloodbankapp.DTO.user;

public class userModel {
    private static userModel userModel;
    public static MutableLiveData<String> registeredLiveData;
    public static MutableLiveData<BackendlessUser> loginedLiveData;

    public static userModel getInstance() {
        if (userModel==null)
            userModel= new userModel();
        return userModel;
    }

    private userModel() {
    }
     public void register(user user) {
        registeredLiveData= new MutableLiveData();
        BackendlessUser RegisterUser = new BackendlessUser();
        RegisterUser.setEmail(user.getUsername());
        RegisterUser.setPassword(user.getPassword());
        RegisterUser.setProperty("name",user.getName());
        RegisterUser.setProperty("blood_type",user.getBlood_type());
        RegisterUser.setProperty("lat",user.getLat());
        RegisterUser.setProperty("lng",user.getLng());

       Backendless.UserService.register(RegisterUser, new AsyncCallback<BackendlessUser>() {
           @Override
           public void handleResponse(BackendlessUser response) {
               registeredLiveData.setValue(response.getEmail());
           }

           @Override
           public void handleFault(BackendlessFault fault) {
            registeredLiveData.setValue(fault.getMessage());
           }
       });

    }

    public void Login(String email, String password) {

        loginedLiveData= new MutableLiveData();
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
           loginedLiveData.setValue(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println(fault.getMessage());
    loginedLiveData.setValue(null);
            }
        });
    }
}
