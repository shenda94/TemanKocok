package com.merna.temankocok.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Merna on 6/17/2018.
 */
import android.app.ProgressDialog;
import android.os.Handler;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.merna.temankocok.AppVar;
import com.merna.temankocok.DividerItemDecoration;
import com.merna.temankocok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.OnLoadMorePemenang;
import model.PemenangAdapter;
import model.PemenangModel;

public class PemenangFragment extends Fragment {
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private PemenangAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "MainActivity";
    private List<PemenangModel> modeldataList;
    String gabung;
    protected Handler handler;
    private ProgressDialog pDialog;
    //Volley Request Queue
    private RequestQueue requestQueue;
    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;
    private int LIMIT = 20;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    String id_group_arisan;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pemenang_fragment,container,false);
        try {
            pDialog = new ProgressDialog(getActivity(), R.style.ProgressBar);
            tvEmptyView = (TextView) v.findViewById(R.id.empty_view_p1);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view_p1);
            handler = new Handler();
            modeldataList = new ArrayList<PemenangModel>();

            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(AppVar.SHARED_PREF_BARCODE3, Context.MODE_PRIVATE);
            id_group_arisan  = sharedPreferences.getString(AppVar.ID_GROUP_SHARED_PREF3, "");

            //Toast.makeText(getActivity(), "Error: stu7 " + kodejab, Toast.LENGTH_LONG).show();
            gabung = AppVar.DATA_URLP + requestCount + "&id_group_arisan=" + id_group_arisan;

            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Process...");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            load_data_first();

            mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout_p1);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // do something
                    mSwipeRefreshLayout.setRefreshing(true);
                    requestCount = 1;
                    gabung = AppVar.DATA_URLP + requestCount + "&id_group_arisan=" + id_group_arisan;
                    modeldataList = new ArrayList<PemenangModel>();
                    //Toast.makeText(getActivity(), "Error: stu7 " + gabung, Toast.LENGTH_LONG).show();

                    load_data_first();

                    // after refresh is done, remember to call the following code
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        //Toast.makeText(MainActivity.this, "tes0", Toast.LENGTH_SHORT).show();
                        mAdapter.notifyDataSetChanged();
                        //mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                        mSwipeRefreshLayout.setRefreshing(false);  // This hides the spinner
                    }
                }
            });
        }

        catch (Exception e) {
            Toast.makeText(getActivity(), "Error: stu7 " + e.toString(), Toast.LENGTH_LONG).show();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //requestCount = 1;
        //gabung = AppVar.DATA_URLP + requestCount + "&id_group_arisan=" + id_group_arisan;
        //modeldataList = new ArrayList<PemenangModel>();
        //load_data_first();
        //mAdapter = new SecondAdapter(modeldataList, mRecyclerView);

        //mAdapter.notifyDataSetChanged();
        //mRecyclerView.invalidate();
        //Toast.makeText(getActivity(), "Error: stu7 ", Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void load_data_first() {
        //Toast.makeText(getActivity(), kodejab, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), gabung, Toast.LENGTH_SHORT).show();

        JsonArrayRequest reqsubkategori = new JsonArrayRequest(gabung,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            hideDialog();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject person = (JSONObject) response
                                            .get(i);

                                    if (person.getInt("statuspesan") == 1){
                                        String id_pemenang = person.getString("id_pemenang");
                                        String id_group_arisan = person.getString("id_group_arisan");
                                        String id_user = person.getString("id_user");
                                        String nama_group_arisan = person.getString("nama_group_arisan");
                                        String id_nama_kocokan = person.getString("id_nama_kocokan");
                                        String nama_user = person.getString("nama_user");
                                        String nm_pemenang = person.getString("nm_pemenang");
                                        String tgl_kocok = person.getString("tgl_kocok");
                                        String nm_periode = person.getString("nm_periode");
                                        String statuspesan = person.getString("statuspesan");

                                        modeldataList.add(new PemenangModel(id_group_arisan, id_nama_kocokan,nama_group_arisan,nm_pemenang,id_user,nama_user,id_pemenang, tgl_kocok, nm_periode));
                                    }

                                }

                                progresafter();
                            }

                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
        Volley.newRequestQueue(this.getActivity()).add(reqsubkategori);
    }

    public void progresafter() {
        try {
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this.getActivity());
            // use a linear layout manager
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
            // create an Object for Adapter
            mAdapter = new PemenangAdapter(modeldataList, mRecyclerView);
            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);

            if (modeldataList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, " row bernilai 0 ", Toast.LENGTH_SHORT).show();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
                //Toast.makeText(getActivity(), " row bernilai 0 ", Toast.LENGTH_SHORT).show();
            }

            mAdapter.setOnLoadMoreListener(new OnLoadMorePemenang() {
                @Override
                public void onLoadMorePemenang() {
                    //add null , so the adapter will check view_type and show progress bar at bottom
                    modeldataList.add(null);
                    mAdapter.notifyItemInserted(modeldataList.size() - 1);
                    //Toast.makeText(MainActivity.this, " set onload more ", Toast.LENGTH_SHORT).show();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //   remove progress item
                            modeldataList.remove(modeldataList.size() - 1);
                            mAdapter.notifyItemRemoved(modeldataList.size());
                            requestCount = requestCount + 1;
                            onscrolldatax(requestCount);
                        }
                    }, 2000);

                }
            });
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), "Error: stu" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void onscrolldatax(int start) {
        //Toast.makeText(getActivity(), "test"+kodeunker, Toast.LENGTH_SHORT).show();
        gabung = AppVar.DATA_URLP + start + "&id_group_arisan=" + id_group_arisan;

        JsonArrayRequest onscrolldata = new JsonArrayRequest(gabung,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response
                                        .get(i);

                                //String id_group_arisan, String id_kocokan_nama, String nama_group_arisan, String nm_kocokan, String id_user, String nama_user, String statussleesai

                                //Toast.makeText(MainActivity.this, person.getString("statuspesan"), Toast.LENGTH_SHORT).show();
                                if (person.getInt("statuspesan") == 1){

                                    String id_pemenang = person.getString("id_pemenang");
                                    String id_group_arisan = person.getString("id_group_arisan");
                                    String id_user = person.getString("id_user");
                                    String nama_group_arisan = person.getString("nama_group_arisan");
                                    String id_nama_kocokan = person.getString("id_nama_kocokan");
                                    String nama_user = person.getString("nama_user");
                                    String nm_pemenang = person.getString("nm_pemenang");
                                    String statuspesan = person.getString("statuspesan");
                                    String tgl_kocok = person.getString("tgl_kocok");
                                    String nm_periode = person.getString("nm_periode");

                                    modeldataList.add(new PemenangModel(id_group_arisan, id_nama_kocokan,nama_group_arisan,nm_pemenang,id_user,nama_user,id_pemenang,tgl_kocok,nm_periode));
                                    mAdapter.notifyItemInserted(modeldataList.size());
                                }
                                else {
                                    String isipesan = person.getString("isipesan");
                                    //Toast.makeText(getActivity(), isipesan, Toast.LENGTH_SHORT).show();
                                }
                            }

                            mAdapter.setLoaded();

                            //progresafter();

                        } catch (JSONException e) {
                            //hideDialog();
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //hideDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
        Volley.newRequestQueue(this.getActivity()).add(onscrolldata);
    }

}