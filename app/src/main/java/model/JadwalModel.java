package model;

/**
 * Created by merna.shenda on 7/9/2018.
 */

public class JadwalModel {
    public String id_jadwal;
    public String id_periode;
    public String nominal;
    public String tgl_bayar;
    public String id_user;
    public String id_group_arisan;
    public String id_user_anggota;
    public String statusbayar;
    public String nama_user;
    public String nama_group_arisan;

    public JadwalModel() {
    }

    public JadwalModel(String id_jadwal, String id_periode, String nominal, String tgl_bayar, String id_user, String id_group_arisan, String id_user_anggota, String statusbayar, String nama_user, String nama_group_arisan) {
        this.id_jadwal = id_jadwal;
        this.id_periode = id_periode;
        this.nominal = nominal;
        this.tgl_bayar = tgl_bayar;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.id_group_arisan = id_group_arisan;
        this.id_user_anggota = id_user_anggota;
        this.statusbayar = statusbayar;
        this.nama_group_arisan = nama_group_arisan;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTgl_bayar() {
        return tgl_bayar;
    }

    public void setTgl_bayar(String tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    public String getid_user() {
        return id_user;
    }

    public void setid_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_group_arisan() {
        return id_group_arisan;
    }

    public void setId_group_arisan(String id_group_arisan) {
        this.id_group_arisan = id_group_arisan;
    }

    public String getId_user_anggota() {
        return id_user_anggota;
    }

    public void setId_user_anggota(String id_user_anggota) {
        this.id_user_anggota = id_user_anggota;
    }

    public String getStatusbayar() {
        return statusbayar;
    }

    public void setStatusbayar(String statusbayar) {
        this.statusbayar = statusbayar;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_group_arisan() {
        return nama_group_arisan;
    }

    public void setNama_group_arisan(String nama_group_arisan) {
        this.nama_group_arisan = nama_group_arisan;
    }
}
