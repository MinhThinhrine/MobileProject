package com.example.appbanthietbidientu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.itemInterface.Adelete;
import com.example.appbanthietbidientu.model.User;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userHolder>{
    private List<User> userList;
    private Adelete adelete;

    public userAdapter(List<User> userList, Adelete adelete) {
        this.userList = userList;
        this.adelete = adelete;
    }

    @NonNull
    @Override
    public userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_user,parent,false);
        return  new userHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userHolder holder, int position) {
        final User user = userList.get(position);
        holder.id.setText("Id: "+user.getId());
        holder.email.setText(user.getEmail());
        holder.bntxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adelete.deleteUser(user);
            }
        });

        holder.suauser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adelete.updateUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class userHolder extends RecyclerView.ViewHolder{
//        LinearLayout linearLayout;
        private TextView id,email;
        private Button bntxoa,suauser;
        public userHolder(@NonNull View itemView) {
            super(itemView);
//            linearLayout = itemView.findViewById(R.id.layout_user);
            bntxoa = itemView.findViewById(R.id.xoaUser);
            id = itemView.findViewById(R.id.iduser);
            email = itemView.findViewById(R.id.emaiuser);
            suauser = itemView.findViewById(R.id.suauser);
        }
    }
}
