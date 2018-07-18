package com.merna.temankocok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import com.android.volley.toolbox.Volley;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {
    private Context context;
    private customfonts.MyEditText nm_lengkap;
    private customfonts.MyEditText no_hp;
    private customfonts.MyEditText alamat;
    private AppCompatButton buttonregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.induk_register);
        context = RegisterActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nm_lengkap = (customfonts.MyEditText)findViewById(R.id.txtfullname);
        no_hp = (customfonts.MyEditText)findViewById(R.id.txtnohp);
        alamat = (customfonts.MyEditText)findViewById(R.id.txtnik);
        buttonregister = (AppCompatButton) findViewById(R.id.register);
        InputFilter[] Textfilters = new InputFilter[1];
        Textfilters[0] = new InputFilter(){
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

                    char[] acceptedChars = new char[]{' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','.'};

                    for (int index = start; index < end; index++) {
                        if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                            return "";
                        }
                    }
                }
                return null;
            }

        };
        nm_lengkap.setFilters(Textfilters);

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving
                if (TextUtils.isEmpty(nm_lengkap.getText())) {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Informasi");
                    pDialog.setContentText("Nama Lengkap Harus Diisi!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmText("OK");
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance
                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                            pDialog.hide();
                            nm_lengkap.requestFocus();
                        }
                    });
                    pDialog.show();

                }
                else if (TextUtils.isEmpty(no_hp.getText())) {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Informasi");
                    pDialog.setContentText("No Handphone Harus Diisi!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmText("OK");
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance
                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                            pDialog.hide();
                            no_hp.requestFocus();
                        }
                    });
                    pDialog.show();

                }
                else if (TextUtils.isEmpty(alamat.getText())) {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Informasi");
                    pDialog.setContentText("Alamat Harus Diisi!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmText("OK");
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance
                            //startActivity(new Intent(TambahAkunActivity.this,KasActivity.class));
                            pDialog.hide();
                            alamat.requestFocus();
                        }
                    });
                    pDialog.show();
                }
                else {
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(AppVar.SHARED_PREF_REGISTER, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putString(AppVar.NMlengkap_SHARED_PREF, nm_lengkap.getText().toString());
                    editor.putString(AppVar.ALAMAT_SHARED_PREF, alamat.getText().toString());
                    editor.putString(AppVar.NOHP_SHARED_PREF, no_hp.getText().toString());
                    editor.commit();

                    startActivity(new Intent(RegisterActivity.this,KodePatternActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(tentangkamiActivity.this,
        //        "Your Message", Toast.LENGTH_LONG).show();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
