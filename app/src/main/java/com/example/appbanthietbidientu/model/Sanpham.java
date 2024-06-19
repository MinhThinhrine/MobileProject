package com.example.appbanthietbidientu.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    String id;
    String tensanpham;
    String giasanpham;
    String hinhanhsanpham;
    String motasanpham;
    String idsanpham;

    public Sanpham() {
    }

    public Sanpham(String id, String tensanpham, String giasanpham, String hinhanhsanpham, String motasanpham, String idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.motasanpham = motasanpham;
        this.idsanpham = idsanpham;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(String giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    @Override
    public String toString() {
        return "Sanpham{" +
                "id='" + id + '\'' +
                ", tensanpham='" + tensanpham + '\'' +
                ", giasanpham='" + giasanpham + '\'' +
                ", hinhanhsanpham='" + hinhanhsanpham + '\'' +
                ", motasanpham='" + motasanpham + '\'' +
                ", idsanpham='" + idsanpham + '\'' +
                '}';
    }
}
