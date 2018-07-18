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
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TambahNamaKocokActivity extends AppCompatActivity {
    EditText namagroup, idgroup6;
    private ProgressDialog pDialog;
    private Context context;
    int success;
    private static final String TAG_SUCCESS = "success";
    public String nmkas1, idakun, iduser;
    String ID_GROUP_ARISAN, ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_nama_kocok);
        context = TambahNamaKocokActivity.this;
        pDialog = new ProgressDialog(context, R.style.ProgressBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        namagroup = (EditText) findViewById (R.id.txtnamakocokan);
        idgroup6 = (EditText) findViewById (R.id.txtidkocoknama1);

        InputFilter[] Textfilters = new InputFilter[1];
        Textfilters[0] = new InputFilter(){
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

                    char[] acceptedChars = new char[]{'0','1','2','3','4','5','6','7','8','9',' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','.'};

                    for (int index = start; index < end; index++) {
                        if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                            return "";
                        }
                    }
                }
                return null;
            }

        };
        namagroup.setFilters(Textfilters);

        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_KOCOKAN, Context.MODE_PRIVATE);
        idgroup6.setText(sharedPreferences.getString(AppVar.ID_KOCOKAN_SHARED_PREF2, ""));
        namagroup.setText(sharedPreferences.getString(AppVar.NAMA_KOCOKAN_SHARED_PREF2, ""));

        AppCompatButton tambah = (AppCompatButton) findViewById(R.id.buattmbh);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    simpan_update();
                    namagroup.setText("");
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void simpan_update() {
//Getting values from edit texts
        nmkas1 = namagroup.getText().toString();
        idakun = idgroup6.getText().toString();

        //SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //iduser = sharedPreferences.getString(AppVar.IDUSER_SHARED_PREF, "");

        SharedPreferences sharedPreferences2 = getSharedPreferences(AppVar.SHARED_PREF_BARCODE3, Context.MODE_PRIVATE);
        ID_GROUP_ARISAN = sharedPreferences2.getString(AppVar.ID_GROUP_SHARED_PREF3,"");

        SharedPreferences sharedPreferences3 = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ID_USER = sharedPreferences3.getString(AppVar.IDUSER_SHARED_PREF,"");

        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Saving Process...");
        pDialog.setIndeterminate(true);
        pDialog.setCanceledOnTouchOutside(false);
        showDialog();
//Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.KOCOKAN_URL,
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
                                SweetAlertDialog pDialog = new SweetAlertDialog(TambahNamaKocokActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                                SweetAlertDialog pDialog1 = new SweetAlertDialog(TambahNamaKocokActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                params.put(AppVar.Key_NamaKocokan, nmkas1);
                params.put(AppVar.Key_id_kocokan, idakun);
                params.put(AppVar.Key_id_group_arisan, ID_GROUP_ARISAN);
                params.put(AppVar.Key_id_user_k, ID_USER);
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
                        Intent intent = new Intent(TambahNamaKocokActivity.this, MainActivity.class);
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
