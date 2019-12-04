package com.example.bloodbankapp.Model;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.bloodbankapp.DTO.user;

public class userModel {
    private static userModel userModel;
    public static userModel getInstance() {
        if (userModel==null)
            userModel= new userModel();
        return userModel;
    }

    private userModel() {
    }
     Boolean registered ;
    public boolean register(user user) {
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
               registered=true;
           }

           @Override
           public void handleFault(BackendlessFault fault) {
            registered=false;
           }
       });
return registered;

    }
}
