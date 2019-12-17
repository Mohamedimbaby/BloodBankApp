package com.example.bloodbankapp.ui.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.bloodbankapp.DTO.Users;
import com.example.bloodbankapp.MainActivity;
import com.example.bloodbankapp.R;
import com.example.bloodbankapp.ViewModel.UserVM;
import com.example.bloodbankapp.myApplication;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment implements IPickResult {
ImageView prof_img ;
TextView nameTxt,usernameTxt,passwordTxt,phoneTxt ;
    ImageView movImg;
Button prof_saveBtn;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    BackendlessUser currentUser;
    Spinner blood_typeSpinner ;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
View root = inflater.inflate(R.layout.fragment_profile, container, false);
         nameTxt=root.findViewById(R.id.prof_name);
         usernameTxt=root.findViewById(R.id.prof_email);
         passwordTxt=root.findViewById(R.id.prof_password);
         phoneTxt=root.findViewById(R.id.phone);
         movImg=root.findViewById(R.id.prof_img);
        prof_saveBtn=root.findViewById(R.id.prof_saveBtn);
        blood_typeSpinner=root.findViewById(R.id.bloodtypeSpinner);
final List<String> bloodTypesList = new ArrayList<>();
Collections.addAll(bloodTypesList,"Please Select ","A-","A+","B-","B+","O-","O+","AB-","AB+");
        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,bloodTypesList);
blood_typeSpinner.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Saving your data");

        setDataToViews();

        movImg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PickImage(v);
    }
});
        prof_saveBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String blood_type = blood_typeSpinner.getSelectedItem().toString();

        if (!name.equals("")&&!phone.equals("")&&!blood_type.equals(bloodTypesList.get(0))) {
              currentUser.setProperty("name",name);
              currentUser.setProperty("phone", phone);
              currentUser.setProperty("blood_type", blood_type);
              currentUser.setPassword(((myApplication)    getActivity().getApplicationContext()).preferences.getString("password","null"));
            progressDialog.show();
              UserVM.getInstance(ProfileFragment.this).Save(currentUser);
              UserVM.UpdateUserData.observe(ProfileFragment.this, new Observer<Users>() {
                  @Override
                  public void onChanged(Users user) {
                      progressDialog.cancel();
                      if (user!=null)
                          Toast.makeText(getContext(),  "Updated Successfully", Toast.LENGTH_SHORT).show();
                  else
                          Toast.makeText(getContext(),  "Something went wrong", Toast.LENGTH_SHORT).show();

                  }

              });
                    }
      else {
          Toast.makeText(getContext(), "fill all the fields", Toast.LENGTH_SHORT).show();
          }


        }


});

        return root;}

    private void setDataToViews() {
        currentUser = Backendless.UserService.CurrentUser();
        usernameTxt.setText(currentUser.getEmail());
        passwordTxt.setText(currentUser.getPassword());
        if (currentUser.getProperty("phone")!=null&&currentUser.getProperty("user_photo_url")!=null) {
phoneTxt.setText(currentUser.getProperty("phone").toString());
            String user_photo_url = currentUser.getProperty("user_photo_url").toString();
            if (user_photo_url!=null)
            {
                Picasso.get().load(user_photo_url).placeholder(R.drawable.ic_launcher_background).into(movImg);
            }
        }
        nameTxt.setText(currentUser.getProperty("name").toString());

        // movImg.setImageBitmap();

    }

    public void PickImage(View view) {
        PickImageDialog.build(new PickSetup()).setOnPickResult(this).show(getFragmentManager());


    }

    @Override
    public void onPickResult(PickResult r) {

        bitmap = r.getBitmap();
        movImg.setImageBitmap(bitmap);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("Do You Want To Upload Your Photo  Now ? ")
                .setTitle("Maybe take a few Seconds .. ").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (bitmap != null) {
                            Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG, 20, nameTxt.getText().toString(), "images", new AsyncCallback<BackendlessFile>() {
                                @Override
                                public void handleResponse(BackendlessFile response) {
                                    Toast.makeText(getContext(),  "photo uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    currentUser.setProperty("user_photo_url", response.getFileURL());
                                    UserVM.getInstance(ProfileFragment.this).uploadPhoto(currentUser);
                                    UserVM.uploadPhotoLiveData.observe(ProfileFragment.this, new Observer<Users>() {
                                        @Override
                                        public void onChanged(Users user) {
                                            progressDialog.cancel();
                                            if (user!=null)
                                                Toast.makeText(getContext(),  "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(getContext(),  "Something went wrong", Toast.LENGTH_SHORT).show();

                                        }

                                    });

                                }


                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    System.out.println(fault.getMessage());
                                }
                            });
                        }

                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        movImg.setImageBitmap(null);
                        bitmap=null;
                    }

                });
        builder.getContext().setTheme(R.style.AppTheme_Dialog);
        builder.create().show();



    }
}

