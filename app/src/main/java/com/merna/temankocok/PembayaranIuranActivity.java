package com.merna.temankocok;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PembayaranIuranActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;
    int success;
    private static final String TAG_SUCCESS = "success";
    EditText namakocokantx1, txtidbayar1, txtidkocokan1, txtharus1, txtnominal1;
    TextView txtviepicker11;
    String nmkocok;
    Spinner cmjenistransaksi1;
    String namakocokantx1str, txtidbayar1str, txtidkocokan1str, txtharus1str, txtnominal1str, jeniskat;
    String txtviepicker11str, ID_USER, ID_GROUP_ARISAN, ID_PERIODE;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_iuran);
        context = PembayaranIuranActivity.this;
        pDialog = new ProgressDialog(context, R.style.ProgressBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namakocokantx1 = (EditText) findViewById (R.id.namakocokantx);
        txtidbayar1 = (EditText) findViewById (R.id.txtidbayar);
        txtidkocokan1 = (EditText) findViewById (R.id.txtidkocokan);
        txtviepicker11 = (TextView) findViewById (R.id.txtviepicker1);
        txtharus1 = (EditText) findViewById (R.id.txtharus);
        txtnominal1 = (EditText) findViewById (R.id.txtnominal);
        cmjenistransaksi1 = (Spinner) findViewById(R.id.cmjenistransaksi);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        txtviepicker11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nmkocok = bundle.getString("namakocokan");
        String nilai1 = bundle.getString("nilai1");
        namakocokantx1.setText(nmkocok);
        txtharus1.setText(nilai1);

        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_PEMBAYARAN, Context.MODE_PRIVATE);
        txtidbayar1.setText(sharedPreferences.getString(AppVar.ID_PEMBAYARAN, ""));
        txtidkocokan1.setText(bundle.getString("id_kocokan"));
        txtviepicker11.setText(sharedPreferences.getString(AppVar.TGLBAYAR, ""));
        //txtharus1.setText(sharedPreferences.getString(AppVar.HARUSBAYAR, ""));
        txtnominal1.setText(sharedPreferences.getString(AppVar.BAYAR, ""));

        String statusname3 = sharedPreferences.getString(AppVar.jenisBAYAR, "");
        String statusname1 = "Tunai";
        String statusname2 = "Transfer";
        //Toast.makeText(contextx, "tess " + statusname3, Toast.LENGTH_LONG).show();

        if (statusname3.equals(statusname1)) {
            cmjenistransaksi1.setSelection(0);
        }
        else if (statusname3.equals(statusname2)) {
            cmjenistransaksi1.setSelection(1);
        }
        //txtnominal1.setText(sharedPreferences.getString(AppVar.BAYAR, ""));

        Button tambah = (Button) findViewById(R.id.button9);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    simpan_update();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca",
        //        Toast.LENGTH_SHORT)
        //        .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,R.style.EditDate,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        txtviepicker11.setText(new StringBuilder().append(year).append("-").append(month).append("-")
                .append(day));
    }

    private void simpan_update() {
//Getting values from edit texts
        namakocokantx1str= namakocokantx1.getText().toString();
        txtidbayar1str= txtidbayar1.getText().toString();
        txtidkocokan1str =txtidkocokan1.getText().toString();
        txtharus1str= txtharus1.getText().toString();
        txtnominal1str=txtnominal1.getText().toString();
        txtviepicker11str = txtviepicker11.getText().toString();
        jeniskat = cmjenistransaksi1.getSelectedItem().toString();

        SharedPreferences sharedPreferences2 = getSharedPreferences(AppVar.SHARED_PREF_PILIHPERIODE, Context.MODE_PRIVATE);
        ID_GROUP_ARISAN = sharedPreferences2.getString(AppVar.ID_GROUPPM_SHARED,"");
        ID_PERIODE = sharedPreferences2.getString(AppVar.ID_PERIODE_SHARED,"");

        SharedPreferences sharedPreferences3 = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ID_USER = sharedPreferences3.getString(AppVar.IDUSER_SHARED_PREF,"");

        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Saving Process...");
        pDialog.setIndeterminate(true);
        pDialog.setCanceledOnTouchOutside(false);
        showDialog();
//Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.BAYAR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        //Toast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                        //cal json
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                            // Cek error node pada json
                            if (success == 1) {
                                //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                hideDialog();
                                SweetAlertDialog pDialog = new SweetAlertDialog(PembayaranIuranActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitleText("Informasi");
                                pDialog.setContentText("Simpan Data Berhasil!");
                                pDialog.setCancelable(false);
                                pDialog.setConfirmText("OK");
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                        finish();
                                    }
                                });
                                pDialog.show();


                            } else {
                                hideDialog();
                                SweetAlertDialog pDialog1 = new SweetAlertDialog(PembayaranIuranActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog1.setTitleText("Informasi");
                                pDialog1.setContentText("Simpan Data Gagal");
                                pDialog1.setCancelable(false);
                                pDialog1.setConfirmText("OK");
                                pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                        finish();
                                    }
                                });
                                pDialog1.show();
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
                params.put(AppVar.Key_id_bayar, txtidbayar1str);
                params.put(AppVar.Key_id_periode_byr, ID_PERIODE);
                params.put(AppVar.Key_idgrouparisan_byr, ID_GROUP_ARISAN);
                params.put(AppVar.Key_tgl_bayar, txtviepicker11str);
                params.put(AppVar.Key_bayar, txtnominal1str);
                params.put(AppVar.Key_id_user_byr, ID_USER);
                params.put(AppVar.Key_harus_pr, txtharus1str);
                params.put(AppVar.Key_jenisbayar, jeniskat);
                params.put(AppVar.Key_idkocok_byr, txtidkocokan1str);
//returning parameter
                return params;
            }
        };
//Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
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
                        Intent intent = new Intent(PembayaranIuranActivity.this, MainActivity.class);
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
