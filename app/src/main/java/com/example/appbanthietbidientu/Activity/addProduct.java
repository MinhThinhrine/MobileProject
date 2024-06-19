package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addProduct extends AppCompatActivity {
    EditText name,price,link,mota;
    RadioGroup radioGroup;
    Button bntAdd;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        anhxa();
        back();


        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameP = name.getText().toString().trim();
                String priceP = price.getText().toString().trim();
                String linkP = link.getText().toString().trim();
                String motaP = mota.getText().toString().trim();
                String checked = getCheckedRadioButtonId();

                if (nameP.isEmpty() || priceP.isEmpty() || linkP.isEmpty() || motaP.isEmpty()) {
                    Toast.makeText(addProduct.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (priceP.length()<5) {
                    Toast.makeText(addProduct.this, "Giá sản phẩm phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (nameP.length() < 6) {
                    Toast.makeText(addProduct.this, "Tên sản phẩm phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (motaP.length()<6) {
                    Toast.makeText(addProduct.this, "Mô tả sản phẩm phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (checked.isEmpty()) {
                    Toast.makeText(addProduct.this, "Vui lòng loại sản phẩm", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = firebase.getReference(checked);

                    ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Kiểm tra xem có dữ liệu trả về không
                            if (snapshot.exists()) {
                                // Lặp qua tất cả các children để lấy ID của user cuối cùng
                                String latestProId = "";
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    latestProId = child.getKey();
                                }
                                int newIdpro = Integer.parseInt(latestProId) + 1;

                                String id = getIdString(checked);
                                Sanpham sanpham = new Sanpham(String.valueOf(newIdpro),nameP,priceP,linkP,motaP,id);
                                ref.child(String.valueOf(newIdpro)).setValue(sanpham)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(addProduct.this, "Đã thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                                    name.setText("");
                                                    price.setText("");
                                                    mota.setText("");
                                                    link.setText("");
                                                } else {
                                                    Toast.makeText(addProduct.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                int newIdpro = 1;
                                String id = getIdString(checked);
                                Sanpham sanpham = new Sanpham(String.valueOf(newIdpro),nameP,priceP,linkP,motaP,id);
                                ref.child(String.valueOf(newIdpro)).setValue(sanpham)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(addProduct.this, "Đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                    name.setText("");
                                                    price.setText("");
                                                    mota.setText("");
                                                    link.setText("");
                                                    startActivity(new Intent(addProduct.this,LoginActivity.class));

                                                } else {
                                                    Toast.makeText(addProduct.this, "Lỗi khi lưu user", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(addProduct.this, "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private String getCheckedRadioButtonId() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(checkedId == (R.id.checkpro1)){
            return "dienthoai";
        } else if (checkedId == (R.id.checkpro2)) {
            return "laptop";
        } else if (checkedId == (R.id.checkpro3)) {
            return "sanphammoi";
        }
        return "";
    }

    public String getIdString (String id){
        if(id.equalsIgnoreCase("dienthoai")){
            return "1";
        } else if (id.equalsIgnoreCase("laptop")) {
            return "2";
        }else if (id.equalsIgnoreCase("sanphammoi")){
            return "3";
        }else {
            return "";
        }
    }

    private void back() {
            imgback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(addProduct.this,manageProduct.class));
                }
            });
        }


    private void anhxa() {
        imgback = findViewById(R.id.newimgBack);
        name = findViewById(R.id.namePro);
        price = findViewById(R.id.pricePro);
        link = findViewById(R.id.linkanh);
        mota = findViewById(R.id.motaPro);
        radioGroup = findViewById(R.id.radioGroupPro);
        bntAdd = findViewById(R.id.buttonAdd);
    }
}