package com.example.bloodbankapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbankapp.DTO.Users;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.databinding.ActivityRegisterBinding;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

public class RegisterActivity extends AppCompatActivity implements LocationListener {
    ActivityRegisterBinding binding;
    LocationManager loc_manager;
    Users Users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        loc_manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String[]perm={Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this,perm,1);

            return;
        }
        else {
            Toast.makeText(this, "gone", Toast.LENGTH_SHORT).show();
            loc_manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

        }
        loc_manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

    }
    ProgressDialog progressDialog;

    public void onRegisteredPressed(View view) {
        progressDialog = new ProgressDialog(this);
        String name  = binding.name.getText().toString();
        String username  = binding.email.getText().toString();
        String password  = binding.password.getText().toString();
        if (name.equals("")||
        password.equals("")||
        username.equals("")||
        Users.getLat()==null||
        Users.getLng()==null){

            Toast.makeText(this, "Please fill all fields... ", Toast.LENGTH_SHORT).show();


        }
        else if (password.length()<8){
            binding.password.setError("Password must be greater than 8 characters ");
        }
        else {
            progressDialog.setTitle("Registration");
            progressDialog.setMessage("Wait till Registration Complete");
            progressDialog.show();
Users.setName(name);
Users.setUsername(username);
Users.setPassword(password);
            binding.password.setError(null);
            UserVM instance = UserVM.getInstance(this);
            instance.register(Users);
            UserVM.registeredLiveData.observe(this, new Observer() {
                @Override
                public void onChanged(Object o) {
                    progressDialog.cancel();
                    Toast.makeText(RegisterActivity.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(in);
                }
            });
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Users.setLat(location.getLatitude());
        Users.setLng(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
