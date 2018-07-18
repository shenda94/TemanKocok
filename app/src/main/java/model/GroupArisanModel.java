package model;

/**
 * Created by Merna on 6/14/2018.
 */

public class GroupArisanModel {
    public String id_group_arisan;
    public String nama_group_arisan;
    public String kode_barcode;
    public String id_user;

    public GroupArisanModel() {
    }

    public GroupArisanModel(String id_group_arisan, String nama_group_arisan, String kode_barcode, String id_user) {
        this.id_group_arisan = id_group_arisan;
        this.nama_group_arisan = nama_group_arisan;
        this.kode_barcode = kode_barcode;
        this.id_user = id_user;
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

    public String getKode_barcode() {
        return kode_barcode;
    }

    public void setKode_barcode(String kode_barcode) {
        this.kode_barcode = kode_barcode;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
