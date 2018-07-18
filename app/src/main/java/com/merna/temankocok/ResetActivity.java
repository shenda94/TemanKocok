package com.merna.temankocok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;
import static com.merna.temankocok.R.raw.success;

public class ResetActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;
    private Button btn1;
    int success;
    private String URL_UPDATE = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/resetstatus?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        context = ResetActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new ProgressDialog(context);

        btn1 = (Button) findViewById(R.id.btnresetarisan);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ResetActivity.this,LoginActivity.class));
                //update
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_BARCODE3, Context.MODE_PRIVATE);
                String url = URL_UPDATE + "id_group_arisan=" + sharedPreferences.getString(AppVar.ID_GROUP_SHARED_PREF3,"");
                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                // Volley's json array request object
                StringRequest req = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {

                                    JSONObject jObj = new JSONObject(response);
                                    success = jObj.getInt("success");

                                    if (success == 1) {
                                        //update recyclerview
                                        //Toast.makeText(getApplicationContext(), "brhasil", Toast.LENGTH_LONG).show();
                                        final SweetAlertDialog pDialog = new SweetAlertDialog(ResetActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                        pDialog.setTitleText("Informasi");
                                        pDialog.setContentText("Reset Arisan Telah Berhasil!");
                                        pDialog.setCancelable(false);
                                        pDialog.setConfirmText("OK");
                                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                // reuse previous dialog instance
                                                //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                                pDialog.hide();
                                                finish();
                                            }
                                        });
                                        pDialog.show();
                                    }
                                    else {
                                        final SweetAlertDialog pDialog = new SweetAlertDialog(ResetActivity.this, SweetAlertDialog.ERROR_TYPE);
                                        pDialog.setTitleText("Informasi");
                                        pDialog.setContentText("Gagal Reset!");
                                        pDialog.setCancelable(false);
                                        pDialog.setConfirmText("OK");
                                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                // reuse previous dialog instance
                                                //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                                pDialog.hide();
                                                finish();
                                            }
                                        });
                                        pDialog.show();
                                        //finish();
                                    }

                                } catch (JSONException e) {
                                    //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "Server Error: " + error.getMessage());

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                // Adding request to request queue
                //AppController.getInstance().addToRequestQueue(req);
                Volley.newRequestQueue(getApplicationContext()).add(req);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Toast.makeText(DataMahasiswaActivity.this, id, Toast.LENGTH_SHORT).show();
        if (id == android.R.id.home) {
            //Toast.makeText(DataMahasiswaActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
            return true;
        }
        if (id == R.id.action_exit) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(AppVar.NAMA_SHARED_PENGGUNA, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();

                        //Starting login activity
                        Intent intent = new Intent(ResetActivity.this, MainActivity.class);
                        startActivity(intent);
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

}
