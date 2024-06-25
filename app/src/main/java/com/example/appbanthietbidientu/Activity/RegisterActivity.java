package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    TextView register;

    EditText account, password, passwordRepeat, username, diachi, sodienthoai;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        khaibao();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAccount = account.getText().toString().trim();
                String strPassword = password.getText().toString().trim();
                String strPasswordRepeat = passwordRepeat.getText().toString().trim();
                String strUsername = username.getText().toString().trim();
                String strDiachi = diachi.getText().toString().trim();
                String strPhone = sodienthoai.getText().toString().trim();

                if (strAccount.isEmpty() || strPassword.isEmpty() || strPasswordRepeat.isEmpty() || strUsername.isEmpty() || strDiachi.isEmpty() || strPhone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strAccount).matches()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (!strPassword.equals(strPasswordRepeat)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = firebase.getReference("User");

                    // Kiểm tra xem tài khoản đã tồn tại chưa
                    ref.orderByChild("email").equalTo(strAccount).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Email đã tồn tại
                                Toast.makeText(RegisterActivity.this, "Tài khoản đã được đăng ký", Toast.LENGTH_SHORT).show();
                            } else {
                                // Email chưa tồn tại, tiến hành đăng ký
                                ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String latestUserId = "";
                                            for (DataSnapshot child : snapshot.getChildren()) {
                                                latestUserId = child.getKey();
                                            }

                                            int newUserId = Integer.parseInt(latestUserId) + 1;
                                            User user = new User(strAccount, strPassword, newUserId, strPhone, strDiachi, "user", strUsername);

                                            ref.child(String.valueOf(newUserId)).setValue(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(RegisterActivity.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                                account.setText("");
                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            int newUserId = 1;
                                            User user = new User(strAccount, strPassword, newUserId, strPhone, strDiachi, "user", strUsername);

                                            ref.child(String.valueOf(newUserId)).setValue(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(RegisterActivity.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                                account.setText(""); // Xóa EditText sau khi lưu thành công
                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(RegisterActivity.this, "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterActivity.this, "Lỗi khi kiểm tra tài khoản", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public void khaibao(){
        account = findViewById(R.id.textAccountRegister);
        password = findViewById(R.id.textPasswordRegister);
        passwordRepeat = findViewById(R.id.textRepeatPassword);
        register = findViewById(R.id.register);
        username = findViewById(R.id.userNameInfor);
        diachi = findViewById(R.id.address);
        sodienthoai = findViewById(R.id.phoneNumber);
    }
}
