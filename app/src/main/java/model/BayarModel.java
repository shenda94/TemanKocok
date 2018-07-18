package model;

/**
 * Created by merna.shenda on 7/10/2018.
 */

public class BayarModel {
    public String id_pembayaran;
    public String tgl_pembayaran;
    public String nominal;
    public String id_group_arisan;
    public String id_periode;
    public String id_user;
    public String id_kocokan_nama;
    public String status_lunas;
    public String tgl_lunas;
    public String jenis_pembayaran;
    public String harusbayar;
    public String status_bayar;
    public String nama_file;
    public String nama_group_arisan;
    public String nama_periode;
    public String tgl_periode;
    public String nm_kocokan;
    public String nama_user;
    public String statusbayar;
    public String statuslunas;

    public BayarModel() {
    }

    public BayarModel(String id_pembayaran, String tgl_pembayaran,String nominal,String id_group_arisan,String id_periode,String id_user,String id_kocokan_nama,String status_lunas, String tgl_lunas,String jenis_pembayaran,String harusbayar,String status_bayar,String nama_file,String nama_group_arisan,String nama_periode,String tgl_periode,String nm_kocokan,String nama_user,String statusbayar,String statuslunas) {
        this.id_pembayaran = id_pembayaran;
        this.id_periode = id_periode;
        this.nominal = nominal;
        this.tgl_pembayaran = tgl_pembayaran;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.id_group_arisan = id_group_arisan;
        this.id_kocokan_nama = id_kocokan_nama;
        this.statusbayar = statusbayar;
        this.nama_group_arisan = nama_group_arisan;
        this.status_lunas = status_lunas;
        this.tgl_lunas = tgl_lunas;
        this.jenis_pembayaran = jenis_pembayaran;
        this.harusbayar = harusbayar;
        this.status_bayar = status_bayar;
        this.nama_file = nama_file;
        this.nama_periode = nama_periode;
        this.tgl_periode = tgl_periode;
        this.nm_kocokan = nm_kocokan;
        this.statuslunas = statuslunas;

    }

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public void setId_pembayaran(String id_pembayaran) {
        this.id_pembayaran = id_pembayaran;
    }

    public String getTgl_pembayaran() {
        return tgl_pembayaran;
    }

    public void setTgl_pembayaran(String tgl_pembayaran) {
        this.tgl_pembayaran = tgl_pembayaran;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getId_group_arisan() {
        return id_group_arisan;
    }

    public void setId_group_arisan(String id_group_arisan) {
        this.id_group_arisan = id_group_arisan;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kocokan_nama() {
        return id_kocokan_nama;
    }

    public void setId_kocokan_nama(String id_kocokan_nama) {
        this.id_kocokan_nama = id_kocokan_nama;
    }

    public String getStatus_lunas() {
        return status_lunas;
    }

    public void setStatus_lunas(String status_lunas) {
        this.status_lunas = status_lunas;
    }

    public String getTgl_lunas() {
        return tgl_lunas;
    }

    public void setTgl_lunas(String tgl_lunas) {
        this.tgl_lunas = tgl_lunas;
    }

    public String getJenis_pembayaran() {
        return jenis_pembayaran;
    }

    public void setJenis_pembayaran(String jenis_pembayaran) {
        this.jenis_pembayaran = jenis_pembayaran;
    }

    public String getHarusbayar() {
        return harusbayar;
    }

    public void setHarusbayar(String harusbayar) {
        this.harusbayar = harusbayar;
    }

    public String getStatus_bayar() {
        return status_bayar;
    }

    public void setStatus_bayar(String status_bayar) {
        this.status_bayar = status_bayar;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public String getNama_group_arisan() {
        return nama_group_arisan;
    }

    public void setNama_group_arisan(String nama_group_arisan) {
        this.nama_group_arisan = nama_group_arisan;
    }

    public String getNama_periode() {
        return nama_periode;
    }

    public void setNama_periode(String nama_periode) {
        this.nama_periode = nama_periode;
    }

    public String getTgl_periode() {
        return tgl_periode;
    }

    public void setTgl_periode(String tgl_periode) {
        this.tgl_periode = tgl_periode;
    }

    public String getNm_kocokan() {
        return nm_kocokan;
    }

    public void setNm_kocokan(String nm_kocokan) {
        this.nm_kocokan = nm_kocokan;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getStatusbayar() {
        return statusbayar;
    }

    public void setStatusbayar(String statusbayar) {
        this.statusbayar = statusbayar;
    }

    public String getStatuslunas() {
        return statuslunas;
    }

    public void setStatuslunas(String statuslunas) {
        this.statuslunas = statuslunas;
    }
}
