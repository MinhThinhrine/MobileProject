package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LichsudhAdapter extends BaseAdapter {
    private List<GioHang> gioHangList;
    Context context;

    public LichsudhAdapter(List<GioHang> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView tenDienThoai,giaDienThoai,motaDienThoai;
        ImageView hinhAnhDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_sanpham,null);
            viewHolder.tenDienThoai = view.findViewById(R.id.tenDienThoai);
            viewHolder.giaDienThoai = view.findViewById(R.id.giaDienThoai);
            viewHolder.hinhAnhDienThoai = view.findViewById(R.id.imageviewDienThoai);
            viewHolder.motaDienThoai = view.findViewById(R.id.motaDienThoai);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GioHang lsdh = gioHangList.get(i);
        // Lấy thông tin từ gioHangArrayList của lsdh
        String tenDienThoai = lsdh.getTensp(); // Lấy tên sản phẩm
        int soluongsp = lsdh.getSoluongsp(); // Lấy số lượng sản phẩm
        long giasp = lsdh.getGiasp(); // Lấy giá sản phẩm

        // Hiển thị thông tin lên các thành phần giao diện
        viewHolder.tenDienThoai.setText(tenDienThoai);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giaDienThoai.setText("Giá: " + decimalFormat.format(giasp)+"₫");
        viewHolder.motaDienThoai.setMaxLines(2);
        viewHolder.motaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.motaDienThoai.setText("Số lượng: " + soluongsp); // Hiển thị số lượng sản phẩm

        // Load hình ảnh sử dụng Picasso
        Picasso.with(context).load(lsdh.getHinhsp())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(viewHolder.hinhAnhDienThoai);

        return view;
    }
}
