package com.merna.temankocok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main2Activity extends AppCompatActivity {
    MediaPlayer mp;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private ProgressDialog pDialog;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pDialog = new ProgressDialog(Main2Activity.this, R.style.ProgressBar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mp = MediaPlayer.create(this, R.raw.success);
        mp.setLooping(false);
        mp.start();

        final SweetAlertDialog pDialog1 = new SweetAlertDialog(Main2Activity.this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog1.setTitleText("Informasi");
        pDialog1.setContentText("Selamat Datang, " + sharedPreferences.getString(AppVar.NAMA_SHARED_PENGGUNA, "") + " !");
        pDialog1.setCancelable(false);
        pDialog1.setConfirmText("OK");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                // reuse previous dialog instance
                //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                pDialog1.hide();
                mp.stop();

            }
        });
        pDialog1.show();

        ImageView img1 = (ImageView) findViewById(R.id.tentangkami);
        img1.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent menutentang = new Intent(Main2Activity.this, TentangActivity.class);
               startActivity(menutentang);
               //finish();
           }
        });

        CardView cv1 = (CardView) findViewById(R.id.tentangkamiCV);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menutentang = new Intent(Main2Activity.this, TentangActivity.class);
                startActivity(menutentang);
                //finish();
            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.addanggota);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuanggota = new Intent(Main2Activity.this, BuatGroupActivity.class);
                startActivity(menuanggota);
                //finish();
            }
        });

        CardView cv3 = (CardView) findViewById(R.id.bankcardId);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuanggota = new Intent(Main2Activity.this, BuatGroupActivity.class);
                startActivity(menuanggota);
                //finish();
            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.listanggota);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send all parameter to global variable, that will show to another activity
                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "1");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView cv4 = (CardView) findViewById(R.id.listanggotaCV);
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send all parameter to global variable, that will show to another activity
                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "1");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        ImageView img31 = (ImageView) findViewById(R.id.kocokannama);
        img31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "2");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView CV2 = (CardView) findViewById(R.id.kocokannamaCV);
        CV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "2");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        ImageView img32 = (ImageView) findViewById(R.id.resetkocokanimg);
        img32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "3");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView CV1 = (CardView) findViewById(R.id.resetkocokan);
        CV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "3");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.kocokbtn);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "4");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView cv5 = (CardView) findViewById(R.id.kocokbtnCV);
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "4");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });


        //periode arisan
        ImageView img6 = (ImageView) findViewById(R.id.periodeimg);
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "5");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView cv6 = (CardView) findViewById(R.id.periode);
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "5");
                editor.commit();

                Intent menulist = new Intent(Main2Activity.this, DafGroupActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        ImageView img7 = (ImageView) findViewById(R.id.addbayar);
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                //SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                //SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                //editor.putString(AppVar.MENU_SHARED_PREF3, "6");
                //editor.commit();

                Intent menulist = new Intent(Main2Activity.this, UtamaBayarActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

        CardView cv7 = (CardView) findViewById(R.id.pembayaran);
        cv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a shared preference
                //SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                //SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                //editor.putString(AppVar.MENU_SHARED_PREF3, "6");
                //editor.commit();

                Intent menulist = new Intent(Main2Activity.this, UtamaBayarActivity.class);
                startActivity(menulist);
                //finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //Toast.makeText(this, "Rivchat version 1.0", Toast.LENGTH_LONG).show();
            logout();
            return true;
        }

        if (id == R.id.action_infouser) {
            custom_dialog1();
            return true;
        }

        if (id == R.id.action_infoapp) {
            startActivity(new Intent(Main2Activity.this,TentangAplikasiActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void custom_dialog1() {
        dialog = new AlertDialog.Builder(Main2Activity.this);
        //inflater = LayoutInflater.from(contextx);
        LayoutInflater inflater = (LayoutInflater)Main2Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.custom_dialog1, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        //dialog.setIcon(R.drawable.ic_shapes);
        //dialog.setTitle("Action");
        TextView test  = (TextView) dialogView.findViewById(R.id.text_dialog);
        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        test.setText("username : " + sharedPreferences.getString(AppVar.NAMA_SHARED_PENGGUNA, ""));
        Button btn_dialog1  = (Button) dialogView.findViewById(R.id.btn_dialog);

        btn_dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                refreshtoken();
            }
        });

        dialog.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //dialog.show();
        AlertDialog alertDialog = dialog.create();
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

    public void refreshtoken() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String urll = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/refreshtoken?id_usertoken="+ sharedPreferences.getString(AppVar.IDUSER_SHARED_PREF, "");
        token = FirebaseInstanceId.getInstance().getToken();

        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Refresh...");
        pDialog.setIndeterminate(true);
        pDialog.setCanceledOnTouchOutside(false);
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        //Toast.makeText(context, "tess " + response, Toast.LENGTH_LONG).show();
                        //cal json
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status_anggota = jObj.getString("success");
                            //success = jObj.getInt(TAG_SUCCESS);

                            //Toast.makeText(context, "tess " + jObj.getString("message"), Toast.LENGTH_LONG).show();
                            // Cek error node pada json
                            if (status_anggota.equals("1")) {
                                //Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
                                hideDialog();
                                final SweetAlertDialog pDialog1 = new SweetAlertDialog(Main2Activity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog1.setTitleText("Informasi");
                                pDialog1.setContentText("Berhasil Refresh Token");
                                pDialog1.setCancelable(false);
                                pDialog1.setConfirmText("OK");
                                pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                        pDialog1.hide();
                                        //finish();
                                    }
                                });
                                pDialog1.show();

                            } else {
                                hideDialog();
                                final SweetAlertDialog pDialog1 = new SweetAlertDialog(Main2Activity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog1.setTitleText("Informasi");
                                pDialog1.setContentText("Gagal Refresh Token");
                                pDialog1.setCancelable(false);
                                pDialog1.setConfirmText("OK");
                                pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                                        pDialog1.hide();
                                        //finish();
                                    }
                                });
                                pDialog1.show();
                                //Displaying an error message on toast
                                //Toast.makeText(context, "Register Gagal", Toast.LENGTH_LONG).show();
                                //Toast.makeText(MainActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            Toast.makeText(Main2Activity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Main2Activity.this, "The server unreachable", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//Adding parameters to request
                params.put("token", token);
//returning parameter
                return params;
            }
        };

        Volley.newRequestQueue(Main2Activity.this).add(stringRequest);
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you sure you want to quit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Main2Activity.this.finish();
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
