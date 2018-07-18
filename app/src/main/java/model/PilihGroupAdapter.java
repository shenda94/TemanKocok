package model;

/**
 * Created by Merna on 6/16/2018.
 */
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
import com.merna.temankocok.KocokanNamaActivity;
import com.merna.temankocok.ListAnggota;
import com.merna.temankocok.LoginActivity;
import com.merna.temankocok.Main2Activity;
import com.merna.temankocok.MainActivity;
import com.merna.temankocok.PeriodeActivity;
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

public class PilihGroupAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<PilihGroupModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnloadMorePilihGroup onLoadMoreListener;

    public PilihGroupAdapter(List<PilihGroupModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMorePilihGroup();
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
                    R.layout.list_pilih_group, parent, false);

            vh = new PilihGroupAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new PilihGroupAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PilihGroupAdapter.StudentViewHolder) {
            PilihGroupModel singleStudent= (PilihGroupModel) modeldataList.get(position);
            ((PilihGroupAdapter.StudentViewHolder) holder).id_surat1.setText(singleStudent.getId_group_arisan());
            ((PilihGroupAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getKode_barcode()));
            String dariString = String.valueOf(singleStudent.getNama_group_arisan());

            try {
                //dari
                ((PilihGroupAdapter.StudentViewHolder) holder).dari.setTextColor(((PilihGroupAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PilihGroupAdapter.StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((PilihGroupAdapter.StudentViewHolder) holder).dari.setText(dariString);
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((PilihGroupAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((PilihGroupAdapter.StudentViewHolder) holder).contextx).load(R.drawable.ic_group_b)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((PilihGroupAdapter.StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((PilihGroupAdapter.StudentViewHolder) holder).imgProfile);
            ((PilihGroupAdapter.StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((PilihGroupAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((PilihGroupAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnloadMorePilihGroup onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, jenis_surat1;
        public PilihGroupModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private ProgressDialog pDialog;
        String urll;
        String hasil1 = "1";
        String hasil2 = "2";
        String hasil3 = "3";
        String hasil4 = "4";
        String hasil5 = "5";
        String hasil6 = "6";
        String hasil7 = "7";
        String hasil;

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            pDialog = new ProgressDialog(contextx, R.style.ProgressBar);
            dari = (TextView) v.findViewById(R.id.from_pilih);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile_pilih);
            id_penerima1 = (TextView) v.findViewById(R.id.id_penerima_pilih);
            id_surat1 = (TextView) v.findViewById(R.id.id_surat_pilih);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_pilih);
            navButton4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPreferences2 = contextx.getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);
                    hasil = sharedPreferences2.getString(AppVar.MENU_SHARED_PREF3,"");
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


                    if (hasil.equals(hasil3)){
                        //Intent intent = new Intent(contextx, ResetActivity.class);
                        //contextx.startActivity(intent);
                        checkuser();
                    }
                    else if (hasil.equals(hasil1)){
                        Intent intent = new Intent(contextx, ListAnggota.class);
                        contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil2)){
                        Intent intent = new Intent(contextx, KocokanNamaActivity.class);
                        contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil4)){
                        Intent intent = new Intent(contextx, PilihPeriodeActivity.class);
                        contextx.startActivity(intent);
                        //Intent intent = new Intent(contextx, MainActivity.class);
                        //contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil5)){
                        //check jika ketua group
                        checkuser();
                    }
                    else if (hasil.equals(hasil6)){
                        checkuser();
                        //Intent intent = new Intent(contextx, PilihPeriodeActivity.class);
                        //contextx.startActivity(intent);
                    }
                    else if (hasil.equals(hasil7)){
                        Intent intent = new Intent(contextx, PilihPeriodeActivity.class);
                        contextx.startActivity(intent);
                    }

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

                                    if (hasil.equals(hasil3)){
                                        Intent intent = new Intent(contextx, ResetActivity.class);
                                        contextx.startActivity(intent);
                                    }
                                    else if (hasil.equals(hasil5)){
                                        //check jika ketua group
                                        Intent intent = new Intent(contextx, PeriodeActivity.class);
                                        contextx.startActivity(intent);
                                    }
                                    else if (hasil.equals(hasil6)){
                                        //checkuser();
                                        Intent intent = new Intent(contextx, PilihPeriodeActivity.class);
                                        contextx.startActivity(intent);
                                    }
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

    public void checkketuagroup() {

    }

}
