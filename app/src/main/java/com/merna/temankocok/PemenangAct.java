package com.merna.temankocok;

/**
 * Created by Merna on 12/30/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.bumptech.glide.load.engine.Resource;
import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;

public class PemenangAct extends AppCompatActivity {
    String Nama, IDD, IUSE,idpe;
    TextView namalengkap, nil;
    private ProgressDialog pDialog;
    private Context context;
    int success;
    private static final String TAG_SUCCESS = "success";
    MediaPlayer mp;
    protected int[] colors;
    protected ViewGroup container;
    protected int orange, darkorange, green, yello3;
    //KonfettiView konfettiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_pemenang);
            context = PemenangAct.this;
            pDialog = new ProgressDialog(context, R.style.ProgressBar);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            container = (ViewGroup) findViewById(R.id.container);
            namalengkap = (TextView) findViewById (R.id.namapemenang);
            nil = (TextView) findViewById (R.id.nilai);

            final Resources res = getResources();
            orange = res.getColor(R.color.colorPrimary);
            darkorange = res.getColor(R.color.colorPrimaryDark);
            green = res.getColor(R.color.green2);
            yello3 = res.getColor(R.color.yellow2);
            colors = new int[] {orange, darkorange, green, yello3};

            //UPDATE
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            pDialog.setMessage("Harap Tunggu");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_PEMENANG, Context.MODE_PRIVATE);
            Nama = sharedPreferences.getString(AppVar.NAMA_PEMENANG_SHARED_PREF2, "");
            IDD = sharedPreferences.getString(AppVar.ID_GROUPPM_SHARED_PREF2, "");
            idpe = sharedPreferences.getString(AppVar.idperiode_SHARED_PREF2, "");

            SharedPreferences sharedPreferences2 = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            IUSE = sharedPreferences2.getString(AppVar.IDUSER_SHARED_PREF, "");

//Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.PEMENANG_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from serverToast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                            //cal json
                            try {
                                JSONObject jObj = new JSONObject(response);
                                success = jObj.getInt(TAG_SUCCESS);

                                //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                                // Cek error node pada json
                                if (success == 1) {
                                    //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    //generateInfinite();
                                    CommonConfetti.rainingConfetti(container,colors).infinite();
                                    mp = MediaPlayer.create(PemenangAct.this, R.raw.levelwin);
                                    mp.setLooping(false);
                                    mp.start();
                                    namalengkap.setText(Nama.toUpperCase());
                                    nil.setText("Rp. " + jObj.getString("message"));

                                } else {
                                    hideDialog();
                                    //Displaying an error message on toast
                                    //Toast.makeText(context, "Register Gagal", Toast.LENGTH_LONG).show();
                                    //Toast.makeText(MainActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                // JSON error
                                Toast.makeText(context, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
//Adding parameters to request
                    params.put(AppVar.NamaKocokan, Nama);
                    params.put(AppVar.id_grouparisan, IDD);
                    params.put(AppVar.id_userKetua, IUSE);
                    params.put(AppVar.id_periodep, idpe);
//returning parameter
                    return params;
                }
            };
//Adding the string request to the queue
            Volley.newRequestQueue(this).add(stringRequest);

            findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mp.stop();
                    finish();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(PemenangAct.this, "Error: stus" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    //@Override
    ///protected ConfettiManager generateInfinite() {
    //    return CommonConfetti.rainingConfetti(container,colors).infinite();
    //}

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
            // finish the activity

            //Toast.makeText(TentangActivity.this, "tes", Toast.LENGTH_LONG).show();
            //Intent menuawal = new Intent(this, Main2Activity.class);
            //startActivity(menuawal);
            //finish();
            //onBackPressed();
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
                        PemenangAct.this.finish();
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

}
