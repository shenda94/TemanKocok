package model;

/**
 * Created by merna.shenda on 7/9/2018.
 */

public class PilihKocokanModel {
    public String id_group_arisan;
    public String nama_kocokan;
    public String id_kocokannama;
    public String id_user;

    public PilihKocokanModel() {
    }

    public PilihKocokanModel(String id_user, String id_group_arisan, String id_kocokannama, String nama_kocokan) {
        this.id_group_arisan = id_group_arisan;
        this.nama_kocokan = nama_kocokan;
        this.id_kocokannama = id_kocokannama;
        this.id_user = id_user;
    }

    public String getId_group_arisan() {
        return id_group_arisan;
    }

    public void setId_group_arisan(String id_group_arisan) {
        this.id_group_arisan = id_group_arisan;
    }

    public String getNama_kocokan() {
        return nama_kocokan;
    }

    public void setNama_kocokan(String nama_kocokan) {
        this.nama_kocokan = nama_kocokan;
    }

    public String getId_kocokannama() {
        return id_kocokannama;
    }

    public void setId_kocokannama(String id_kocokannama) {
        this.id_kocokannama = id_kocokannama;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
