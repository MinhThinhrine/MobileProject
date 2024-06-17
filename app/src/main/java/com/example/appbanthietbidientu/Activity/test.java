package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.appbanthietbidientu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Khởi tạo Firebase Database với URL cụ thể
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // Ghi dữ liệu vào Firebase Database
        myRef.setValue("Hello, World!").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Dữ liệu đã được ghi thành công!");
            } else {
                Log.e("Firebase", "Ghi dữ liệu thất bại: " + task.getException().getMessage());
            }
        });

        // Ghi dữ liệu con vào Firebase Database
        myRef.child("ABC").setValue("1234").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Dữ liệu con đã được ghi thành công!");
            } else {
                Log.e("Firebase", "Ghi dữ liệu con thất bại: " + task.getException().getMessage());
            }
        });
    }
}