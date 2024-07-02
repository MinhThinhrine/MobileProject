//package com.example.appbanthietbidientu.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.appbanthietbidientu.R;
//
//public class manageOder extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quan_ly_don_hang);
//    }
//}

package com.example.appbanthietbidientu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.Adapter.ListDhAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Lichsu_donhang;
import com.example.appbanthietbidientu.ultil.BaseFunctionActivity;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class manageOder extends BaseFunctionActivity {

    Toolbar toolbarlichsu;
    ListView listViewlichsu;
    ArrayList<Lichsu_donhang> lichsuArrayList;
    ListDhAdapter lichsuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            Khaibao();
            ActionBar();
            getData();
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Error Connect Internet");
        }
        listViewlichsu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AdminCTDH.class);
                intent.putExtra("thongtindonhang", lichsuArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Lsdh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lichsuArrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Lichsu_donhang lsdh = dataSnapshot.getValue(Lichsu_donhang.class);
                        lichsuArrayList.add(lsdh);
                    }
                    lichsuAdapter = new ListDhAdapter(lichsuArrayList, getApplicationContext());
                    listViewlichsu.setAdapter(lichsuAdapter);
                } else {
                    Toast.makeText(manageOder.this, "Không tồn tại đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error: "+ error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ActionBar() {
        toolbarlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manageOder.this,HomeAdmin.class));
            }
        });
    }

    private void Khaibao() {
        toolbarlichsu = findViewById(R.id.toolchitietlsdh);
        listViewlichsu = findViewById(R.id.listLsdh);
        lichsuArrayList = new ArrayList<>();
    }
}
