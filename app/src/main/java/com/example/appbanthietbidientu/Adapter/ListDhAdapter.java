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
import com.example.appbanthietbidientu.model.Lichsu_donhang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ListDhAdapter extends BaseAdapter {
    private List<Lichsu_donhang> lsList;
    private Context context;

    public ListDhAdapter(List<Lichsu_donhang> lsList, Context context) {
        this.lsList = lsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lsList.size();
    }

    @Override
    public Object getItem(int i) {
        return lsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView tenDienThoai, giaDienThoai, motaDienThoai;
        ImageView hinhAnhDienThoai;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
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

        Lichsu_donhang lsdh = lsList.get(i);
            // Lấy thông tin từ gioHangArrayList của lsdh
            String tenDienThoai = lsdh.getGioHangArrayList().get(0).getTensp(); // Lấy tên sản phẩm
            int soluongsp = lsdh.getGioHangArrayList().size(); // Lấy số lượng sản phẩm
            String giasp = lsdh.getTongTien(); // Lấy giá sản phẩm

            // Hiển thị thông tin lên các thành phần giao diện
            viewHolder.tenDienThoai.setText(tenDienThoai);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolder.giaDienThoai.setText("Tổng tiền: " + giasp);
            viewHolder.motaDienThoai.setMaxLines(2);
            viewHolder.motaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
            viewHolder.motaDienThoai.setText("Số sản phẩm: " + soluongsp); // Hiển thị số lượng sản phẩm

            // Load hình ảnh sử dụng Picasso
            Picasso.with(context).load(lsdh.getGioHangArrayList().get(0).getHinhsp())
                    .placeholder(R.drawable.loadimage)
                    .error(R.drawable.errorimage)
                    .into(viewHolder.hinhAnhDienThoai);

        return view;
    }
}
