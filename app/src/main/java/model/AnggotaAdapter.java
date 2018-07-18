package model;

/**
 * Created by Merna on 6/17/2018.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.merna.temankocok.AppVar;
import com.merna.temankocok.CircleTransform;
//import com.merna.temankocok.DaftarAnggota;
import com.merna.temankocok.LoginActivity;
import com.merna.temankocok.Main2Activity;
import com.merna.temankocok.PilihPeriodeActivity;
import com.merna.temankocok.QrCodeActivity;
import com.merna.temankocok.R;
import com.merna.temankocok.ResetActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import customfonts.MyTextView;
import static com.merna.temankocok.R.color.message;

public class AnggotaAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<AnggotaModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreAnggota onLoadMoreListener;

    public AnggotaAdapter(List<AnggotaModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMoreAnggota();
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
                    R.layout.layout_anggota, parent, false);

            vh = new AnggotaAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new AnggotaAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AnggotaAdapter.StudentViewHolder) {
            AnggotaModel singleStudent= (AnggotaModel) modeldataList.get(position);
            ((AnggotaAdapter.StudentViewHolder) holder).id_surat1.setText(singleStudent.getId_group_arisan());
            ((AnggotaAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getId_anggota()));
            ((StudentViewHolder) holder).id_user.setText(String.valueOf(singleStudent.getId_user()));
            String dariString = String.valueOf(singleStudent.getNama_user());

            try {
                //dari
                ((AnggotaAdapter.StudentViewHolder) holder).dari.setTextColor(((AnggotaAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((AnggotaAdapter.StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((AnggotaAdapter.StudentViewHolder) holder).dari.setText(dariString);

                ((AnggotaAdapter.StudentViewHolder) holder).icon_star1.setTextColor(((AnggotaAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((AnggotaAdapter.StudentViewHolder) holder).icon_star1.setTypeface(Typeface.DEFAULT);
                ((AnggotaAdapter.StudentViewHolder) holder).icon_star1.setText("Group : " + String.valueOf(singleStudent.getNama_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((AnggotaAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((AnggotaAdapter.StudentViewHolder) holder).contextx).load(R.drawable.ic_approve)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((AnggotaAdapter.StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((AnggotaAdapter.StudentViewHolder) holder).imgProfile);
            ((AnggotaAdapter.StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((AnggotaAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((AnggotaAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreAnggota onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, icon_star1, id_user;
        public AnggotaModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private String URL_UPDATE = "http://esurat.perumnas.co.id/esuratwebservice/updatesurat.php?";
        private ProgressDialog pDialog;
        String urll;
        String IDANGGOTA;

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            pDialog = new ProgressDialog(contextx, R.style.ProgressBar);
            dari = (TextView) v.findViewById(R.id.from_a);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile_a);
            id_penerima1 = (TextView) v.findViewById(R.id.id_anggota_a);
            icon_star1 = (TextView) v.findViewById(R.id.icon_star_a);
            id_user = (TextView) v.findViewById(R.id.id_user_aa);
            id_surat1 = (TextView) v.findViewById(R.id.id_group_arisan_a);
            img1 = (ImageView) v.findViewById(R.id.icon_profile_hapuz);
            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_a);

            imgProfile.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkuser1();
                }
            });

            img1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    IDANGGOTA = modeldata.getId_anggota();
                    checkuser();

                    /*SharedPreferences sharedPreferences2 = contextx.getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);
                    String hasil = sharedPreferences2.getString(AppVar.MENU_SHARED_PREF3,"");
                    //send all parameter to global variable, that will show to another activity
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_BARCODE3, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putString(AppVar.ID_GROUP_SHARED_PREF3, modeldata.getId_group_arisan());
                    editor.commit();

                    //Toast.makeText(contextx, modeldata.getStatus_baca(), Toast.LENGTH_LONG).show();

                    //update unread status to be read
                    //so let's begin
                    //we need using method for call web service. then update it
                    String hasil1 = "1";
                    String hasil2 = "2";
                    String hasil3 = "3";

                    if (hasil.equals(hasil3)){
                        Intent intent = new Intent(contextx, ResetActivity.class);
                        contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil1)){
                        Intent intent = new Intent(contextx, ResetActivity.class);
                        contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil2)){
                        Intent intent = new Intent(contextx, DaftarAnggota.class);
                        contextx.startActivity(intent);
                    }*/



                    //contextx.finish();
                }
            });

        }

        public void checkuser() {
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
                                    hapusanggota();
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
                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PILIHUSRPERIODE, Context.MODE_PRIVATE);
                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    //Adding values to editor
                                    editor.putString(AppVar.ID_userPM_SHARED, modeldata.getId_user());
                                    editor.commit();

                                    Intent intent = new Intent(contextx, PilihPeriodeActivity.class);
                                    contextx.startActivity(intent);

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

        public void hapusanggota() {
            urll = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/hapusanggota?IDANGGOTA="+modeldata.getId_anggota();
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
                                String status_anggota = jObj.getString("success");
                                //success = jObj.getInt(TAG_SUCCESS);

                                //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                                // Cek error node pada json
                                if (status_anggota.equals("1")) {
                                    //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Hapus Anggota Berhasil");
                                    pDialog1.setCancelable(false);
                                    pDialog1.setConfirmText("OK");
                                    pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                            pDialog1.hide();
                                            ((Activity)contextx).finish();
                                            //finish();
                                        }
                                    });
                                    pDialog1.show();

                                    //then hapus
                                } else {
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Hapus Anggota Gagal");
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

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public void updatestatusbaca() {

    }

}
