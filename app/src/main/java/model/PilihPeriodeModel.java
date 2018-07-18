package model;

/**
 * Created by merna.shenda on 7/5/2018.
 */

public class PilihPeriodeModel {
    public String id_periode;
    public String nama_periode;
    public String status_selesai;;
    public String tgl_periode;
    public String id_group_arisan;
    public String nominal;
    public String tgl_jadwalkocok;

    public PilihPeriodeModel() {
    }

    public PilihPeriodeModel(String id_periode, String nama_periode, String status_selesai, String tgl_periode, String id_group_arisan, String nominal, String tgl_jadwalkocok) {
        this.id_group_arisan = id_group_arisan;
        this.id_periode = id_periode;
        this.nama_periode = nama_periode;
        this.status_selesai = status_selesai;
        this.tgl_periode = tgl_periode;
        this.nominal = nominal;
        this.tgl_jadwalkocok = tgl_jadwalkocok;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getNama_periode() {
        return nama_periode;
    }

    public void setNama_periode(String nama_periode) {
        this.nama_periode = nama_periode;
    }

    public String getStatus_selesai() {
        return status_selesai;
    }

    public void setStatus_selesai(String status_selesai) {
        this.status_selesai = status_selesai;
    }

    public String getTgl_periode() {
        return tgl_periode;
    }

    public void setTgl_periode(String tgl_periode) {
        this.tgl_periode = tgl_periode;
    }

    public String getId_group_arisan() {
        return id_group_arisan;
    }

    public void setId_group_arisan(String id_group_arisan) {
        this.id_group_arisan = id_group_arisan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTgl_jadwalkocok() {
        return tgl_jadwalkocok;
    }

    public void setTgl_jadwalkocok(String tgl_jadwalkocok) {
        this.tgl_jadwalkocok = tgl_jadwalkocok;
    }
}
