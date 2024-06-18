package com.example.appbanthietbidientu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userHolder>{
    private List<User> userList;

    public userAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_user,parent,false);
        return  new userHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userHolder holder, int position) {
        User user = userList.get(position);
        holder.id.setText("Id: "+user.getId());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class userHolder extends RecyclerView.ViewHolder{
        private TextView id,email;
        public userHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.iduser);
            email = itemView.findViewById(R.id.emaiuser);
        }
    }
}