package com.merna.temankocok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UtamaBayarActivity extends AppCompatActivity {
    private Button btnbuat;
    private Button btnscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_bayar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnbuat = (Button) findViewById(R.id.btngroupbaru);
        btnscan = (Button) findViewById(R.id.btnscangroup);

        btnbuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "7");
                editor.commit();

                SharedPreferences sharedPreferences1 = getSharedPreferences(AppVar.SHARED_PREF_PILIHUSRPERIODE, Context.MODE_PRIVATE);
                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                //Adding values to editor
                editor1.putString(AppVar.ID_userPM_SHARED, "");
                editor1.commit();

                startActivity(new Intent(UtamaBayarActivity.this,DafGroupActivity.class));
            }
        });

        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating a shared preference
                SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_MENU3, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString(AppVar.MENU_SHARED_PREF3, "6");
                editor.commit();

                startActivity(new Intent(UtamaBayarActivity.this,DafGroupActivity.class));
                //startActivity(new Intent(HalamanUtamaActivity.this,LoginActivity.class));
            }
        });

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
                        UtamaBayarActivity.this.finish();
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
