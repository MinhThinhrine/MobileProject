package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class prdAdminAdapter extends RecyclerView.Adapter<prdAdminAdapter.productHolder>{
    private List<Sanpham> sanphamList;
    Context context;

    public prdAdminAdapter(List<Sanpham> sanphamList, Context context) {
        this.sanphamList = sanphamList;
        this.context = context;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false);
        return new productHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Sanpham sanpham = sanphamList.get(position);
        if(sanpham == null){
            return;
        }
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(holder.anhsp);
        holder.txtTen.setText(sanpham.getTensanpham());
        holder.txtGia.setText("Giá: " + decimalFormat.format(Integer.parseInt(sanpham.getGiasanpham())) + "₫");

    }

    @Override
    public int getItemCount() {
        if(sanphamList != null){
            return sanphamList.size();
        }
        return 0;
    }

    class productHolder extends RecyclerView.ViewHolder{
        private TextView txtGia;
        private TextView txtTen;
        private ImageView anhsp;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.Ten_pd);
            txtGia = itemView.findViewById(R.id.giapd);
            anhsp = itemView.findViewById(R.id.anhsp);

        }
    }
}
