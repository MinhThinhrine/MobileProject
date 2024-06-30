package com.example.appbanthietbidientu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Lichsu_donhang;

import java.util.List;

public class LichsudhAdapter extends BaseAdapter {
    private List<Lichsu_donhang> lichsudhList;
    private Context context;

    public LichsudhAdapter(Context context, List<Lichsu_donhang> lichsudhList) {
        this.context = context;
        this.lichsudhList = lichsudhList;
    }

    @Override
    public int getCount() {
        return lichsudhList.size();
    }

    @Override
    public Object getItem(int i) {
        return lichsudhList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView maDonHang, ngayMua, tongTien, trangThai, tenKhachHang, diaChi, btnCancel;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.dong_sanpham, null);

            viewHolder.maDonHang = view.findViewById(R.id.madonhang);
            viewHolder.ngayMua = view.findViewById(R.id.ngaydathang);
            viewHolder.tongTien = view.findViewById(R.id.tongtiendh);
            viewHolder.trangThai = view.findViewById(R.id.trangthai);
            viewHolder.tenKhachHang = view.findViewById(R.id.tenKh);
            viewHolder.diaChi = view.findViewById(R.id.diachigiaohang);
            viewHolder.btnCancel = view.findViewById(R.id.cancel_order);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Lichsu_donhang lichsu_donhang = lichsudhList.get(i);

        viewHolder.maDonHang.setText(lichsu_donhang.getMaDonHang());
        viewHolder.ngayMua.setText(lichsu_donhang.getNgayDatHang());
        viewHolder.tongTien.setText(String.valueOf(lichsu_donhang.getTongTien()));
        viewHolder.trangThai.setText(lichsu_donhang.getTrangThaiDonHang());
        viewHolder.tenKhachHang.setText(lichsu_donhang.getTenKhachHang());
        viewHolder.diaChi.setText(lichsu_donhang.getDiaChiGiaoHang());

        viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện hủy đơn hàng
            }
        });

        return view;
    }
}
