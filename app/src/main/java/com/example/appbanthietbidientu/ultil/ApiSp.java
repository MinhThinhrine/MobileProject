package com.example.appbanthietbidientu.ultil;

import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.response.SignInResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiSp {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    //https://firebasestorage.googleapis.com/v0/b/realtime-64f58.appspot.com/o/sanphammoinhat.json?alt=media&token=04505275-acd8-47cb-9bdb-5885d1fbaeff
    //https://firebasestorage.googleapis.com/v0/b/realtime-64f58.appspot.com/o/dienthoai.json?alt=media&token=d14eb726-c131-4860-a3e4-266d0aa206ed
    //https://firebasestorage.googleapis.com/v0/b/realtime-64f58.appspot.com/o/laptop.json?alt=media&token=4452ff5b-1980-4626-b646-5fa4c03159d0

//    ApiSp apiLogin = new Retrofit.Builder()
////            .baseUrl("https://firebasestorage.googleapis.com/v0/b/realtime-64f58.appspot.com/o/")
//            .baseUrl("http://192.168.1.10/hocadr/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(ApiSp.class);
    ApiSp apiDevice = new Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/realtime-64f58.appspot.com/o/")
//            .baseUrl("http://192.168.1.246/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiSp.class);
//
//    @Multipart
//    @POST("register.php")
//    Call<SignInResponse> confirmRegister(@Part(Const.KEY_EMAIL) RequestBody email,
//                                         @Part(Const.KEY_PASSWORD) RequestBody password);
//
//    @Multipart
//    @POST("login.php")
//    Call<SignInResponse> confirmLogin(@Part(Const.KEY_EMAIL) RequestBody email,
//                                         @Part(Const.KEY_PASSWORD) RequestBody password);
//
//    @GET("sanphammoinhat.json")
//    Call<List<Sanpham>> getListsp(@Query("alt") String alt, @Query("token") String token);
//
//    @GET("dienthoai.json")
//    Call<List<Sanpham>> getlistDienThoai(@Query("alt") String alt, @Query("token") String token);
//
//    @GET("laptop.json")
//    Call<List<Sanpham>> getlistLapTop(@Query("alt") String alt, @Query("token") String token);

    @Multipart
    @POST("thongtinkhachhang.php")
    Call<SignInResponse> getThongTinKhachHang(@Part(Const.KEY_USERNAME) RequestBody tenkhachhang,
                                          @Part(Const.KEY_SDT) RequestBody sodienthoai,
                                          @Part(Const.KEY_EMAIL) RequestBody email);
}
