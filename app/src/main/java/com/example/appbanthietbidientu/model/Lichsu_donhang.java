package com.example.appbanthietbidientu.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Lichsu_donhang implements Serializable {
         String maDonHang;
         ArrayList<GioHang> gioHangArrayList;
         String maKhachHang;
         String tenKhachHang;
         String ngayDatHang;
         String tongTien;
         String trangThaiDonHang;
         String diaChiGiaoHang;

    public Lichsu_donhang() {
    }

    public Lichsu_donhang(String maDonHang, ArrayList<GioHang> gioHangArrayList, String maKhachHang, String tenKhachHang, String ngayDatHang, String tongTien, String trangThaiDonHang, String diaChiGiaoHang) {
        this.maDonHang = maDonHang;
        this.gioHangArrayList = gioHangArrayList;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.ngayDatHang = ngayDatHang;
        this.tongTien = tongTien;
        this.trangThaiDonHang = trangThaiDonHang;
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public ArrayList<GioHang> getGioHangArrayList() {
        return gioHangArrayList;
    }

    public void setGioHangArrayList(ArrayList<GioHang> gioHangArrayList) {
        this.gioHangArrayList = gioHangArrayList;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThaiDonHang() {
        return trangThaiDonHang;
    }

    public void setTrangThaiDonHang(String trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    @Override
    public String toString() {
        return "Lichsu_donhang{" +
                "maDonHang='" + maDonHang + '\'' +
                ", gioHangArrayList=" + gioHangArrayList +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", ngayDatHang='" + ngayDatHang + '\'' +
                ", tongTien=" + tongTien +
                ", trangThaiDonHang='" + trangThaiDonHang + '\'' +
                ", diaChiGiaoHang='" + diaChiGiaoHang + '\'' +
                '}';
    }
}
