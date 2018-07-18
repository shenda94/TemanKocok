package model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.merna.temankocok.AppVar;
import com.merna.temankocok.PembayaranIuranActivity;
import com.merna.temankocok.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by merna.shenda on 7/10/2018.
 */

public class BayarAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<BayarModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMorePembayaran onLoadMoreListener;

    public BayarAdapter(List<BayarModel> modeldata, RecyclerView recyclerView) {
        modeldataList = modeldata;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (modeldataList.size() <= 10) {
                                    loading = false;
                                }
                                else {
                                    if (onLoadMoreListener != null) {
                                        onLoadMoreListener.onLoadMorePembayaran();
                                    }
                                    loading = true;
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return modeldataList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_bayar, parent, false);

            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BayarAdapter.StudentViewHolder) {
            BayarModel singleStudent= (BayarModel) modeldataList.get(position);
            String dariString = String.valueOf(singleStudent.getNominal());

            try {
                //dari
                //((StudentViewHolder) holder).bayar_jad1.setTextColor(((BayarAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                //((BayarAdapter.StudentViewHolder) holder).bayar_jad1.setTypeface(Typeface.DEFAULT);
                ((BayarAdapter.StudentViewHolder) holder).bayar_jad1.setText("Bayar Rp. " + dariString);

                //((StudentViewHolder) holder).nominal.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                //((BayarAdapter.StudentViewHolder) holder).nominal.setTypeface(Typeface.DEFAULT);
                ((BayarAdapter.StudentViewHolder) holder).harus_jad1.setText("Iuran Rp. " + String.valueOf(singleStudent.getHarusbayar()));
                ((StudentViewHolder) holder).tglbayar_jad1.setText("Tgl Bayar : " + singleStudent.getTgl_pembayaran());
                ((StudentViewHolder) holder).statusbayar_jad1.setText("Status : " + singleStudent.getStatusbayar());
                ((StudentViewHolder) holder).nmkocokan_jad1.setText(singleStudent.getNm_kocokan());
                ((StudentViewHolder) holder).jenis_jad1.setText(singleStudent.getJenis_pembayaran());
                ((StudentViewHolder) holder).pemilik1.setText(singleStudent.getNama_user());
                ((StudentViewHolder) holder).periode_byr1.setText(singleStudent.getNama_periode());
                ((StudentViewHolder) holder).lunas1.setText(singleStudent.getStatuslunas());

                //((StudentViewHolder) holder).nmgroup.setTextColor(((PeriodeAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((BayarAdapter.StudentViewHolder) holder).nama_group_arisan.setTypeface(Typeface.DEFAULT);
                ((BayarAdapter.StudentViewHolder) holder).nama_group_arisan.setText("Group : " + String.valueOf(singleStudent.getNama_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((BayarAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            ((BayarAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((BayarAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMorePembayaran onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView bayar_jad1, harus_jad1, jenis_jad1, lunas1, nmkocokan_jad1, pemilik1, periode_byr1, tglbayar_jad1, statusbayar_jad1, nama_group_arisan;
        public BayarModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private ProgressDialog pDialog;
        String urll;
        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            pDialog = new ProgressDialog(contextx, R.style.ProgressBar);
            bayar_jad1 = (TextView) v.findViewById(R.id.bayar_jad);
            nama_group_arisan = (TextView) v.findViewById(R.id.icon_star_jad);
            harus_jad1 = (TextView) v.findViewById(R.id.harus_jad);
            jenis_jad1 = (TextView) v.findViewById(R.id.jenis_jad);
            lunas1 = (TextView) v.findViewById(R.id.lunas);
            nmkocokan_jad1 = (TextView) v.findViewById(R.id.nmkocokan_jad);
            pemilik1 = (TextView) v.findViewById(R.id.pemilik);
            periode_byr1 = (TextView) v.findViewById(R.id.periode_byr);
            tglbayar_jad1 = (TextView) v.findViewById(R.id.tglbayar_jad);
            statusbayar_jad1 = (TextView) v.findViewById(R.id.statusbayar_jad);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_jad);
            navButton4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    custom_dialog1();
                }
            });

        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        public void custom_dialog1() {
            dialog = new AlertDialog.Builder(contextx);
            inflater = LayoutInflater.from(contextx);
            dialogView = inflater.inflate(R.layout.custom_dialog2, null);
            dialog.setView(dialogView);
            dialog.setCancelable(false);
            //dialog.setIcon(R.drawable.ic_shapes);
            dialog.setTitle("Action");

            final AlertDialog alertDialog = dialog.create();
            Button btnscan = (Button) dialogView.findViewById(R.id.btn_dialog1);
            Button btnscan1 = (Button) dialogView.findViewById(R.id.btn_dialog2);
            Button btnscan2 = (Button) dialogView.findViewById(R.id.btn_dialog);

            btnscan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //simpan_update();
                    if (modeldata.getStatus_bayar().equals("2")){
                        final SweetAlertDialog pDialog = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Informasi");
                        pDialog.setContentText("Pembayaran Sudah Diterima. Anda Tidak Bisa Edit Kembali!");
                        pDialog.setCancelable(false);
                        pDialog.setConfirmText("OK");
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.hide();
                            }
                        });
                        pDialog.show();
                    }
                    else {
                        //logout();
                        SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PEMBAYARAN, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putString(AppVar.ID_PEMBAYARAN, modeldata.getId_pembayaran());
                        editor.putString(AppVar.namakocokant, modeldata.getNm_kocokan());
                        editor.putString(AppVar.PBidkocokan, modeldata.getId_kocokan_nama());
                        editor.putString(AppVar.TGLBAYAR, modeldata.getTgl_pembayaran());
                        editor.putString(AppVar.HARUSBAYAR, modeldata.getHarusbayar());
                        editor.putString(AppVar.BAYAR, modeldata.getNominal());
                        editor.putString(AppVar.jenisBAYAR, modeldata.getJenis_pembayaran());
                        editor.commit();

                        Intent intent1 = new Intent(contextx, PembayaranIuranActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id_kocokan", modeldata.getId_kocokan_nama());
                        bundle1.putString("namakocokan", modeldata.getNm_kocokan());
                        bundle1.putString("nilai1",modeldata.getHarusbayar());
                        intent1.putExtras(bundle1);
                        contextx.startActivity(intent1);

                        //Intent intent = new Intent(contextx, PembayaranIuranActivity.class);
                        //contextx.startActivity(intent);
                    }

                    alertDialog.dismiss();
                }
            });

            btnscan1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkuser1();
                    alertDialog.dismiss();
                }
            });

            btnscan2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            /*dialog.setPositiveButton("Edit Pembayaran", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //simpan_update();
                    if (modeldata.getStatus_bayar().equals("2")){
                        final SweetAlertDialog pDialog = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Informasi");
                        pDialog.setContentText("Pembayaran Sudah Diterima. Anda Tidak Bisa Edit Kembali!");
                        pDialog.setCancelable(false);
                        pDialog.setConfirmText("OK");
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.hide();
                            }
                        });
                        pDialog.show();
                    }
                    else {
                        //logout();
                        SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PEMBAYARAN, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putString(AppVar.ID_PEMBAYARAN, modeldata.getId_pembayaran());
                        editor.putString(AppVar.namakocokant, modeldata.getNm_kocokan());
                        editor.putString(AppVar.PBidkocokan, modeldata.getId_kocokan_nama());
                        editor.putString(AppVar.TGLBAYAR, modeldata.getTgl_pembayaran());
                        editor.putString(AppVar.HARUSBAYAR, modeldata.getHarusbayar());
                        editor.putString(AppVar.BAYAR, modeldata.getNominal());
                        editor.putString(AppVar.jenisBAYAR, modeldata.getJenis_pembayaran());
                        editor.commit();

                        Intent intent1 = new Intent(contextx, PembayaranIuranActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id_kocokan", modeldata.getId_kocokan_nama());
                        bundle1.putString("namakocokan", modeldata.getNm_kocokan());
                        bundle1.putString("nilai1",modeldata.getHarusbayar());
                        intent1.putExtras(bundle1);
                        contextx.startActivity(intent1);

                        //Intent intent = new Intent(contextx, PembayaranIuranActivity.class);
                        //contextx.startActivity(intent);
                    }
                    dialog.dismiss();
                }
            });*/

            /*dialog.setNegativeButton("Approve Pembayaran", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkuser1();
                    dialog.dismiss();
                }
            });*/

            /*dialog.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });*/

            //dialog.show();
            //AlertDialog alertDialog = dialog.create();
            alertDialog.show();

            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            //nbutton.setBackgroundColor(Color.YELLOW);
            nbutton.setTextColor(Color.BLACK);
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            //pbutton.setBackgroundColor(Color.YELLOW);
            pbutton.setTextColor(Color.BLACK);
        }

        public void checkuser1() {
            SharedPreferences sharedPreferences1 = contextx.getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String iduser = sharedPreferences1.getString(AppVar.IDUSER_SHARED_PREF, "");

            urll = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/checkpermisionarisan?id_user="+iduser+"&id_group_arisan="+modeldata.getId_group_arisan();
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Checking...");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, urll,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server
                            //Toast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                            //cal json
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String status_anggota = jObj.getString("status_anggota");
                                //success = jObj.getInt(TAG_SUCCESS);

                                //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                                // Cek error node pada json
                                if (status_anggota.equals("1")) {
                                    //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    approvebayar();
                                    //then hapus
                                } else {
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Anda Tidak Memiliki Ijin. Anda Bukan Ketua Group Arisan ini");
                                    pDialog1.setCancelable(false);
                                    pDialog1.setConfirmText("OK");
                                    pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                            pDialog1.hide();
                                            //finish();
                                        }
                                    });
                                    pDialog1.show();
                                    //Displaying an error message on toast
                                    //Toast.makeText(context, "Register Gagal", Toast.LENGTH_LONG).show();
                                    //Toast.makeText(MainActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                // JSON error
                                Toast.makeText(contextx, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                                hideDialog();
                                //e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                            hideDialog();
                            Toast.makeText(contextx, "The server unreachable", Toast.LENGTH_LONG).show();
                        }
                    });

            Volley.newRequestQueue(contextx).add(stringRequest);
        }

        public void approvebayar() {
            urll = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/approvebayar?Key_id_bayar="+modeldata.getId_pembayaran()+"&Key_idkocok_byr="+modeldata.getId_kocokan_nama();
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Proses Approve...");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, urll,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server
                            //Toast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                            //cal json
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String status_anggota = jObj.getString("success");
                                //success = jObj.getInt(TAG_SUCCESS);

                                //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                                // Cek error node pada json
                                if (status_anggota.equals("1")) {
                                    //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Approve Pembayaran Berhasil");
                                    pDialog1.setCancelable(false);
                                    pDialog1.setConfirmText("OK");
                                    pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                            pDialog1.hide();
                                            //((Activity)contextx).finish();
                                            //finish();
                                        }
                                    });
                                    pDialog1.show();

                                    //then hapus
                                }
                                else if (status_anggota.equals("2")) {
                                    //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Pembayaran Iuran Masih Kurang.");
                                    pDialog1.setCancelable(false);
                                    pDialog1.setConfirmText("OK");
                                    pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                            pDialog1.hide();
                                            //((Activity)contextx).finish();
                                            //finish();
                                        }
                                    });
                                    pDialog1.show();

                                    //then hapus
                                }
                                else {
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Approve Gagal");
                                    pDialog1.setCancelable(false);
                                    pDialog1.setConfirmText("OK");
                                    pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                            pDialog1.hide();

                                            //finish();
                                        }
                                    });
                                    pDialog1.show();
                                    //Displaying an error message on toast
                                    //Toast.makeText(context, "Register Gagal", Toast.LENGTH_LONG).show();
                                    //Toast.makeText(MainActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                // JSON error
                                Toast.makeText(contextx, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                                hideDialog();
                                //e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                            hideDialog();
                            Toast.makeText(contextx, "The server unreachable", Toast.LENGTH_LONG).show();
                        }
                    });

            Volley.newRequestQueue(contextx).add(stringRequest);
        }

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

}
