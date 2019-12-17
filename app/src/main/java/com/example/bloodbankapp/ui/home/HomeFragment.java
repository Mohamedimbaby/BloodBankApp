package com.example.bloodbankapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbankapp.Adapters.CustomAdapter;
import com.example.bloodbankapp.DTO.Users;
import com.example.bloodbankapp.R;
import com.example.bloodbankapp.ViewModel.UserVM;

import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView usersRecycler ;

    String TAG ="mov";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"on Attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserVM instance = UserVM.getInstance(this);
        instance.getAllUsers();

        Log.i(TAG,"on Create");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        usersRecycler=root.findViewById(R.id.peopleRecycler);
        Log.i(TAG,"on createView");
        UserVM.getAllUsersLiveData.observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                System.out.println(users.size());
                CustomAdapter adapter = new CustomAdapter(getContext(),users);
                usersRecycler.setAdapter(adapter);
                usersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        });

       //        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"on Activity created");
    }

    @Override
    public void onDestroyView() {

        Log.i(TAG,"on Destroy View");

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.i(TAG,"on Detach");
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"on Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"on Pause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"on Start");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"on Stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"on Destroy");}
}