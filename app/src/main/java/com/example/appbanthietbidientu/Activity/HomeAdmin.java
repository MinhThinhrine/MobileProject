package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.appbanthietbidientu.Adapter.itemAdminAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Item_admin;

import java.util.ArrayList;

public class HomeAdmin extends AppCompatActivity {
    GridView item;
    ArrayList<Item_admin> itemAdmins;
    itemAdminAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        Anhxa();

        adminAdapter = new itemAdminAdapter(this, R.layout.dong_item_admin, itemAdmins);

        item.setAdapter(adminAdapter);
        clickItemadmin();
    }

    private void clickItemadmin() {
        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HomeAdmin.this, "position"+i, Toast.LENGTH_SHORT).show();
                switch (i){
                    case 0:
                        startActivity(new Intent(HomeAdmin.this,manageUser.class));
                }
            }
        });
    }

    private void Anhxa() {
        item = findViewById(R.id.gridviewitem);
        itemAdmins = new ArrayList<>();

        // Thêm dữ liệu vào danh sách itemAdmins
        itemAdmins.add(new Item_admin(R.drawable.icon_email, "Quản lý người dùng"));
        itemAdmins.add(new Item_admin(R.drawable.ic_action_phone, " Sản phẩm điện thoại"));
        itemAdmins.add(new Item_admin(R.drawable.ic_action_laptop, " sản phẩm Laptop"));
        itemAdmins.add(new Item_admin(R.drawable.ic_action_giohang, "Sản phẩm mới"));
        itemAdmins.add(new Item_admin(R.drawable.ic_action_giohang, "Sản phẩm mới"));
        itemAdmins.add(new Item_admin(R.drawable.ic_action_giohang, "Sản phẩm mới"));


    }
}
