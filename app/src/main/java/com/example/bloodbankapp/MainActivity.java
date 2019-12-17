package com.example.bloodbankapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((myApplication) getApplicationContext()).backEnd_intial(this);


        String login = ((myApplication) getApplicationContext()).preferences.getString("login", "null");
        if(login.equals("null")) {
            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);
        }
        else{
            String username = ((myApplication) getApplicationContext()).preferences.getString("username", "null");
            String password = ((myApplication) getApplicationContext()).preferences.getString("password", "null");
            System.out.println("username : "+ username +", password :" +password);
            UserVM.getInstance(this).Login(username, password);

        }

        BottomNavigationView navView = findViewById(R.id.nav_view);

//        Serializable login_user = getIntent().getExtras().getSerializable("login_user");
//        System.out.println("email ============================================    "+((BackendlessUser) login_user).getEmail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_home, R.id.navigation_notifications)
                .build();
        
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.action_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.actionbarmenu) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Are You Sure You Want ot Log out  ? ")
                    .setTitle("Question .. ").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((myApplication) getApplication()).editor.putString("login","null");
                            ((myApplication) getApplication()).editor.commit();

                            Intent loginIntent= new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(loginIntent);
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    });
            builder.getContext().setTheme(R.style.AppTheme_Dialog);
            builder.create().show();

        }
        return super.onOptionsItemSelected(item);
    }
}
