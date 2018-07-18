package com.merna.temankocok.model;

/**
 * Created by Merna on 12/25/2017.
 */

public class ModelData {
    private int id_anggota;
    private String nm_kocokan;
    private String tgl_daftar;
    private String nm_lengkap;

    public ModelData() {

    }
    public ModelData(int id_anggota, String nm_kocokan, String tgl_daftar, String nm_lengkap) {
        this.id_anggota = id_anggota;
        this.nm_kocokan = nm_kocokan;
        this.tgl_daftar = tgl_daftar;
        this.nm_lengkap = nm_lengkap;
    }

    public String getNm_lengkap() {
        return nm_lengkap;
    }

    public void setNm_lengkap(String nm_lengkap) {
        this.nm_lengkap = nm_lengkap;
    }

    public int getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(int id_anggota) {
        this.id_anggota = id_anggota;
    }

    public String getNm_kocokan() {
        return nm_kocokan;
    }

    public void setNm_kocokan(String nm_kocokan) {
        this.nm_kocokan = nm_kocokan;
    }

    public String getTgl_daftar() {
        return tgl_daftar;
    }

    public void setTgl_daftar(String tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }
}
