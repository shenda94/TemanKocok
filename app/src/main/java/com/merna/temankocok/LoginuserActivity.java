package com.merna.temankocok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class LoginuserActivity extends AppCompatActivity {
    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;
    private EditText email;
    private EditText password;
    int success;
    String pesan;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_message = "message";
    public static final String TAG_ID       = "ID_User";
    public static String id_pengguna;
    private boolean loggedIn = false;
    private String codepattern, Pincode;
    String id_user, imeiandroid, no_hp1, kode_barcode, nm_lengkap1, alamat_rumah, idgroup, id_group_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);
        context = LoginuserActivity.this;

        pDialog = new ProgressDialog(context);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        buttonLogin = (AppCompatButton) findViewById(R.id.btnbaca);
        email = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = email.getText().toString().trim();
                //Toast.makeText(context, "tess " + nama, Toast.LENGTH_LONG).show();
                //login();
                SharedPreferences sharedPreferences = LoginuserActivity.this.getSharedPreferences(AppVar.SHARED_PREF_NAMAUSER, Context.MODE_PRIVATE);
                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppVar.NAMAUSER_HRF, nama);
                editor.commit();

                startActivity(new Intent(LoginuserActivity.this,LoginActivity.class));
                finish();
            }
        });


    }

    private void login() {
//Getting values from edit texts
        final String nama = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Login Process...");
        pDialog.setIndeterminate(true);
        pDialog.setCanceledOnTouchOutside(false);
        showDialog();
//Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        //Toast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                        //cal json
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            //Toast.makeText(context, "tess " + success, Toast.LENGTH_LONG).show();
                            // Cek error node pada json
                            if (success == 1) {
                                id_user = jObj.getString("id_user");
                                nm_lengkap1 = jObj.getString("nama_user");
                                //kode_barcode = jObj.getString("kode_barcode");
                                no_hp1 = jObj.getString("no_hp");
                                alamat_rumah= jObj.getString("alamat_rumah");
                                imeiandroid = jObj.getString("kode_imei");
                                //idgroup = jObj.getString("id_group_arisan");
                                id_group_user = jObj.getString("id_group_user");

                                //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                hideDialog();

                                SharedPreferences sharedPreferences = LoginuserActivity.this.getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, true);

                                //Adding values to editor
                                editor.putString(AppVar.IDUSER_SHARED_PREF, id_user);

                                //Adding values to editor
                                editor.putString(AppVar.HPNO_SHARED_PREF, no_hp1);

                                //Adding values to editor
                                editor.putString(AppVar.imeihp1_SHARED_PREF, imeiandroid);

                                //Adding values to editor
                                editor.putString(AppVar.alamat1_SHARED_PREF, alamat_rumah);

                                //Adding values to editor
                                editor.putString(AppVar.IDGROUP_SHARED_PREF, idgroup);

                                editor.commit();

                                //
                                startActivity(new Intent(LoginuserActivity.this,Main2Activity.class));
                                finish();

                            } else {
                                hideDialog();

                                SweetAlertDialog pDialog1 = new SweetAlertDialog(LoginuserActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog1.setTitleText("Informasi");
                                pDialog1.setContentText("Login Gagal. Harap cek password anda kembali!");
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
                params.put(AppVar.log_pattern, codepattern);
                params.put(AppVar.log_imei, "");
                params.put(AppVar.user_pattern, nama);
//returning parameter
                return params;
            }
        };
//Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
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
