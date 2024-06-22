package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.appbanthietbidientu.response.SignInResponse;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView titleLogin, registerAccount, forgotPassword;
    EditText edtAccount, edtPassword;
    TextView login;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        khaibao();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("User");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtAccount.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strEmail.isEmpty() || strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    Query emailQuery = usersRef.orderByChild("email").equalTo(strEmail);
                    emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                                String userId = userSnapshot.getKey();
                                String storedPassword = userSnapshot.child("pass").getValue(String.class);
                                sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);

                                if(storedPassword!=null && storedPassword.equals(strPassword)){
                                    String role = userSnapshot.child("role").getValue(String.class);
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("role", role);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("role",role);
                                    editor.putString("email", strEmail);
                                    editor.apply();
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                }
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

        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, forgot.class);
                startActivity(intent);
            }
        });
    }
    public void khaibao(){
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        login = findViewById(R.id.login);
        registerAccount = findViewById(R.id.register_account);
        forgotPassword = findViewById(R.id.forgot_password);
    }
}
