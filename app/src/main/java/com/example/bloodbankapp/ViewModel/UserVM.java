package com.example.bloodbankapp.ViewModel;

import com.example.bloodbankapp.DTO.user;
import com.example.bloodbankapp.Model.userModel;

public class UserVM {
    private static UserVM userVM ;
    public static UserVM getInstance()
    {
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
     {
         getModelInstatnce().register (user);
     }
}
