package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addUser extends AppCompatActivity {
    EditText edt1,edt2,edt3;
    Button bntAdd;
    RadioGroup radioGroup;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        anhxa();
        back();
        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt1.getText().toString().trim();
                String pass1 = edt1.getText().toString().trim();
                String pass2 = edt1.getText().toString().trim();
                String checked = getCheckedRadioButtonId();

                if (email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                    Toast.makeText(addUser.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(addUser.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (pass1.length() < 6) {
                    Toast.makeText(addUser.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (!pass1.equals(pass2)) {
                    Toast.makeText(addUser.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                } else if (checked.isEmpty()) {
                    Toast.makeText(addUser.this, "Vui lòng chọn quyền", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = firebase.getReference("User");

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
                                User user = new User(email, pass1, newUserId,"user");

                                ref.child(String.valueOf(newUserId)).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(addUser.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                    edt1.setText("");
                                                    edt2.setText("");
                                                    edt3.setText("");
                                                } else {
                                                    Toast.makeText(addUser.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                int newUserId = 1;
                                User user = new User(email, pass1, newUserId,"user");
                                ref.child(String.valueOf(newUserId)).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(addUser.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                    edt1.setText("");
                                                    edt2.setText("");
                                                    edt3.setText("");
                                                    startActivity(new Intent(addUser.this,LoginActivity.class));

                                                } else {
                                                    Toast.makeText(addUser.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(addUser.this, "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Toast.makeText(addUser.this, "check"+checked, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String getCheckedRadioButtonId() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(checkedId == (R.id.checkRole1)){
            return "user";
        } else if (checkedId == (R.id.checkRole2)) {
            return "admin";
        }
        return "";
    }

    private void back() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addUser.this,manageUser.class));
            }
        });
    }



    private void anhxa() {
        edt1 = findViewById(R.id.newE);
        edt2 = findViewById(R.id.newP1);
        edt3 = findViewById(R.id.newP2);
        bntAdd = findViewById(R.id.buttonAdd);
        radioGroup = findViewById(R.id.radioGroup);
        imgback = findViewById(R.id.newimgBack);
    }
}