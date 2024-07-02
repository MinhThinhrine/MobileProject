package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.Adapter.SanphamAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.ultil.BaseFunctionActivity;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DienThoaiActivity extends BaseFunctionActivity {
    SearchView searchView;
    Toolbar toolbarDienThoai;
    ProgressBar loadDienThoai;
    ListView listViewDienThoai;
    ArrayList<Sanpham> dienThoaiArrayList;
    SanphamAdapter dienThoaiAdapter;
    View footerView;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
//    mHander mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            Khaibao();
            ActionBar();
            getData();
//            LoadmoreData();
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Error Connect Internet");
        }

        listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",dienThoaiArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        loadDienThoai.setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("dienthoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dienThoaiArrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Sanpham sanpham = dataSnapshot.getValue(Sanpham.class);
                        dienThoaiArrayList.add(sanpham);
                    }
                    dienThoaiAdapter = new SanphamAdapter(dienThoaiArrayList, getApplicationContext());
                    listViewDienThoai.setAdapter(dienThoaiAdapter);
                    loadDienThoai.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(DienThoaiActivity.this, "Không tồn tại sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error: "+ error.getMessage(),Toast.LENGTH_SHORT).show();
                loadDienThoai.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void ActionBar() {
        setSupportActionBar(toolbarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDienThoai.setNavigationIcon(R.drawable.ic_action_back);
        toolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        getMenuInflater().inflate(R.menu.search,menu);
        searchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @SuppressLint("Range")
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
                searchView.setQuery(cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)),false);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dienThoaiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dienThoaiAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
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
        loadDienThoai = findViewById(R.id.loadDienThoai);
        toolbarDienThoai = findViewById(R.id.ToolbarDienThoai);
        listViewDienThoai = findViewById(R.id.listviewDienThoai);

        dienThoaiArrayList = new ArrayList<>();
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.loading_layout,null);
//        mHander=new mHander();
    }
}