package model;

/**
 * Created by merna.shenda on 7/9/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.merna.temankocok.PembayaranActivity;
import com.merna.temankocok.PeriodeActivity;
import com.merna.temankocok.PilihKocokanActivity;
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

public class JadwalAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<JadwalModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreJadwal onLoadMoreListener;

    public JadwalAdapter(List<JadwalModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMoreJadwal();
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
                    R.layout.list_jadwal, parent, false);

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
        if (holder instanceof JadwalAdapter.StudentViewHolder) {
            JadwalModel singleStudent= (JadwalModel) modeldataList.get(position);
            String dariString = String.valueOf(singleStudent.getNama_user());

            try {
                //dari
                ((StudentViewHolder) holder).nama_user.setTextColor(((JadwalAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((JadwalAdapter.StudentViewHolder) holder).nama_user.setTypeface(Typeface.DEFAULT);
                ((JadwalAdapter.StudentViewHolder) holder).nama_user.setText(dariString);

                //((StudentViewHolder) holder).nominal.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((JadwalAdapter.StudentViewHolder) holder).nominal.setTypeface(Typeface.DEFAULT);
                ((JadwalAdapter.StudentViewHolder) holder).nominal.setText("Rp. " + String.valueOf(singleStudent.getNominal()));

                //timestamp_p
                //nama_user
                ((StudentViewHolder) holder).tgl_bayar.setText("Tgl Lunas : " + singleStudent.getTgl_bayar());
                ((StudentViewHolder) holder).statusbayar.setText("Status : " + singleStudent.getStatusbayar());

                //((StudentViewHolder) holder).nmgroup.setTextColor(((PeriodeAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((JadwalAdapter.StudentViewHolder) holder).nama_group_arisan.setTypeface(Typeface.DEFAULT);
                ((JadwalAdapter.StudentViewHolder) holder).nama_group_arisan.setText("Group : " + String.valueOf(singleStudent.getNama_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((JadwalAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            ((JadwalAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((JadwalAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreJadwal onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_user, nama_group_arisan, statusbayar, tgl_bayar, nominal;
        public JadwalModel modeldata;
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
            nama_user = (TextView) v.findViewById(R.id.from_jad);
            nama_group_arisan = (TextView) v.findViewById(R.id.icon_star_jad);
            nominal = (TextView) v.findViewById(R.id.timestamp_jad);
            tgl_bayar = (TextView) v.findViewById(R.id.tglbayar_jad);
            statusbayar = (TextView) v.findViewById(R.id.statusbayar_jad);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_jad);
            navButton4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    custom_dialog1();

                }
            });

        }

        public void kirimreminder() {
            urll = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/reminder?id_user="+modeldata.getid_user();
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Proses Kirim...");
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
                                    pDialog1.setContentText("Berhasil Mengirim Reminder");
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

                                } else {
                                    hideDialog();
                                    final SweetAlertDialog pDialog1 = new SweetAlertDialog(contextx, SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.setTitleText("Informasi");
                                    pDialog1.setContentText("Gagal Mengirim Reminder");
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
                            Toast.makeText(contextx, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
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

        public void custom_dialog1() {
            dialog = new AlertDialog.Builder(contextx);
            inflater = LayoutInflater.from(contextx);
            dialogView = inflater.inflate(R.layout.custom_dialog, null);
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
                    kirimreminder();
                    alertDialog.dismiss();
                }
            });

            btnscan1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PILIHUSRPERIODE, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Adding values to editor
                    editor.putString(AppVar.ID_userPM_SHARED, modeldata.getid_user());
                    editor.commit();

                    Intent intent = new Intent(contextx, PilihKocokanActivity.class);
                    contextx.startActivity(intent);
                    alertDialog.dismiss();
                }
            });

            btnscan2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            /*dialog.setPositiveButton("Kirim Reminder", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //simpan_update();
                    kirimreminder();
                    dialog.dismiss();
                }
            });*/

            /*dialog.setNegativeButton("Approve Pembayaran", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PILIHUSRPERIODE, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Adding values to editor
                    editor.putString(AppVar.ID_userPM_SHARED, modeldata.getid_user());
                    editor.commit();

                    Intent intent = new Intent(contextx, PilihKocokanActivity.class);
                    contextx.startActivity(intent);
                    dialog.dismiss();
                }
            });*/

            //dialog.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
            //    @Override
            //    public void onClick(DialogInterface dialog, int which) {
            //        dialog.dismiss();
            //    }
            //});

            //dialog.show();

            alertDialog.show();

            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            //nbutton.setBackgroundColor(Color.YELLOW);
            nbutton.setTextColor(Color.BLACK);
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            //pbutton.setBackgroundColor(Color.YELLOW);
            pbutton.setTextColor(Color.BLACK);
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
