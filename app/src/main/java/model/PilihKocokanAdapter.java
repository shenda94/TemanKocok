package model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.merna.temankocok.AppVar;
import com.merna.temankocok.CircleTransform;
import com.merna.temankocok.KocokanNamaActivity;
import com.merna.temankocok.ListAnggota;
import com.merna.temankocok.MainActivity;
import com.merna.temankocok.PembayaranActivity;
import com.merna.temankocok.PembayaranIuranActivity;
import com.merna.temankocok.PeriodeActivity;
import com.merna.temankocok.PilihPeriodeActivity;
import com.merna.temankocok.R;
import com.merna.temankocok.ResetActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by merna.shenda on 7/9/2018.
 */

public class PilihKocokanAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<PilihKocokanModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMorePilihKocok onLoadMoreListener;

    public PilihKocokanAdapter(List<PilihKocokanModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMorePilihKocok();
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
                    R.layout.pilih_kocokan, parent, false);

            vh = new PilihKocokanAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new PilihKocokanAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PilihKocokanAdapter.StudentViewHolder) {
            PilihKocokanModel singleStudent= (PilihKocokanModel) modeldataList.get(position);
            ((PilihKocokanAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getId_kocokannama()));
            String dariString = String.valueOf(singleStudent.getNama_kocokan());

            try {
                //dari
                ((PilihKocokanAdapter.StudentViewHolder) holder).dari.setTextColor(((PilihKocokanAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PilihKocokanAdapter.StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((PilihKocokanAdapter.StudentViewHolder) holder).dari.setText(dariString);
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((PilihGroupAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            ((PilihKocokanAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((PilihKocokanAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMorePilihKocok onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, jenis_surat1;
        public PilihKocokanModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private ProgressDialog pDialog;
        String nilai1;

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            pDialog = new ProgressDialog(contextx, R.style.ProgressBar);
            dari = (TextView) v.findViewById(R.id.from_pilih);
            id_penerima1 = (TextView) v.findViewById(R.id.id_penerima_pilih);

            SharedPreferences sharedPreferencesz = contextx.getSharedPreferences(AppVar.SHARED_PREF_PILIHPERIODE, Context.MODE_PRIVATE);
            nilai1 = sharedPreferencesz.getString(AppVar.NILAI_SHARED, "");

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_pilih);
            navButton4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //
                    Intent intent = new Intent(contextx, PembayaranActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idkocokan",modeldata.getId_kocokannama());
                    bundle.putString("nmkocokan",modeldata.getNama_kocokan());
                    bundle.putString("nilai",nilai1);
                    intent.putExtras(bundle);
                    contextx.startActivity(intent);

                }
            });

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
