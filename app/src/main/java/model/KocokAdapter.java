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
import customfonts.MyTextView;
import static com.merna.temankocok.R.color.message;

public class KocokAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<KocokModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreKocok onLoadMoreListener;

    public KocokAdapter(List<KocokModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMoreKocok();
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
                    R.layout.layout_kocok, parent, false);

            vh = new KocokAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new KocokAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KocokAdapter.StudentViewHolder) {
            KocokModel singleStudent= (KocokModel) modeldataList.get(position);
            ((KocokAdapter.StudentViewHolder) holder).id_surat1.setText(singleStudent.getId_group_arisan());
            ((KocokAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getId_kocokan_nama()));
            ((KocokAdapter.StudentViewHolder) holder).id_user.setText(String.valueOf(singleStudent.getId_user()));
            String dariString = String.valueOf(singleStudent.getNm_kocokan());

            try {
                //dari
                ((KocokAdapter.StudentViewHolder) holder).dari.setTextColor(((KocokAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((KocokAdapter.StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((KocokAdapter.StudentViewHolder) holder).dari.setText(dariString);

                ((KocokAdapter.StudentViewHolder) holder).nama_user.setTextColor(((KocokAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((KocokAdapter.StudentViewHolder) holder).nama_user.setTypeface(Typeface.DEFAULT);
                ((KocokAdapter.StudentViewHolder) holder).nama_user.setText("User : " + String.valueOf(singleStudent.getNama_user()));

                ((KocokAdapter.StudentViewHolder) holder).icon_star1.setTextColor(((KocokAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((KocokAdapter.StudentViewHolder) holder).icon_star1.setTypeface(Typeface.DEFAULT);
                ((KocokAdapter.StudentViewHolder) holder).icon_star1.setText("Group : " + String.valueOf(singleStudent.getNama_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((KocokAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((KocokAdapter.StudentViewHolder) holder).contextx).load(R.drawable.ic_anggota_new)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((KocokAdapter.StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((KocokAdapter.StudentViewHolder) holder).imgProfile);
            ((KocokAdapter.StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((KocokAdapter.StudentViewHolder) holder).modeldata= singleStudent;
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

    public void setOnLoadMoreListener(OnLoadMoreKocok onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, icon_star1, id_user, nama_user;
        public KocokModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private String URL_UPDATE = "http://esurat.perumnas.co.id/esuratwebservice/updatesurat.php?";

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            dari = (TextView) v.findViewById(R.id.from_k);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile_k);
            id_penerima1 = (TextView) v.findViewById(R.id.id_kocokan_nama);
            icon_star1 = (TextView) v.findViewById(R.id.icon_star_k);
            nama_user = (TextView) v.findViewById(R.id.timestamp_k);
            id_user = (TextView) v.findViewById(R.id.id_user_k);
            id_surat1 = (TextView) v.findViewById(R.id.id_group_arisan_k);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_k);
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
