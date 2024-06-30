package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Sanpham;
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
        ViewHolder viewHolder=new ViewHolder();
        if(view == null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_sanpham,null);
            viewHolder.tenDienThoai=view.findViewById(R.id.tenDienThoai);
            viewHolder.giaDienThoai=view.findViewById(R.id.giaDienThoai);
            viewHolder.hinhAnhDienThoai=view.findViewById(R.id.imageviewDienThoai);
            viewHolder.motaDienThoai=view.findViewById(R.id.motaDienThoai);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        GioHang dienThoai=gioHangList.get(i);
        viewHolder.tenDienThoai.setText(dienThoai.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giaDienThoai.setText("Tổng tiền: " + decimalFormat.format(dienThoai.getGiasp()) + "₫");
        viewHolder.motaDienThoai.setMaxLines(2);
        viewHolder.motaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.motaDienThoai.setText(dienThoai.getSoluongsp());
        Picasso.with(context).load(dienThoai.getHinhsp())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(viewHolder.hinhAnhDienThoai);
        return view;
    }
}
