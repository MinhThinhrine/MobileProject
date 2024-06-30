package com.example.appbanthietbidientu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.Adapter.SanphamAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.ultil.BaseFunctionActivity;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListDonHangActivity extends BaseFunctionActivity {

    Toolbar toolbarlichsu;
    ProgressBar loadlichsu;
    ListView listViewlichsu;
    ArrayList<Sanpham> lichsuArrayList;
    SanphamAdapter lichsuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu);

        if (CheckConnect.haveNetworkConnected(getApplicationContext())) {
            Khaibao();
            ActionBar();
            getData();
        } else {
            CheckConnect.ShowToast_Short(getApplicationContext(), "Error Connect Internet");
        }

        listViewlichsu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChitietDonHang.class);
                intent.putExtra("thongtindonhang", lichsuArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        loadlichsu.setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Lsdh").child("1").child("giohangArrayList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lichsuArrayList.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String tensp = dataSnapshot.child("tensp").getValue(String.class);
                                long soluongsp = dataSnapshot.child("soluongsp").getValue(Long.class);
                                // Create your model object or process the data as needed
                                // Example:
                                GioHangItem gioHangItem = new GioHangItem(tensp, (int) soluongsp);
                                lichsuArrayList.add(gioHangItem);
                            }
                            lichsuAdapter = new SanphamAdapter(lichsuArrayList, getApplicationContext());
                            listViewlichsu.setAdapter(lichsuAdapter);
                            loadlichsu.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(lichsuActivity.this, "Không tồn tại sản phẩm nào", Toast.LENGTH_SHORT).show();
                            loadlichsu.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadlichsu.setVisibility(View.INVISIBLE);
                    }
                });
    }


    private void ActionBar() {
        toolbarlichsu = findViewById(R.id.addtoolbar3);
        setSupportActionBar(toolbarlichsu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlichsu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Khaibao() {
        toolbarlichsu = findViewById(R.id.addtoolbar3);
        listViewlichsu = findViewById(R.id.recyclerViewLichSu);
        lichsuArrayList = new ArrayList<>();
    }
}
