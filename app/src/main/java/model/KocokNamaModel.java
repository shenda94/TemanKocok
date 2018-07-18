package model;

/**
 * Created by Merna on 6/18/2018.
 */

public class KocokNamaModel {
    public String id_kocokan_nama;
    public String id_group_arisan;
    public String nama_group_arisan;
    public String nm_kocokan;
    public String id_user;
    public String nama_user;
    public String statussleesai;

    public KocokNamaModel() {
    }

    public KocokNamaModel(String id_group_arisan, String id_kocokan_nama, String nama_group_arisan, String nm_kocokan, String id_user, String nama_user, String statussleesai) {
        this.id_group_arisan = id_group_arisan;
        this.id_kocokan_nama = id_kocokan_nama;
        this.nama_group_arisan = nama_group_arisan;
        this.nm_kocokan = nm_kocokan;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.statussleesai = statussleesai;
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

    public String getNm_kocokan() {
        return nm_kocokan;
    }

    public void setNm_kocokan(String nm_kocokan) {
        this.nm_kocokan = nm_kocokan;
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

    public String getStatussleesai() {
        return statussleesai;
    }

    public void setStatussleesai(String statussleesai) {
        this.statussleesai = statussleesai;
    }
}
