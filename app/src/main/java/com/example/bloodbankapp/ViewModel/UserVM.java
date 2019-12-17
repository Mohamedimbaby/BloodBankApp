package com.example.bloodbankapp.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.backendless.BackendlessUser;
import com.example.bloodbankapp.DTO.Users;
import com.example.bloodbankapp.Model.userModel;

import java.util.List;

public class UserVM {
    private static UserVM userVM ;
   static LifecycleOwner lifecycleOwner;
    public static MutableLiveData<String> registeredLiveData;
    public static MutableLiveData<BackendlessUser> loginedLiveData;
    public static MutableLiveData<List<Users>> getAllUsersLiveData;
    public static MutableLiveData<Users> UpdateUserData;
    public static MutableLiveData<Users> uploadPhotoLiveData;
    public static UserVM getInstance(LifecycleOwner LCO)
    {
        lifecycleOwner=LCO;
        if(userVM==null) userVM= new UserVM();

        return userVM;
    }
   private static userModel userModel;
    private static userModel getModelInstatnce()
    {
       if (userModel==null)
           userModel= com.example.bloodbankapp.Model.userModel.getInstance();
       return userModel;
    }

    private UserVM() {
    }
     public void register (Users Users)
     { registeredLiveData= new MutableLiveData();
         getModelInstatnce().register(Users);
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
    public void getAllUsers() {
        getAllUsersLiveData= new MutableLiveData();
        getModelInstatnce().getAllUsers();
        com.example.bloodbankapp.Model.userModel.getAllUsersLiveData.observe(lifecycleOwner, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                getAllUsersLiveData.setValue(users);
            }
        });
    }

    public void Save(BackendlessUser currentUser) {
        UpdateUserData= new MutableLiveData();
        getModelInstatnce().updateUser(currentUser);
        com.example.bloodbankapp.Model.userModel.UpdateUserData.observe(lifecycleOwner, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                UpdateUserData.setValue(users);
            }
        });
    }

    public void uploadPhoto(BackendlessUser currentUser) {
        uploadPhotoLiveData= new MutableLiveData();
        getModelInstatnce().uploadPhoto(currentUser);
        com.example.bloodbankapp.Model.userModel.uploadPhotoLiveData.observe(lifecycleOwner, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                uploadPhotoLiveData.setValue(users);
            }
        });
    }
}
