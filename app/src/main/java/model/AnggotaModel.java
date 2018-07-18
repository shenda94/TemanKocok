package model;

/**
 * Created by Merna on 6/17/2018.
 */

public class AnggotaModel {
    public String id_group_arisan;
    public String nama_group_arisan;
    public String id_anggota;
    public String id_user;
    public String nama_user;
    public String statusanggota;

    public AnggotaModel() {
    }

    public AnggotaModel(String id_group_arisan, String nama_group_arisan, String id_anggota, String id_user, String nama_user, String statusanggota) {
        this.id_group_arisan = id_group_arisan;
        this.nama_group_arisan = nama_group_arisan;
        this.id_anggota = id_anggota;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.statusanggota = statusanggota;
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

    public String getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(String id_anggota) {
        this.id_anggota = id_anggota;
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

    public String getStatusanggota() {
        return statusanggota;
    }

    public void setStatusanggota(String statusanggota) {
        this.statusanggota = statusanggota;
    }
}
