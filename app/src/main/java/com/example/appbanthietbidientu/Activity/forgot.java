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
import com.example.appbanthietbidientu.Activity.LoginActivity;
import com.example.appbanthietbidientu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    TextView xacnhan;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        khaibao();
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAccount = account.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(strAccount).matches()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else {
                    Query emailQuery = usersRef.orderByChild("email").equalTo(strEmail);
                    emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                                String userId = userSnapshot.getKey();
                                sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
                                    Toast.makeText(getApplicationContext(), "Xác nhận", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(forgot.this, LoginActivity.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", strEmail);
                                    editor.apply();
                                    startActivity(intent);
                                    finish();
                                }else {
                                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public void khaibao(){
        email = findViewById(R.id.edtAccount);
        xacnhan = findViewById(R.id.xacnhan);
    }
}
