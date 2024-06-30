package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.Adapter.LichsudhAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Lichsu_donhang;

import java.util.ArrayList;

public class ChitietDonHang extends AppCompatActivity {

    Toolbar toolbarChitietls;
    TextView maDonHang, ngayMua, tongTien, trangThai, tenKhachHang, diaChi, btnCancel;
    Lichsu_donhang lsdh;
    LichsudhAdapter gioHangAdapter;
    ListView lsdhList;
    ArrayList<GioHang> lsdhchitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietlichsu);

        overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);

        Khaibao();
        setupActionBar();
        getInfordh();
//        setupCancelClick();
    }

//    private void setupCancelClick() {
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (lsdh != null) {
//                    // Perform necessary action to cancel the order
//                    Toast.makeText(getApplicationContext(), "Đơn hàng đã được xóa", Toast.LENGTH_SHORT).show();
//
//                    // Return to the ListDonHangActivity
//                    Intent intent = new Intent(getApplicationContext(), ListDonHangActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear previous activities
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Đơn hàng không tồn tại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    @SuppressLint("WrongViewCast")
    private void getInfordh() {
        lsdh = (Lichsu_donhang) getIntent().getSerializableExtra("thongtindonhang");
        if (lsdh != null) {
            lsdhchitiet = lsdh.getGioHangArrayList();
            maDonHang.setText(lsdh.getMaDonHang());
            ngayMua.setText(lsdh.getNgayDatHang());
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

    private void setupActionBar() {
        toolbarChitietls.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Go back to the previous activity
            }
        });
    }

    private void Khaibao() {
        toolbarChitietls = findViewById(R.id.toolchitietlsdh);
        maDonHang = findViewById(R.id.madonhang);
        ngayMua = findViewById(R.id.ngaydathang);
        tongTien = findViewById(R.id.tongtiendh);
        trangThai = findViewById(R.id.trangthai);
        tenKhachHang = findViewById(R.id.tenKh);
        diaChi = findViewById(R.id.diachigiaohang);
        btnCancel = findViewById(R.id.cancel_order);
        lsdhList = findViewById(R.id.recyclerView);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
