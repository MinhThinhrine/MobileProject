package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appbanthietbidientu.Adapter.prdAdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class manageProduct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Sanpham> sanphamList;
    private prdAdminAdapter prdAdminAdapter;
    private TextView textView;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        textView = findViewById(R.id.numProduct); // Ánh xạ TextView
        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            getData();
            anhxa();
            back();
            add();
        } else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Error Connect Internet");
        }
    }

    private void add() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manageProduct.this,addProduct.class));
            }
        });
    }

    private void back() {
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manageProduct.this,HomeAdmin.class));
            }
        });
    }

    private void getData() {
        sanphamList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getSharedPreferences("dataProduct", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("sanpham","");
        databaseReference.child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanphamList.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Sanpham sanpham = dataSnapshot.getValue(Sanpham.class);
                        sanphamList.add(sanpham);
                    }
                    // Sau khi cập nhật dữ liệu, cần thông báo cho Adapter biết là dữ liệu đã thay đổi
                    prdAdminAdapter.notifyDataSetChanged();
//                    Log.d("ERRRRRRRRRRRRRRR","List "+sanphamList.size());
                    // Cập nhật số lượng sản phẩm lên TextView
                    textView.setText("  Số sản phẩm: "+sanphamList.size());
                } else {
                    Toast.makeText(manageProduct.this, "Không có sản phẩm nào tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhxa() {
        recyclerView = findViewById(R.id.cyclistadm);
        toolbar = findViewById(R.id.titleProduct);
        prdAdminAdapter = new prdAdminAdapter(sanphamList, getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(manageProduct.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(prdAdminAdapter);

        String title = sharedPreferences.getString("title","");
        toolbar.setTitle(title);
        button = findViewById(R.id.addProduct);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prdAdminAdapter != null) {
            prdAdminAdapter.cleanContext();
        }
    }
}
