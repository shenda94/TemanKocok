package com.merna.temankocok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.JadwalAdapter;
import model.JadwalModel;
import model.OnLoadMoreJadwal;

public class JadwaliuranActivity extends AppCompatActivity {
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private JadwalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "JadwaliuranActivity";
    private List<JadwalModel> modeldataList;
    String gabung;
    protected Handler handler;
    private ProgressDialog pDialog;
    //Volley Request Queue
    private RequestQueue requestQueue;
    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;
    private int LIMIT = 20;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    String id_group_arisan, id_periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwaliuran);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new ProgressDialog(this, R.style.ProgressBar);
        try {
            //pDialog = new ProgressDialog(this, R.style.ProgressBar);
            tvEmptyView = (TextView) findViewById(R.id.empty_viewjad);
            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_viewjad);
            handler = new Handler();
            modeldataList = new ArrayList<JadwalModel>();

            //SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            //id_user  = sharedPreferences.getString(AppVar.IDUSER_SHARED_PREF, "");

            SharedPreferences sharedPreferencesz = getSharedPreferences(AppVar.SHARED_PREF_PILIHPERIODE, Context.MODE_PRIVATE);
            id_group_arisan  = sharedPreferencesz.getString(AppVar.ID_GROUPPM_SHARED, "");
            id_periode  = sharedPreferencesz.getString(AppVar.ID_PERIODE_SHARED, "");

            gabung = AppVar.DATA_URL_jadwal + requestCount + "&id_periode=" + id_periode + "&id_group_arisan=" + id_group_arisan;

            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Process...");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            load_data_first();
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutjad);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // do something
                    mSwipeRefreshLayout.setRefreshing(true);
                    requestCount = 1;
                    gabung = AppVar.DATA_URL_jadwal + requestCount + "&id_periode=" + id_periode + "&id_group_arisan=" + id_group_arisan;
                    modeldataList = new ArrayList<JadwalModel>();
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
            Toast.makeText(JadwaliuranActivity.this, "Error: stu7 " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Toast.makeText(TentangActivity.this, id, Toast.LENGTH_LONG).show();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you sure you want to quit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        JadwaliuranActivity.this.finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        //nbutton.setBackgroundColor(Color.YELLOW);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        //pbutton.setBackgroundColor(Color.YELLOW);
        pbutton.setTextColor(Color.BLACK);


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
                                        String id_jadwal = person.getString("id_jadwal");
                                        String id_periode = person.getString("id_periode");
                                        String id_group_arisan = person.getString("id_group_arisan");
                                        String id_user = person.getString("id_user");
                                        String nama_group_arisan = person.getString("nama_group_arisan");
                                        String statusbayar = person.getString("statusbayar");
                                        String id_user_anggota = person.getString("id_user_anggota");
                                        String tgl_bayar = person.getString("tgl_bayar");
                                        String nominal = person.getString("nominal");
                                        String nama_user = person.getString("nama_user");

                                        //(String id_jadwal, String id_periode, String nominal, String tgl_bayar, String id_user, String id_group_arisan, String id_user_anggota, String statusbayar, String nama_user, String nama_group_arisan)

                                        modeldataList.add(new JadwalModel( id_jadwal,  id_periode,  nominal,  tgl_bayar,  id_user,  id_group_arisan,  id_user_anggota,  statusbayar,  nama_user,  nama_group_arisan));
                                    }
                                }

                                progresafter();
                            }



                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                            Toast.makeText(JadwaliuranActivity.this,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(JadwaliuranActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
        Volley.newRequestQueue(this).add(reqsubkategori);
    }

    public void progresafter() {
        try {
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            // use a linear layout manager
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            // create an Object for Adapter
            mAdapter = new JadwalAdapter(modeldataList, mRecyclerView);
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

            mAdapter.setOnLoadMoreListener(new OnLoadMoreJadwal() {
                @Override
                public void onLoadMoreJadwal() {
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
            Toast.makeText(JadwaliuranActivity.this, "Error: stu" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void onscrolldatax(int start) {
        //Toast.makeText(getActivity(), "test"+kodeunker, Toast.LENGTH_SHORT).show();
        // + "&id_periode=" + id_periode + "&id_group_arisan=" + id_group_arisan
        gabung = AppVar.DATA_URL_jadwal + start + "&id_periode=" + id_periode + "&id_group_arisan=" + id_group_arisan;

        JsonArrayRequest onscrolldata = new JsonArrayRequest(gabung,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response
                                        .get(i);

                                //Toast.makeText(MainActivity.this, person.getString("statuspesan"), Toast.LENGTH_SHORT).show();
                                if (person.getInt("statuspesan") == 1){
                                    String id_jadwal = person.getString("id_jadwal");
                                    String id_periode = person.getString("id_periode");
                                    String id_group_arisan = person.getString("id_group_arisan");
                                    String id_user = person.getString("id_user");
                                    String nama_group_arisan = person.getString("nama_group_arisan");
                                    String statusbayar = person.getString("statusbayar");
                                    String id_user_anggota = person.getString("id_user_anggota");
                                    String tgl_bayar = person.getString("tgl_bayar");
                                    String nominal = person.getString("nominal");
                                    String nama_user = person.getString("nama_user");

                                    //(String id_jadwal, String id_periode, String nominal, String tgl_bayar, String id_user, String id_group_arisan, String id_user_anggota, String statusbayar, String nama_user, String nama_group_arisan)

                                    modeldataList.add(new JadwalModel( id_jadwal,  id_periode,  nominal,  tgl_bayar,  id_user,  id_group_arisan,  id_user_anggota,  statusbayar,  nama_user,  nama_group_arisan));
                                    mAdapter.notifyItemInserted(modeldataList.size());
                                }
                                else {
                                    String isipesan = person.getString("isipesan");
                                }
                            }

                            mAdapter.setLoaded();
                            //progresafter();

                        } catch (JSONException e) {
                            //hideDialog();
                            e.printStackTrace();
                            Toast.makeText(JadwaliuranActivity.this,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //hideDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(JadwaliuranActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
        Volley.newRequestQueue(this).add(onscrolldata);
    }

}
