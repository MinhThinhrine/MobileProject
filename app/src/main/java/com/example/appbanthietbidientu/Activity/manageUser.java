package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appbanthietbidientu.Adapter.userAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class manageUser extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<User> userList;
    private userAdapter userAdapter;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    Button buttonadd;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            getData();
            anhxa();
            back();
            add();
//            LoadmoreData();
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Error Connect Internet");
        }

    }

    private void add() {
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manageUser.this,addUser.class));
            }
        });
    }

    private void back() {
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manageUser.this,HomeAdmin.class));
            }
        });
    }

    private void getData() {
        userList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user = dataSnapshot.getValue(User.class);
                        if (!user.getEmail().equals(email)) {
                            userList.add(user);
                        }
                    }
                    linearLayoutManager = new LinearLayoutManager(manageUser.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(userAdapter);
                }else{
                    Toast.makeText(manageUser.this, "Không có người dùng nào tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhxa() {
        buttonadd = findViewById(R.id.them);
        recyclerView = findViewById(R.id.listUser);
        toolbar = findViewById(R.id.toolbar);
        userAdapter = new userAdapter(userList);
    }
}