package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newInforUser extends AppCompatActivity {
    private EditText newName, newEmail, newPhone, newAddress;
    private TextView newIF, huyNewIF;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_infor_user);

        anhxa();
        getData();

        newIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedName = newName.getText().toString();
                String updatedEmail = newEmail.getText().toString();
                String updatedPhone = newPhone.getText().toString();
                String updatedAddress = newAddress.getText().toString();
                if (id != -1) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(String.valueOf(id));

                    reference.child("userName").setValue(updatedName);
                    reference.child("email").setValue(updatedEmail);
                    reference.child("phone").setValue(updatedPhone);
                    reference.child("address").setValue(updatedAddress);
                    Toast.makeText(newInforUser.this, "Thông tin người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(newInforUser.this, "ID người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        huyNewIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(String.valueOf(id));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    newName.setText(user.getUserName());
                    newEmail.setText(user.getEmail());
                    newPhone.setText(user.getPhone());
                    newAddress.setText(user.getAddress());
                }else {
                    Toast.makeText(newInforUser.this, "User không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        newName = findViewById(R.id.newName);
        newEmail = findViewById(R.id.newEmail);
        newPhone = findViewById(R.id.newPhone);
        newAddress = findViewById(R.id.newAddress);
        newIF = findViewById(R.id.newIF);
        huyNewIF = findViewById(R.id.huyNewIF);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("idUser")) {
            id = intent.getIntExtra("idUser", -1); // Giá trị mặc định là -1 nếu không có dữ liệu
            Toast.makeText(this, "Received idUser: "+id, Toast.LENGTH_SHORT).show();
        }
    }
}
