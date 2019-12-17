package com.example.bloodbankapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbankapp.DTO.Users;
import com.example.bloodbankapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
List <Users> users ;
Context context;
    public CustomAdapter(Context context, List<Users> users) {
       this.context=context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder viewHolder =new ViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bloodType.setText(users.get(position).getBlood_type());
    holder.usernameTxt.setText(users.get(position).getName());
        String user_photo_url = users.get(position).getUser_photo_url();
        if(user_photo_url!=null)
        Picasso.get().load(user_photo_url).placeholder(R.drawable.ic_user).into(holder.prof_img);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView prof_img ;
        TextView usernameTxt ,bloodType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_img = itemView.findViewById(R.id.userPhoto);
            usernameTxt = itemView.findViewById(R.id.usernameTxt);
            bloodType = itemView.findViewById(R.id.bloodtype);

        }
    }
}
