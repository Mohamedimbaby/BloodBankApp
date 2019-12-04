package com.example.bloodbankapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbankapp.DTO.user;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);


    }

    public void onRegisteredPressed(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registration");
        progressDialog.show();
        user user = new user();
        UserVM instance = UserVM.getInstance();
        instance.register(user);
        String name  = binding.name.getText().toString();
        String username  = binding.email.getText().toString();
        String password  = binding.password.getText().toString();
        if (name.equals("")||
        password.equals("")||
        username.equals("")){
            Toast.makeText(this, "Please fill all fields... ", Toast.LENGTH_SHORT).show();
        }
        else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1)
        {
            if (resultCode==RESULT_OK)
            {
                System.out.println("dada");
                Place place=PlacePicker.getPlace(data,this);
                LatLng latLng = place.getLatLng();
                String s = latLng.latitude +""+ latLng.longitude;
                binding.Address.setText(s);
            }
        }
    }
    public void onLocationPressed(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent=builder.build(this);
            startActivityForResult(intent,1);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            System.out.println("first catch "+e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            System.out.println("second catch "+e.getMessage());
            e.printStackTrace();
        }
    }

}
