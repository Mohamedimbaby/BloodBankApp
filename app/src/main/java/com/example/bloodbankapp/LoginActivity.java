package com.example.bloodbankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.BackendlessUser;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
    ProgressDialog progressDialog;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
    }

    public void onLoginPressed(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.show();
        String email = binding.loginEmail.getText().toString();
        String password = binding.loginPassword.getText().toString();
        if (email.equals("")||password.equals(""))
        {
            Toast.makeText(this, "Please fill All the fields ", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8) binding.loginPassword.setError("password must be at least 8 characters");
        else {
            UserVM.getInstance(this).Login(email,password);
        UserVM.loginedLiveData.observe(this, new Observer<BackendlessUser>() {
            @Override
            public void onChanged(BackendlessUser backendlessUser) {
                progressDialog.cancel();
                if (backendlessUser!=null)
                {

                    Toast.makeText(LoginActivity.this, "Login Successfully...", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(LoginActivity.this, "Login Failed...", Toast.LENGTH_SHORT).show();

                }

            }
        });
        }
    }
}
