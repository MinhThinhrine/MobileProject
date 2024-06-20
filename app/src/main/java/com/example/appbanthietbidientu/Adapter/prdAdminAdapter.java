package com.example.appbanthietbidientu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.Activity.updateProduct;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.itemInterface.IDelete;
import com.example.appbanthietbidientu.model.Sanpham;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class prdAdminAdapter extends RecyclerView.Adapter<prdAdminAdapter.productHolder>{
    private List<Sanpham> sanphamList;
    Context context;
    private IDelete iDelete;
    public prdAdminAdapter(List<Sanpham> sanphamList, Context context,IDelete iDelete) {
        this.sanphamList = sanphamList;
        this.context = context;
        this.iDelete = iDelete;
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
        holder.bntSuasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnclickGotoUpdateProduct(sanpham);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDelete.deleteProduct(sanpham);
                Toast.makeText(context, "Xóa sản phẩm: "+sanpham.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OnclickGotoUpdateProduct(Sanpham sanpham){
        Intent intent = new Intent(context, updateProduct.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_sp",sanpham);
        intent.putExtras(bundle);
        // Kiểm tra nếu context là một Activity
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        if(sanphamList != null){
            return sanphamList.size();
        }
        return 0;
    }

    public void cleanContext(){
        context = null;
    }

    class productHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        private TextView txtGia;
        private Button bntSuasp,btnXoa;
        private TextView txtTen;
        private ImageView anhsp;

        public productHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layoutprdadm);
            txtTen = itemView.findViewById(R.id.Ten_pd);
            txtGia = itemView.findViewById(R.id.giapd);
            anhsp = itemView.findViewById(R.id.anhsp);
            bntSuasp = itemView.findViewById(R.id.bntSuasp);

            btnXoa = itemView.findViewById(R.id.btnXoaPd);
        }
    }
}
