package com.example.bloodbankapp.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.backendless.BackendlessUser;
import com.example.bloodbankapp.DTO.user;
import com.example.bloodbankapp.Model.userModel;

public class UserVM {
    private static UserVM userVM ;
   static LifecycleOwner lifecycleOwner;
    public static MutableLiveData<String> registeredLiveData;
    public static MutableLiveData<BackendlessUser> loginedLiveData;
    public static UserVM getInstance(LifecycleOwner LCO)
    {
        lifecycleOwner=LCO;
        if(userVM==null) userVM= new UserVM();

        return userVM;
    }
   private static userModel userModel;
    public static userModel getModelInstatnce()
    {
       if (userModel==null)
           userModel= com.example.bloodbankapp.Model.userModel.getInstance();
       return userModel;
    }

    private UserVM() {
    }
     public void register (user user)
     { registeredLiveData= new MutableLiveData();
         getModelInstatnce().register(user);
         com.example.bloodbankapp.Model.userModel.registeredLiveData.observe(lifecycleOwner, new Observer<String>() {
             @Override
             public void onChanged(String s) {
                 registeredLiveData.setValue(s);
             }
         });
     }

    public void Login(String email, String password) {
        loginedLiveData= new MutableLiveData();
        getModelInstatnce().Login(email,password);
        com.example.bloodbankapp.Model.userModel.loginedLiveData.observe(lifecycleOwner, new Observer<BackendlessUser>() {
            @Override
            public void onChanged(BackendlessUser backendlessUser) {
           loginedLiveData.setValue(backendlessUser);
            }
        });
    }
}
