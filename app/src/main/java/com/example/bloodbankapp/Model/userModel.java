package com.example.bloodbankapp.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.bloodbankapp.DTO.Users;

import java.net.HttpCookie;
import java.util.List;

public class userModel {
    public static LiveData<BackendlessUser> getLoginedUserData;
    private static userModel userModel;
    public static MutableLiveData<String> registeredLiveData;
    public static MutableLiveData<BackendlessUser> loginedLiveData;
    public static MutableLiveData<Users> UpdateUserData;
    public static MutableLiveData<Users> uploadPhotoLiveData;
    public static MutableLiveData<List<Users>> getAllUsersLiveData;

    public static userModel getInstance() {
        if (userModel==null)
            userModel= new userModel();
        return userModel;
    }

    private userModel() {
    }
     public void register(Users Users) {
        registeredLiveData= new MutableLiveData();
        BackendlessUser RegisterUser = new BackendlessUser();
        RegisterUser.setEmail(Users.getUsername());
        RegisterUser.setPassword(Users.getPassword());
        RegisterUser.setProperty("name", Users.getName());
        RegisterUser.setProperty("blood_type", Users.getBlood_type());
        RegisterUser.setProperty("lat", Users.getLat());
        RegisterUser.setProperty("lng", Users.getLng());

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
    public void getAllUsers() {

        getAllUsersLiveData= new MutableLiveData();
        Backendless.Data.of(Users.class).find(new AsyncCallback<List<Users>>() {
            @Override
            public void handleResponse(List<Users> response) {
                System.out.println("*******************"+response.get(0).getName());
                getAllUsersLiveData.setValue(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println(fault.getMessage());
                getAllUsersLiveData.setValue(null);
            }
        });
    }

    public void updateUser(BackendlessUser currentUser) {
        Users user = new Users();
        user.setName(currentUser.getProperty("name").toString());
        user.setUsername(currentUser.getEmail());
        user.setPassword(currentUser.getPassword());
         user.setName(currentUser.getProperty("name").toString());
         user.setBlood_type(currentUser.getProperty("blood_type").toString());
         user.setLat(Double.parseDouble(currentUser.getProperty("lat").toString()));
         user.setLng(Double.parseDouble(currentUser.getProperty("lng").toString()));
         user.setPhone(currentUser.getProperty("phone").toString());
         user.setObjectId(currentUser.getObjectId());
         user.setUser_photo_url(currentUser.getProperty("user_photo_url").toString());
        System.out.println(user.getObjectId()+ ", "  +user.getObjectId()+ ", "+user.getPassword()+ ", "+user.getUsername()+ ", "+user.getUser_photo_url()+ ", " );
        UpdateUserData= new MutableLiveData();
        Backendless.Data.of(Users.class).save(user, new AsyncCallback<Users>() {
            @Override
            public void handleResponse(Users response) {
                UpdateUserData.setValue(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) { UpdateUserData.setValue(null);
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& "+fault.getMessage());
            }
        });
    }
    public void uploadPhoto(BackendlessUser currentUser) {
        Users user = new Users();
        user.setName(currentUser.getProperty("name").toString());
        user.setUsername(currentUser.getEmail());
        user.setPassword(currentUser.getPassword());
        user.setLat(Double.parseDouble(currentUser.getProperty("lat").toString()));
        user.setLng(Double.parseDouble(currentUser.getProperty("lng").toString()));         user.setObjectId(currentUser.getObjectId());
         user.setUser_photo_url(currentUser.getProperty("user_photo_url").toString());
        System.out.println(user.getObjectId()+ ", "  +user.getObjectId()+ ", "+user.getPassword()+ ", "+user.getUsername()+ ", "+user.getUser_photo_url()+ ", " );
        uploadPhotoLiveData= new MutableLiveData();
        Backendless.Data.of(Users.class).save(user, new AsyncCallback<Users>() {
            @Override
            public void handleResponse(Users response) {
                uploadPhotoLiveData.setValue(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) { UpdateUserData.setValue(null);
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& "+fault.getMessage());
            }
        });
    }
}
