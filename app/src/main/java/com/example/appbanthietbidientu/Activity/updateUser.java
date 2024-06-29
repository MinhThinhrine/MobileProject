package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateUser extends AppCompatActivity {
    private TextView toolbar_UDUser;
    private ImageView newimgBackUDUser;
    private TextView idUUser;
    private TextView EUUser;
    private EditText PhoneUUser;
    private EditText adrUser;
    private EditText nameUUser;
    private RadioGroup radioGroupUser;
    private RadioButton checkRoleUUser1;
    private RadioButton checkRoleUUser2;
    private Button buttonUUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        anhxa();
        setData();
        buttonUUser.setOnClickListener(v -> {
            String email = EUUser.getText().toString().trim();
            String phone = PhoneUUser.getText().toString().trim();
            String address = adrUser.getText().toString().trim();
            String name = nameUUser.getText().toString().trim();
            String role = getCheckedRadioButtonId();
            if (email.isEmpty() || phone.isEmpty() || address.isEmpty() || name.isEmpty() || role.isEmpty()) {
                Toast.makeText(updateUser.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                User user1 = new User(email, user.getPass(), user.getId(), phone, address, role, name);
                databaseReference.child(String.valueOf(user.getId())).setValue(user1).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(updateUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        PhoneUUser.setText("");
                        adrUser.setText("");
                        nameUUser.setText("");
                        checkRoleUUser1.setChecked(true);
                    } else {
                        Toast.makeText(updateUser.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        newimgBackUDUser.setOnClickListener(v -> {
            finish();
        });
    }

    private void setData() {
        idUUser.setText(user.getId()+"");
        EUUser.setText(user.getEmail()+"");
        PhoneUUser.setText(user.getPhone()+"");
        adrUser.setText(user.getAddress()+"");
        nameUUser.setText(user.getUserName());
        if (user.getRole().equals("user")) {
            checkRoleUUser1.setChecked(true);
        } else {
            checkRoleUUser2.setChecked(true);
        }
    }
    private String getCheckedRadioButtonId() {
        int checkedId = radioGroupUser.getCheckedRadioButtonId();
        if(checkedId == (R.id.checkRoleUUser1)){
            return "user";
        } else if (checkedId == (R.id.checkRoleUUser2)) {
            return "admin";
        }
        return "";
    }

    private void anhxa() {
        toolbar_UDUser = findViewById(R.id.toolbar_UDUser);
        newimgBackUDUser = findViewById(R.id.newimgBackUDUser);
        idUUser = findViewById(R.id.idUUser);
        EUUser = findViewById(R.id.EUUser);
        PhoneUUser = findViewById(R.id.PhoneUUser);
        adrUser = findViewById(R.id.adrUser);
        nameUUser = findViewById(R.id.nameUUser);
        radioGroupUser = findViewById(R.id.radioGroupUser);
        buttonUUser = findViewById(R.id.buttonUUser);
        checkRoleUUser1 = findViewById(R.id.checkRoleUUser1);
        checkRoleUUser2 = findViewById(R.id.checkRoleUUser2);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "bundle bị null", Toast.LENGTH_SHORT).show();
        }
        user = (User) bundle.get("object_us");
    }
}