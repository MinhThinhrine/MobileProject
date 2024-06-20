package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class updateProduct extends AppCompatActivity {
    String data;
    TextView txtIdSp, typePro;
    EditText edtNa, edtPri, edtMo, edtLi;
    Button btnEd;
    Sanpham sanpham;
    ImageView imageView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        anhxa();
        back();
        editProduct();
    }

    private void editProduct() {
        btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtNa.getText().toString().trim();
                String price = edtPri.getText().toString().trim();
                String mota = edtMo.getText().toString().trim();
                String link = edtLi.getText().toString().trim();

                if (name.isEmpty() || price.isEmpty() || mota.isEmpty() || link.isEmpty()) {
                    Toast.makeText(updateProduct.this, "Vui lòng nhập đủ các thông tin", Toast.LENGTH_SHORT).show();
                } else if (name.length() < 6 || price.length() < 5 || mota.length() < 6) {
                    Toast.makeText(updateProduct.this, "Vui lòng nhập chính xác các thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("tensanpham", name);
                    updates.put("giasanpham", price);
                    updates.put("motasanpham", mota);
                    updates.put("hinhanhsanpham", link);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference(data).child(sanpham.getId());

                    reference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(updateProduct.this, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                                edtNa.setText("");
                                edtPri.setText("");
                                edtLi.setText("");
                                edtMo.setText("");
                            } else {
                                Toast.makeText(updateProduct.this, "Lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void back() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhxa() {
        imageView = findViewById(R.id.backUD);
        txtIdSp = findViewById(R.id.txtIdSp);
        typePro = findViewById(R.id.typePro);

        edtNa = findViewById(R.id.edtNa);
        edtPri = findViewById(R.id.edtPri);
        edtMo = findViewById(R.id.edtMo);
        edtLi = findViewById(R.id.edtLi);
        btnEd = findViewById(R.id.btnEd);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "bundle bị null", Toast.LENGTH_SHORT).show();
        }
        sanpham = (Sanpham) bundle.get("object_sp");
        sharedPreferences = getSharedPreferences("dataProduct", Context.MODE_PRIVATE);
        data = sharedPreferences.getString("sanpham", "");

        txtIdSp.setText("Id: " + sanpham.getId());
        typePro.setText("Loại sản phẩm: " + typepro(data));

        edtNa.setText(sanpham.getTensanpham());
        edtPri.setText(sanpham.getGiasanpham());
        edtLi.setText(sanpham.getHinhanhsanpham());
        edtMo.setText(sanpham.getMotasanpham());
    }

    public String typepro(String data) {
        if (data.equalsIgnoreCase("sanphammoi")) {
            return "Sản phẩm mới";
        } else if (data.equalsIgnoreCase("dienthoai")) {
            return "Điện thoại";
        } else if (data.equalsIgnoreCase("laptop")) {
            return "Laptop";
        }
        return "";
    }
}
