package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;

public class updateProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            Toast.makeText(this, "bundle bị null", Toast.LENGTH_SHORT).show();
        }

        Sanpham sanpham = (Sanpham) bundle.get("object_sp");
        TextView textView = findViewById(R.id.tttvsp);
        textView.setText(sanpham.toString());

    }
}