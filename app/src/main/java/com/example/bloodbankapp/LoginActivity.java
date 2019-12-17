package com.example.bloodbankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.multidex.MultiDexApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.databinding.ActivityLoginBinding;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    String TAG = "result";
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        Log.i(TAG, "oncreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "on Start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "oncRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on Destroy");
    }

    public void onLoginPressed(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        String email = binding.loginEmail.getText().toString();

        password = binding.loginPassword.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please fill All the fields ", Toast.LENGTH_SHORT).show();
            Intent start = new Intent(this, MainActivity.class);
            startActivity(start);
        } else if (password.length() < 8)
            binding.loginPassword.setError("password must be at least 8 characters");
        else {

            UserVM.getInstance(this).Login(email, password);
            UserVM.loginedLiveData.observe(this, new Observer<BackendlessUser>() {
                @Override
                public void onChanged(BackendlessUser backendlessUser) {
                    progressDialog.cancel();
                    if (backendlessUser != null) {
                        Toast.makeText(LoginActivity.this, "Login Successfully...", Toast.LENGTH_SHORT).show();
                        Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        ((myApplication) getApplication()).editor.putString("username",backendlessUser.getEmail());
                        System.out.println(backendlessUser.getEmail());
                        ((myApplication) getApplication()).editor.putString("name",backendlessUser.getProperty("name").toString());
                        ((myApplication) getApplication()).editor.putString("password",password);

                        ((myApplication) getApplication()).editor.putString("login","1");
                        ((myApplication) getApplication()).editor.commit();
                        MainIntent.putExtra("login_user", backendlessUser);
                        startActivity(MainIntent);
                    } else {

                        Toast.makeText(LoginActivity.this, "Login Failed...", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }

    public void registerText(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);

    }
}
