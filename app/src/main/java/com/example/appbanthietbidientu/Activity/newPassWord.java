package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appbanthietbidientu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newPassWord extends AppCompatActivity {
    private EditText oldPass;
    private EditText newPass1;
    private EditText newPass2;
    private TextView btnNewPass;
    private TextView backInfor;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_word);
        anhxa();

        btnNewPass.setOnClickListener(v -> {
            String oldPassword = oldPass.getText().toString();
            String newPassword1 = newPass1.getText().toString();
            String newPassword2 = newPass2.getText().toString();

            if (oldPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()) {
                Toast.makeText(newPassWord.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (newPassword1.length() < 5 || newPassword2.length() < 5) {
                Toast.makeText(newPassWord.this, "Mật khẩu mới phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
            } else if (!newPassword1.equals(newPassword2)) {
                Toast.makeText(newPassWord.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            } else {
                if (id != -1) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(String.valueOf(id));
                    reference.child("pass").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pass1 = snapshot.getValue(String.class);
                            if (!oldPassword.equals(pass1)) {
                                Toast.makeText(newPassWord.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            } else {
                                reference.child("pass").setValue(newPassword1);
                                Toast.makeText(newPassWord.this, "Thông tin người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();
                                oldPass.setText("");
                                newPass1.setText("");
                                newPass2.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(newPassWord.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(newPassWord.this, "ID người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backInfor.setOnClickListener(v -> {
            finish();
        });
    }

    private void anhxa() {
        oldPass = findViewById(R.id.oldPass);
        newPass1 = findViewById(R.id.newPass1);
        newPass2 = findViewById(R.id.newPass2);
        btnNewPass = findViewById(R.id.BntnewPass);
        backInfor = findViewById(R.id.backInfor);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("idUser")) {
            id = intent.getIntExtra("idUser", -1); // Giá trị mặc định là -1 nếu không có dữ liệu
            Toast.makeText(this, "Received idUser: " + id, Toast.LENGTH_SHORT).show();
        }
    }
}
