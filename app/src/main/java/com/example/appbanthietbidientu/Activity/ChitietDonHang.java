package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanthietbidientu.Adapter.GioHangAdapter;
import com.example.appbanthietbidientu.Adapter.LichsudhAdapter;
import com.example.appbanthietbidientu.Adapter.SanphamAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Lichsu_donhang;
import com.example.appbanthietbidientu.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChitietDonHang extends AppCompatActivity {

        Toolbar toolbarChitietls;
        ImageView anhChitietsp;
        TextView maDonHang, ngayMua, tongTien, trangThai, tenKhachHang, diaChi, btnCancel;
        Spinner spinner;
        Lichsu_donhang lsdh;
        ListView recyclerView;
        LichsudhAdapter gioHangAdapter;
        ListView lsdhList;
        ArrayList<GioHang> lsdhchitiet;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chitietlichsu);

            overridePendingTransition(R.anim.animation_enter_right,R.anim.animation_exit_left);

            Khaibao();
            ActionBar();
            getInfordh();
            EvenClickCancel();
        }

    private void EvenClickCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin đơn hàng từ intent
                Lichsu_donhang lsdh = (Lichsu_donhang) getIntent().getSerializableExtra("thongtindonhang");

                if (lsdh != null) {
                    // Xóa đơn hàng khỏi danh sách trong ListDonHangActivity
                    ListDonHangActivity listDonHangActivity = new ListDonHangActivity();
                    listDonHangActivity.removeLichsuDonhang(lsdh);

                    // Thông báo cho người dùng
                    Toast.makeText(getApplicationContext(), "Đơn hàng đã được xóa", Toast.LENGTH_SHORT).show();

                    // Quay trở lại Activity lịch sử đơn hàng
                    Intent intent = new Intent(getApplicationContext(), ListDonHangActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa bỏ các Activity trước đó
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Đơn hàng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void getInfordh() {
        // Lấy thông tin đơn hàng từ intent
        lsdh = (Lichsu_donhang) getIntent().getSerializableExtra("thongtindonhang");

        if (lsdh != null) {
            // Lấy chi tiết đơn hàng từ đối tượng Lichsu_donhang
            lsdhchitiet = lsdh.getGioHangArrayList();

            // Hiển thị thông tin lên các TextView
            maDonHang.setText(lsdh.getMaDonHang());
            ngayMua.setText(lsdh.getNgayDatHang());
            tongTien.setText(lsdh.getTongTien());
            trangThai.setText(lsdh.getTrangThaiDonHang());
            tenKhachHang.setText(lsdh.getTenKhachHang());
            diaChi.setText(lsdh.getDiaChiGiaoHang());

            // Lấy danh sách GioHang và thiết lập RecyclerView
            ArrayList<GioHang> lsdhchitiet = lsdh.getGioHangArrayList();
            gioHangAdapter = new LichsudhAdapter(lsdhchitiet, getApplicationContext());
            recyclerView.setAdapter(gioHangAdapter);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }


    private void ActionBar() {
        setSupportActionBar(toolbarChitietls);
        toolbarChitietls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChitietDonHang.this,ListDonHangActivity.class));
            }
        });
    }

    private void Khaibao() {
        toolbarChitietls = findViewById(R.id.toolchitietls);
        anhChitietsp = findViewById(R.id.imageviewDienThoai);
        maDonHang = findViewById(R.id.madonhang);
        ngayMua = findViewById(R.id.ngaydathang);
        tongTien = findViewById(R.id.tongtiendh);
        trangThai = findViewById(R.id.trangthai);
        tenKhachHang = findViewById(R.id.tenKh);
        diaChi = findViewById(R.id.diachigiaohang);
        btnCancel = findViewById(R.id.cancel_order);
        recyclerView = findViewById(R.id.recyclerView);
    }


    @Override
        public void finish() {
            super.finish();
        }
}
