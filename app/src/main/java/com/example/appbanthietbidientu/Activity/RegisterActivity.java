package com.example.appbanthietbidientu.Activity;

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
    EditText account, password, passwordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        register = findViewById(R.id.register);
        account = findViewById(R.id.textAccountRegister);
        password = findViewById(R.id.textPasswordRegister);
        passwordRepeat = findViewById(R.id.textRepeatPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAccount = account.getText().toString().trim();
                String strPassword = password.getText().toString();
                String strPasswordRepeat = passwordRepeat.getText().toString();

                if (strAccount.isEmpty() || strPassword.isEmpty() || strPasswordRepeat.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strAccount).matches()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (!strPassword.equals(strPasswordRepeat)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    // Khởi tạo Firebase
                    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = firebase.getReference("User");

                    // Lấy ID của user mới nhất từ Firebase
                    ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Kiểm tra xem có dữ liệu trả về không
                            if (snapshot.exists()) {
                                // Lặp qua tất cả các children để lấy ID của user cuối cùng
                                String latestUserId = "";
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    latestUserId = child.getKey();
                                }

                                // Chuyển đổi latestUserId thành kiểu int (nếu cần)
                                int newUserId = Integer.parseInt(latestUserId) + 1;

                                // Tạo đối tượng User
                                User user = new User(strAccount, strPassword, newUserId);

                                ref.child(String.valueOf(newUserId)).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                    account.setText("");
                                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                int newUserId = 1;

                                User user = new User(strAccount, strPassword, newUserId);

                                ref.child(String.valueOf(newUserId)).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Đã lưu user vào Firebase", Toast.LENGTH_SHORT).show();
                                                    account.setText(""); // Xóa EditText sau khi lưu thành công
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
        });
    }
}
