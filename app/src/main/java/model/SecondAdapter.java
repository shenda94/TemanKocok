package model;

/**
 * Created by merna.shenda on 4/30/2018.
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
import com.merna.temankocok.LoginActivity;
import com.merna.temankocok.Main2Activity;
import com.merna.temankocok.QrCodeActivity;
import com.merna.temankocok.R;
import com.merna.temankocok.TambahGroupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import customfonts.MyTextView;
import static com.merna.temankocok.R.color.message;

public class SecondAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<GroupArisanModel> modeldataList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreGroup onLoadMoreListener;

    public SecondAdapter(List<GroupArisanModel> modeldata, RecyclerView recyclerView) {
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
                                        onLoadMoreListener.onLoadMoreGroup();
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
                    R.layout.message_list_row1, parent, false);

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
        if (holder instanceof StudentViewHolder) {
            GroupArisanModel singleStudent= (GroupArisanModel) modeldataList.get(position);
            ((StudentViewHolder) holder).id_surat1.setText(singleStudent.getId_group_arisan());
            //((StudentViewHolder) holder).jenis_surat1.setText(String.valueOf(singleStudent.getId_user()));
            ((StudentViewHolder) holder).id_penerima1.setText(String.valueOf(singleStudent.getKode_barcode()));
            String tempString = String.valueOf(singleStudent.getNama_group_arisan());
            String dariString = String.valueOf(singleStudent.getNama_group_arisan());
            String perihalString = String.valueOf(singleStudent.getNama_group_arisan());

            try {
                //dari
                ((StudentViewHolder) holder).dari.setTextColor(((StudentViewHolder) holder).contextx.getResources().getColor(R.color.colorText));
                ((StudentViewHolder) holder).dari.setTypeface(Typeface.DEFAULT);
                ((StudentViewHolder) holder).dari.setText(dariString);
            } catch (Exception e) {
                //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                Toast.makeText(((StudentViewHolder) holder).contextx, e.toString(), Toast.LENGTH_LONG).show();
            }

            Glide.with(((StudentViewHolder) holder).contextx).load(R.drawable.ic_group_b)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(((StudentViewHolder) holder).contextx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((StudentViewHolder) holder).imgProfile);
            ((StudentViewHolder) holder).imgProfile.setColorFilter(null);
            ((StudentViewHolder) holder).modeldata= singleStudent;
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return modeldataList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreGroup onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView dari;
        public TextView id_penerima1, id_surat1, jenis_surat1;
        public GroupArisanModel modeldata;
        public Context contextx;
        public ImageView imgProfile, img1;
        int success;
        private String URL_UPDATE = "http://esurat.perumnas.co.id/esuratwebservice/updatesurat.php?";

        public StudentViewHolder(View v) {
            super(v);
            contextx = v.getContext();
            dari = (TextView) v.findViewById(R.id.from);
            imgProfile = (ImageView) v.findViewById(R.id.icon_profile);
            img1 = (ImageView) v.findViewById(R.id.icon_profile1);
            id_penerima1 = (TextView) v.findViewById(R.id.id_penerima);
            id_surat1 = (TextView) v.findViewById(R.id.id_surat);

            img1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_BARCODE, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(AppVar.ID_GROUP_SHARED_PREF, id_surat1.getText().toString());
                    editor.commit();

                    //
                    contextx.startActivity(new Intent(contextx.getApplicationContext(),QrCodeActivity.class));
                    //contextx.finish();
                }
            });


            LinearLayout navButton4 = (LinearLayout) v.findViewById(R.id.message_container);
            navButton4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    //send all parameter to global variable, that will show to another activity
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = contextx.getSharedPreferences(AppVar.SHARED_PREF_GROUP, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putString(AppVar.ID_GROUP_SHARED_PREF2, modeldata.getId_group_arisan());
                    editor.putString(AppVar.NAMA_GROUP_SHARED_PREF2, modeldata.getNama_group_arisan());
                    editor.putString(AppVar.KODEBARCODE_GROUP_SHARED_PREF2, modeldata.getKode_barcode());
                    editor.commit();

                    /*editor.putString(AppVar.KEPADA2, modeldata.getNama_penerima());
                    editor.putString(AppVar.DARI2, modeldata.getNama_jab());
                    editor.putString(AppVar.PERIHAL2, modeldata.getPerihal());
                    editor.putString(AppVar.FILESURAT2, modeldata.getNm_file());
                    editor.putString(AppVar.FOLDERSURAT2, modeldata.getNm_folder());
                    editor.putString(AppVar.TGLSURAT2, modeldata.getTgl_surat());
                    editor.putString(AppVar.NOMOR2, modeldata.getNomor_surat());
                    editor.putString(AppVar.STATUSPENERIMA2, modeldata.getStatus_penerima());
                    */

                    //Toast.makeText(contextx, modeldata.getStatus_baca(), Toast.LENGTH_LONG).show();

                    //update unread status to be read
                    //so let's begin
                    //we need using method for call web service. then update it

                    Intent intent = new Intent(contextx, TambahGroupActivity.class);
                    contextx.startActivity(intent);
                    //contextx.finish();
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
