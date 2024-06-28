package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.Adapter.userAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.itemInterface.Adelete;
import com.example.appbanthietbidientu.model.User;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    TextView txview;
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
                    txview.setText("Số người dùng: "+userList.size());
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
        txview = findViewById(R.id.numuser);
        toolbar = findViewById(R.id.toolbar);
        buttonadd = findViewById(R.id.them);
        recyclerView = findViewById(R.id.listUser);
        userAdapter = new userAdapter(userList, new Adelete() {
            @Override
            public void deleteUser(User user) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

                reference.child(String.valueOf(user.getId())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(manageUser.this, "Đã xóa user thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(manageUser.this, "Xóa user thất bại", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }

            @Override
            public void updateUser(User user) {
                Intent intent = new Intent(manageUser.this, updateUser.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_us",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}