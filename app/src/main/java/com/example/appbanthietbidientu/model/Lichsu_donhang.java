package com.example.appbanthietbidientu.model;

public class Lichsu_donhang {
        private String maDonHang;
        private String maSanPham;
        private String maKhachHang;
        private String tenKhachHang;
        private String ngayDatHang;
        private double tongTien;
        private String trangThaiDonHang;
        private String diaChiGiaoHang;

        public Lichsu_donhang(String maDonHang, String maSanPham, String maKhachHang, String tenKhachHang, String ngayDatHang, double tongTien, String trangThaiDonHang, String diaChiGiaoHang) {
            this.maDonHang = maDonHang;
            this.maSanPham = maSanPham;
            this.maKhachHang = maKhachHang;
            this.tenKhachHang = tenKhachHang;
            this.ngayDatHang = ngayDatHang;
            this.tongTien = tongTien;
            this.trangThaiDonHang = trangThaiDonHang;
            this.diaChiGiaoHang = diaChiGiaoHang;
        }

        // Getters và Setters
        public String getMaDonHang() {
            return maDonHang;
        }

        public void setMaDonHang(String maDonHang) {
            this.maDonHang = maDonHang;
        }

        public String getMaSanPham() {
            return maSanPham;
        }

        public void setMaSanPham(String maSanPham) {
            this.maSanPham = maSanPham;
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

        public double getTongTien() {
            return tongTien;
        }

        public void setTongTien(double tongTien) {
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
            return "LichSuDonHang{" +
                    "maDonHang='" + maDonHang + '\'' +
                    ", maSanPham='" + maSanPham + '\'' +
                    ", maKhachHang='" + maKhachHang + '\'' +
                    ", tenKhachHang='" + tenKhachHang + '\'' +
                    ", ngayDatHang='" + ngayDatHang + '\'' +
                    ", tongTien=" + tongTien +
                    ", trangThaiDonHang='" + trangThaiDonHang + '\'' +
                    ", diaChiGiaoHang='" + diaChiGiaoHang + '\'' +
                    '}';
        }
    }
