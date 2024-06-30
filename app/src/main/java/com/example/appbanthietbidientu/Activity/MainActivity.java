package com.example.appbanthietbidientu.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanthietbidientu.Adapter.LoaispAdapter;
import com.example.appbanthietbidientu.Adapter.SanphammoiAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Loaisp;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.model.Sanphammoi;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ViewFlipper viewFlipper;
    ProgressBar loadSpMoi,loadMain;
    RecyclerView listSanPhamMoi;
    ListView listManHinhChinh;
    ArrayList<Loaisp> loaispArrayList;
    ArrayList<Sanpham> sanphamArrayList;
    TextView titleHome,logout,txtEmail;
    public static ArrayList<GioHang> gioHangArrayList;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Khaibao();

        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            ActionBar();
            GetDuLieusp();
            ActionViewFlip();
            ClickItemManHinhChinh();
        }else{
            Toast.makeText(getApplicationContext(),"Error connect Internet",Toast.LENGTH_LONG).show();
            finish();
        }

        Typeface andro = ResourcesCompat.getFont(MainActivity.this,R.font.svn_androgyne);
        titleHome.setTypeface(andro);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Animation anim_in_right= AnimationUtils.loadAnimation(this,R.anim.anim_in_right);
        Animation anim_out_right=AnimationUtils.loadAnimation(this,R.anim.anim_out_right);

        viewFlipper.setInAnimation(anim_in_right);
        viewFlipper.setOutAnimation(anim_out_right);
    }

    private void ClickItemManHinhChinh() {
        listManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.close();
                        break;
                    case 1:
                        Intent dienthoai = new Intent(MainActivity.this, DienThoaiActivity.class);
                        dienthoai.putExtra("idloaisanpham", loaispArrayList.get(i).getId());
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(MainActivity.this, LaptopActivity.class);
                        laptop.putExtra("idloaisanpham", loaispArrayList.get(i).getId());
                        startActivity(laptop);
                        break;
                    case 3:
                        Intent lienhe = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 4:
                        Intent thongtin=new Intent(MainActivity.this, inforUser.class);
                        startActivity(thongtin);
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, HomeAdmin.class));
                        break;
                }
            }
        });
    }

    private void GetDuLieusp() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        loadMain.setVisibility(View.VISIBLE);

        databaseReference.child("sanphammoi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanphamArrayList.clear();
                for(DataSnapshot nap : snapshot.getChildren()){
                    Sanpham sanphammoi = nap.getValue(Sanpham.class);
                    sanphamArrayList.add(sanphammoi);
                }
                SanphammoiAdapter sanphamAdapter = new SanphammoiAdapter(sanphamArrayList, getApplicationContext());
                listSanPhamMoi.setAdapter(sanphamAdapter);

                listSanPhamMoi.setAdapter(sanphamAdapter);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                listSanPhamMoi.setLayoutManager(gridLayoutManager);
                loadMain.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                loadMain.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void ActionViewFlip() {
        ArrayList<String> mangQuangCao=new ArrayList<>();
        mangQuangCao.add("https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/2024/06/banner/1920x450--1--1920x450.jpg");
        mangQuangCao.add("https://anphat.com.vn/media/lib/19-05-2021/esamz7rxyaaxax5.jpg");
        mangQuangCao.add("https://r1.community.samsung.com/t5/image/serverpage/image-id/7729916iCF466CB66DC564B1/image-size/large?v=v2&px=999");
        mangQuangCao.add("https://pbs.twimg.com/media/Eg7iPyrUYAAhi4c?format=jpg&name=900x900");

        for(int i=0;i<mangQuangCao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(MainActivity.this).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.iconGioHang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Khaibao() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.Navigation);
        loadSpMoi = findViewById(R.id.loadSpMoi);
        loadMain = findViewById(R.id.loadMain);
        viewFlipper = findViewById(R.id.viewflipper);
        listManHinhChinh = findViewById(R.id.listManhinhchinh);
        listSanPhamMoi = findViewById(R.id.listSanphammoi);
        titleHome = findViewById(R.id.titleHome);
        logout = findViewById(R.id.logout);
        txtEmail = findViewById(R.id.txtEmail);
        sanphamArrayList = new ArrayList<>();


        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role","");
        String email = sharedPreferences.getString("email","");
        txtEmail.setText(email);

        loaispArrayList=new ArrayList<>();
        loaispArrayList.add(0,new Loaisp(0,"Trang chính",R.drawable.ic_action_home));
        loaispArrayList.add(1,new Loaisp(0,"Điện thoại",R.drawable.ic_action_phone));
        loaispArrayList.add(2,new Loaisp(0,"Laptop",R.drawable.ic_action_laptop));
        loaispArrayList.add(3,new Loaisp(0,"Liên hệ",R.drawable.ic_action_contact));
        loaispArrayList.add(4,new Loaisp(0,"Thông tin tài khoản",R.drawable.ic_action_infor));
        if(role.contains("admin")){
            loaispArrayList.add(5,new Loaisp(0,"Admin",R.drawable.icon_user_40));
            Toast.makeText(this, "Role:"+role, Toast.LENGTH_SHORT).show();
        }

        LoaispAdapter loaispAdapter=new LoaispAdapter(loaispArrayList,MainActivity.this);
        listManHinhChinh.setAdapter(loaispAdapter);

        if(gioHangArrayList != null){

        }else {
            gioHangArrayList=new ArrayList<>();
        }
    }
}