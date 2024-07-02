package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.Adapter.LichsudhAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Lichsu_donhang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminCTDH extends AppCompatActivity {

    Toolbar toolbarChitietls;
    TextView maDonHang, ngayMua, tongTien, trangThai, tenKhachHang, diaChi, btnCancel;
    Lichsu_donhang lsdh;
    LichsudhAdapter gioHangAdapter;
    ListView lsdhList;
    ArrayList<GioHang> lsdhchitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminctdh);

        overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);

        Khaibao();
        ActionBar();
        getInfordh();
        setupCancelClick();
    }
    private void setupCancelClick() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lsdh != null) {
                    // Thực hiện xóa đơn hàng từ Firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Lsdh").child(lsdh.getMaDonHang());

                    myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Đơn hàng đã được xóa", Toast.LENGTH_SHORT).show();

                                // Quay trở lại ListDonHangActivity
                                Intent intent = new Intent(getApplicationContext(), ListDonHangActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear previous activities
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi khi xóa đơn hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Đơn hàng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint("WrongViewCast")
    private void getInfordh() {
        lsdh = (Lichsu_donhang) getIntent().getSerializableExtra("thongtindonhang");
        if (lsdh != null) {
            lsdhchitiet = lsdh.getGioHangArrayList();
            maDonHang.setText("Mã đơn hàng: "+lsdh.getMaDonHang());
            ngayMua.setText("Ngày mua: "+lsdh.getNgayDatHang());
            tongTien.setText(lsdh.getTongTien());
            trangThai.setText(lsdh.getTrangThaiDonHang());
            tenKhachHang.setText(lsdh.getTenKhachHang());
            diaChi.setText(lsdh.getDiaChiGiaoHang());

            gioHangAdapter = new LichsudhAdapter(lsdhchitiet, getApplicationContext());
            lsdhList.setAdapter(gioHangAdapter);
        } else {
            Toast.makeText(this, "Không thể lấy thông tin đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void ActionBar() {
        toolbarChitietls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCTDH.this,ListDonHangActivity.class));
            }
        });
    }

    private void Khaibao() {
        toolbarChitietls = findViewById(R.id.toolchitietlsdha);
        maDonHang = findViewById(R.id.madonhanga);
        ngayMua = findViewById(R.id.ngaydathanga);
        tongTien = findViewById(R.id.tongtiendha);
        trangThai = findViewById(R.id.trangthaia);
        tenKhachHang = findViewById(R.id.tenKha);
        diaChi = findViewById(R.id.diachigiaohanga);
        btnCancel = findViewById(R.id.cancel_ordera);
        lsdhList = findViewById(R.id.recyclerViewa);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
