package model;

/**
 * Created by Merna on 6/17/2018.
 */
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
import com.merna.temankocok.QrCodeActivity;
import com.merna.temankocok.R;
import com.merna.temankocok.ResetActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import customfonts.MyTextView;
import static com.merna.temankocok.R.color.message;

public class PemenangAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<PemenangModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMorePemenang onLoadMoreListener;

    public PemenangAdapter(List<PemenangModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMorePemenang();
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
                    R.layout.list_pemenang, parent, false);

            vh = new PemenangAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new PemenangAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PemenangAdapter.StudentViewHolder) {
            PemenangModel singleStudent= (PemenangModel) modeldataList.get(position);
            ((PemenangAdapter.StudentViewHolder) holder).id_surat1.setText(singleStudent.getId_group_arisan());
            ((PemenangAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getId_kocokan_nama()));
            ((PemenangAdapter.StudentViewHolder) holder).id_user.setText(String.valueOf(singleStudent.getId_user()));
            String dariString = String.valueOf(singleStudent.getNm_pemenang());

            try {
                //dari
                ((PemenangAdapter.StudentViewHolder) holder).dari.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PemenangAdapter.StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((PemenangAdapter.StudentViewHolder) holder).dari.setText(dariString);

                ((PemenangAdapter.StudentViewHolder) holder).nama_user.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PemenangAdapter.StudentViewHolder) holder).nama_user.setTypeface(Typeface.DEFAULT);
                ((PemenangAdapter.StudentViewHolder) holder).nama_user.setText("Periode : " + String.valueOf(singleStudent.getNm_periode()));

                //timestamp_p
                //nama_user
                ((PemenangAdapter.StudentViewHolder) holder).tglkocokq.setText("Tgl Kocok : " + singleStudent.getTgl_kocok());

                ((PemenangAdapter.StudentViewHolder) holder).icon_star1.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PemenangAdapter.StudentViewHolder) holder).icon_star1.setTypeface(Typeface.DEFAULT);
                ((PemenangAdapter.StudentViewHolder) holder).icon_star1.setText("Group : " + String.valueOf(singleStudent.getNama_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((PemenangAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((PemenangAdapter.StudentViewHolder) holder).contextx).load(R.drawable.ic_anggota_new)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((PemenangAdapter.StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((PemenangAdapter.StudentViewHolder) holder).imgProfile);
            ((PemenangAdapter.StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((PemenangAdapter.StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((KocokAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMorePemenang onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, icon_star1, id_user, nama_user, tglkocokq;
        public PemenangModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private String URL_UPDATE = "http://esurat.perumnas.co.id/esuratwebservice/updatesurat.php?";

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            dari = (TextView) v.findViewById(R.id.from_p);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile_p);
            id_penerima1 = (TextView) v.findViewById(R.id.id_pemenang);
            icon_star1 = (TextView) v.findViewById(R.id.icon_star_p);
            nama_user = (TextView) v.findViewById(R.id.timestamp_p);
            id_user = (TextView) v.findViewById(R.id.id_user_p);
            id_surat1 = (TextView) v.findViewById(R.id.id_group_arisan_p);
            tglkocokq = (TextView) v.findViewById(R.id.tglkocok_p);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_p);
            navButton4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //
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

    public void updatestatusbaca() {

    }

}
