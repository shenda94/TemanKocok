package model;

/**
 * Created by merna.shenda on 7/4/2018.
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
import com.merna.temankocok.TambahPeriodeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import customfonts.MyTextView;
import static com.merna.temankocok.R.color.message;

public class PeriodeAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<PeriodeModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMorePeriode onLoadMoreListener;

    public PeriodeAdapter(List<PeriodeModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMorePeriode();
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
                    R.layout.list_periode, parent, false);

            vh = new PeriodeAdapter.StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.lay_progress, parent, false);

            vh = new PeriodeAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PeriodeAdapter.StudentViewHolder) {
            PeriodeModel singleStudent= (PeriodeModel) modeldataList.get(position);
            ((StudentViewHolder) holder).id_periode.setText(singleStudent.getId_periode());
            //((PeriodeAdapter.StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getId_kocokan_nama()));
            //((PeriodeAdapter.StudentViewHolder) holder).id_user.setText(String.valueOf(singleStudent.getId_user()));
            String dariString = String.valueOf(singleStudent.getNama_periode());

            try {
                //dari
                ((PeriodeAdapter.StudentViewHolder) holder).nmperiode.setTextColor(((PeriodeAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PeriodeAdapter.StudentViewHolder) holder).nmperiode.setTypeface(Typeface.DEFAULT);
                ((PeriodeAdapter.StudentViewHolder) holder).nmperiode.setText(dariString);

                //((StudentViewHolder) holder).nominal.setTextColor(((PemenangAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PeriodeAdapter.StudentViewHolder) holder).nominal.setTypeface(Typeface.DEFAULT);
                ((PeriodeAdapter.StudentViewHolder) holder).nominal.setText("Rp. " + String.valueOf(singleStudent.getNominal()));

                //timestamp_p
                //nama_user
                ((StudentViewHolder) holder).tglkocok.setText("Kocok : " + singleStudent.getTgl_jadwalkocok());
                ((StudentViewHolder) holder).tglperiode.setText("Periode : " + singleStudent.getTgl_periode());

                //((StudentViewHolder) holder).nmgroup.setTextColor(((PeriodeAdapter.StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((PeriodeAdapter.StudentViewHolder) holder).nmgroup.setTypeface(Typeface.DEFAULT);
                ((PeriodeAdapter.StudentViewHolder) holder).nmgroup.setText("Group : " + String.valueOf(singleStudent.getId_group_arisan()));
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((PeriodeAdapter.StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((PeriodeAdapter.StudentViewHolder) holder).contextx).load(R.drawable.ic_periode)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((PeriodeAdapter.StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((PeriodeAdapter.StudentViewHolder) holder).imgProfile);
            ((PeriodeAdapter.StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((PeriodeAdapter.StudentViewHolder) holder).modeldata= singleStudent;
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

    public void setOnLoadMoreListener(OnLoadMorePeriode onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView id_periode, nmperiode, tglkocok, tglperiode, nominal, nmgroup;
        public PeriodeModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            //dari = (TextView) v.findViewById(R.id.from_p);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile_pr);
            id_periode = (TextView) v.findViewById(R.id.id_periodepr);
            nmgroup = (TextView) v.findViewById(R.id.icon_star_pr);
            nominal = (TextView) v.findViewById(R.id.timestamp_pr);
            tglperiode = (TextView) v.findViewById(R.id.akun_tr);
            tglkocok = (TextView) v.findViewById(R.id.tglkocok_pr);
            nmperiode = (TextView) v.findViewById(R.id.from_pr);

            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container_pr);
            navButton4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //
                    //
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_PERIODE, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putString(AppVar.ID_PERIODE_SHARED_PR, modeldata.getId_periode());
                    editor.putString(AppVar.NAMA_PERIODE_SHARED_PR, modeldata.getNama_periode());
                    editor.putString(AppVar.NOMINAL_PERIODE_SHARED_PR, modeldata.nominal);
                    //editor.putString(AppVar.ID_GROUPARISAN_SHARED_PR, modeldata.id_group_arisan);
                    //editor.putString(AppVar.ID_USER_SHARED_PR, modeldata.id);
                    editor.putString(AppVar.TGLPERIODE_SHARED_PR, modeldata.getTgl_periode());
                    editor.putString(AppVar.TGLJADWALKOCOK_SHARED_PR, modeldata.getTgl_jadwalkocok());
                    editor.commit();

                    Intent intent = new Intent(contextx, TambahPeriodeActivity.class);
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

    public void updatestatusbaca() {

    }
}
