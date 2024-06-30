package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class inforUser extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout layout_user;
    private RelativeLayout relativLayout;
    private ImageView imagUser;
    private TextView iduser, emaiuser, nameUser, phoneUser, addUser, thaydoithongtin, doimk;
    private ImageView imageView, bntdoimk, btnLichsu;
    private int id;
    User user;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
        anhxa();
        getData();



        thaydoithongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inforUser.this, newInforUser.class);
                intent.putExtra("idUser",id);
                startActivity(intent);
            }
        });

        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inforUser.this, newPassWord.class);
                intent.putExtra("idUser",id);
                startActivity(intent);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inforUser.this, ListDonHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        String email = sharedPreferences.getString("email", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query query = reference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Duyệt qua các kết quả trả về từ query
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey(); // Lấy ID của user là lấy cái node phía trước của user
                    user = snapshot.getValue(User.class);
                    user.setId(Integer.parseInt(userId)); //set lại id theo node nếu id ko đúng
                }
                iduser.setText("Id: "+user.getId()+"");
                phoneUser.setText("Số điện thoại: "+user.getPhone());
                addUser.setText("Địa chỉ: "+user.getAddress());
                nameUser.setText("Tên: "+user.getUserName());
                emaiuser.setText("Email: "+user.getEmail());
                id = user.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Log.e("Firebase Error", "Error fetching user data", databaseError.toException());
            }
        });
    }

    private void anhxa() {
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.titleUser);
        layout_user = findViewById(R.id.layout_user);
        relativLayout = findViewById(R.id.relativLayout);
        imagUser = findViewById(R.id.imagUser);
        iduser = findViewById(R.id.iduser);
        emaiuser = findViewById(R.id.emaiuser);
        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        addUser = findViewById(R.id.addUser);
        thaydoithongtin = findViewById(R.id.thaydoithongtin);
        doimk = findViewById(R.id.doimk);
        imageView = findViewById(R.id.imageView);
        bntdoimk = findViewById(R.id.bntdoimk);
        btnLichsu = findViewById(R.id.bntlichsu);
    }
}