package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Lichsu_donhang;
import com.example.appbanthietbidientu.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ThongTinKhachHang extends AppCompatActivity {
    Toolbar toolbarThongTinKhachHang;
    EditText NhapTenKhachHang, NhapSoDienThoai, NhapEmail;
    TextView XacNhan, Huy, txtThanhToanTien;
    ProgressDialog progressDialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        overridePendingTransition(R.anim.animation_scale_enter_right, R.anim.animation_scale_exit_left);

        //set thông báo xác nhận
        progressDialog = new ProgressDialog(ThongTinKhachHang.this);
        progressDialog.setMessage("Please wait ...");

        Khaibao();
        Actionbar();
        ClickXacNhan();
        ClickHuy();
        getDataUser();
    }

    private void ClickHuy() {
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ClickXacNhan() {
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lsdh");
                reference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy; HH:mm:ss");
                        @SuppressLint({"NewApi", "LocalSuppress"}) LocalDateTime now = LocalDateTime.now();
                        @SuppressLint({"NewApi", "LocalSuppress"}) String formattedDateTime = now.format(formatter);

                        if (snapshot.exists()) {
                            String lateOrder = "";
                            for (DataSnapshot child : snapshot.getChildren()) {
                                lateOrder = child.getKey();
                            }
                            int lateOrderINT = Integer.parseInt(lateOrder) + 1;
                            ArrayList<GioHang> list = MainActivity.gioHangArrayList;
                            Lichsu_donhang lichsu_donhang = new Lichsu_donhang(
                                    String.valueOf(lateOrderINT),
                                    list,
                                    String.valueOf(user.getId()),
                                    user.getUserName(),
                                    formattedDateTime,
                                    getIntent().getStringExtra("TotalMoney"),
                                    "Đang xử lý",
                                    user.getAddress()
                            );
                            reference.child(String.valueOf(lateOrderINT)).setValue(lichsu_donhang);
                            MainActivity.gioHangArrayList.clear();
                            Intent intent = new Intent(ThongTinKhachHang.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Cảm ơn bạn đã mua hàng", Toast.LENGTH_LONG).show();
                        } else {
                            int lateOrderINT = 1;
                            ArrayList<GioHang> list = MainActivity.gioHangArrayList;
                            Lichsu_donhang lichsu_donhang = new Lichsu_donhang(
                                    String.valueOf(lateOrderINT),
                                    list,
                                    String.valueOf(user.getId()),
                                    NhapTenKhachHang.getText().toString(),
                                    formattedDateTime,
                                    getIntent().getStringExtra("TotalMoney"),
                                    "Đang xử lý",
                                    user.getAddress()
                            );
                            reference.child(String.valueOf(lateOrderINT)).setValue(lichsu_donhang);
                            MainActivity.gioHangArrayList.clear();
                            Intent intent = new Intent(ThongTinKhachHang.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Cảm ơn bạn đã mua hàng", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Bỏ comment và chỉnh sửa nếu cần thiết
//            if(CheckConnect.haveNetworkConnected(ThongTinKhachHang.this)){
//                if(NhapTenKhachHang.getText().toString().isEmpty() || NhapSoDienThoai.getText().toString().isEmpty() || NhapEmail.getText().toString().isEmpty()){
//                    Toast.makeText(ThongTinKhachHang.this,"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
//                }else {
//                    progressDialog.show();
//
//                    String strTen=NhapTenKhachHang.getText().toString().trim();
//                    String strSDT=NhapSoDienThoai.getText().toString().trim();
//                    String strEmail=NhapEmail.getText().toString().trim();
//
//                    RequestBody requestBodyTenKhachHang=RequestBody.create(MediaType.parse("multipart/form-data"),strTen);
//                    RequestBody requestBodySDT=RequestBody.create(MediaType.parse("multipart/form-data"),strSDT);
//                    RequestBody requestBodyEmail=RequestBody.create(MediaType.parse("multipart/form-data"),strEmail);
//
//                    CountDownTimer cd = new CountDownTimer(3000,3000) {
//                        @Override
//                        public void onTick(long l) {
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            ApiSp.apiDevice.getThongTinKhachHang(requestBodyTenKhachHang,requestBodySDT,requestBodyEmail)
//                                    .enqueue(new Callback<SignInResponse>() {
//                                        @Override
//                                        public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
//                                            if(response.body() != null){
//                                                switch (response.body().statusCode){
//                                                    case 200:
//                                                        progressDialog.dismiss();
//                                                        MainActivity.gioHangArrayList.clear();
//                                                        Intent intent = new Intent(ThongTinKhachHang.this,MainActivity.class);
//                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                        startActivity(intent);
//                                                        Toast.makeText(getApplicationContext(),"Cảm ơn bạn đã mua hàng",Toast.LENGTH_LONG).show();
//                                                        break;
//                                                    case 400:
//                                                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<SignInResponse> call, Throwable t) {
//                                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
//                    }.start();
//
//                }
//            }else{
//                Toast.makeText(getApplicationContext(),"Error connect Internet",Toast.LENGTH_LONG).show();
//            }
            }
        });
    }


    private void Actionbar() {
        setSupportActionBar(toolbarThongTinKhachHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTinKhachHang.setNavigationIcon(R.drawable.ic_action_back);
        toolbarThongTinKhachHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getDataUser() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query query = reference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Duyệt qua các kết quả trả về từ query
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey(); // Lấy ID của user là lấy cái node phía trước của user
                    user = snapshot.getValue(User.class);
                    user.setId(Integer.parseInt(userId)); //set lại id theo node nếu id ko đúng
                }
                NhapSoDienThoai.setText(user.getPhone());
                NhapTenKhachHang.setText(user.getUserName());
                NhapEmail.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Log.e("Firebase Error", "Error fetching user data", databaseError.toException());
            }
        });
    }

    private void Khaibao() {
        toolbarThongTinKhachHang = findViewById(R.id.ToolbarThongTinKhachHang);
        NhapTenKhachHang = findViewById(R.id.TenKhachHang);
        NhapSoDienThoai = findViewById(R.id.SoDienThoai);
        NhapEmail = findViewById(R.id.EmailKhachHang);
        XacNhan = findViewById(R.id.XacNhan);
        Huy = findViewById(R.id.Huy);
        txtThanhToanTien = findViewById(R.id.txtThanhToanTien);

        String nd = getIntent().getStringExtra("TotalMoney");

        txtThanhToanTien.setText("Thanh toán số tiền: " + nd);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.animation_scale_enter_left, R.anim.animation_scale_exit_right);
    }
}