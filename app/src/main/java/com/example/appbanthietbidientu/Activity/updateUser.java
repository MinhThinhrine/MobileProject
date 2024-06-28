package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        // Ánh xạ các thành phần từ XML
        anhxa();
        getData();
        buttonUUser.setOnClickListener(v -> {
            // Thực hiện các thao tác khi buttonAdd được nhấn
        });
        radioGroupUser.setOnCheckedChangeListener((group, checkedId) -> {
            // Xử lý khi người dùng chọn role
        });
        newimgBackUDUser.setOnClickListener(v -> {
            finish();
        });
    }

    private void getData() {
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
        checkRoleUUser1 = findViewById(R.id.checkRoleUUser1);
        checkRoleUUser2 = findViewById(R.id.checkRoleUUser2);
        buttonUUser = findViewById(R.id.buttonUUser);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "bundle bị null", Toast.LENGTH_SHORT).show();
        }
        sanpham = (Sanpham) bundle.get("object_sp");
    }
}