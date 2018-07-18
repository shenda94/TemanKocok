package model;

/**
 * Created by Merna on 6/17/2018.
 */

public class PemenangModel {
    public String id_kocokan_nama;
    public String id_group_arisan;
    public String nama_group_arisan;
    public String nm_pemenang;
    public String id_user;
    public String nama_user;
    public String id_pemenang;
    public String tgl_kocok;
    public String nm_periode;

    public PemenangModel() {
    }

    public PemenangModel(String id_group_arisan, String id_kocokan_nama, String nama_group_arisan, String nm_pemenang, String id_user, String nama_user, String id_pemenang, String tgl_kocok, String nm_periode) {
        this.id_group_arisan = id_group_arisan;
        this.id_kocokan_nama = id_kocokan_nama;
        this.nama_group_arisan = nama_group_arisan;
        this.nm_pemenang = nm_pemenang;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.id_pemenang = id_pemenang;
        this.tgl_kocok = tgl_kocok;
        this.nm_periode = nm_periode;
    }

    public String getId_kocokan_nama() {
        return id_kocokan_nama;
    }

    public void setId_kocokan_nama(String id_kocokan_nama) {
        this.id_kocokan_nama = id_kocokan_nama;
    }

    public String getId_group_arisan() {
        return id_group_arisan;
    }

    public void setId_group_arisan(String id_group_arisan) {
        this.id_group_arisan = id_group_arisan;
    }

    public String getNama_group_arisan() {
        return nama_group_arisan;
    }

    public void setNama_group_arisan(String nama_group_arisan) {
        this.nama_group_arisan = nama_group_arisan;
    }

    public String getNm_pemenang() {
        return nm_pemenang;
    }

    public void setNm_pemenang(String nm_pemenang) {
        this.nm_pemenang = nm_pemenang;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getId_pemenang() {
        return id_pemenang;
    }

    public void setId_pemenang(String id_pemenang) {
        this.id_pemenang = id_pemenang;
    }

    public String getTgl_kocok() {
        return tgl_kocok;
    }

    public void setTgl_kocok(String tgl_kocok) {
        this.tgl_kocok = tgl_kocok;
    }

    public String getNm_periode() {
        return nm_periode;
    }

    public void setNm_periode(String nm_periode) {
        this.nm_periode = nm_periode;
    }
}
