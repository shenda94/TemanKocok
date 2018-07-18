package com.merna.temankocok;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
//import com.merna.luckywheel.sqlite.MyDatabase;
//import com.merna.luckywheel.sqlite.Poses;
//import com.merna.luckywheel.sqlite.SQLiteAssetHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;
import rubikstudio.library.PielView;

public class MainActivity extends AppCompatActivity implements AccelerometerListener {
    List<LuckyItem> data = new ArrayList<>();
    //MyDatabase myDatabase;
    //private ArrayList<Poses> DaftarArrayList;
    String gabung;
    protected Handler handler;
    private ProgressDialog pDialog;
    String id_user;
    LuckyWheelView luckyWheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //setContentView(R.layout.content_kocok);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            pDialog = new ProgressDialog(this, R.style.ProgressBar);
            handler = new Handler();

            luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);

            SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_BARCODE3, Context.MODE_PRIVATE);
            id_user  = sharedPreferences.getString(AppVar.ID_GROUP_SHARED_PREF3, "");
            //
            gabung = AppVar.DATA_URL2 + "id_group_arisan=" + id_user;
            //Toast.makeText(MainActivity.this, "Error: stu7 " + gabung, Toast.LENGTH_LONG).show();

            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Haraf Tunggu...");
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            showDialog();

            /*LuckyItem luckyItem1 = new LuckyItem();
            luckyItem1.text = "Rena";
            luckyItem1.icon = R.drawable.test1;
            luckyItem1.color = 0xffFFF3E0;
            data.add(luckyItem1);

            LuckyItem luckyItem2 = new LuckyItem();
            luckyItem2.text = "Diana";
            luckyItem2.icon = R.drawable.test2;
            luckyItem2.color = 0xffFFE0B2;
            data.add(luckyItem2);

            LuckyItem luckyItem3 = new LuckyItem();
            luckyItem3.text = "Ais";
            luckyItem3.icon = R.drawable.test3;
            luckyItem3.color = 0xffFFCC80;
            data.add(luckyItem3);

            //////////////////
            LuckyItem luckyItem4 = new LuckyItem();
            luckyItem4.text = "Nisa";
            luckyItem4.icon = R.drawable.test4;
            luckyItem4.color = 0xffFFF3E0;
            data.add(luckyItem4);

            LuckyItem luckyItem5 = new LuckyItem();
            luckyItem5.text = "Eka";
            luckyItem5.icon = R.drawable.test5;
            luckyItem5.color = 0xffFFE0B2;
            data.add(luckyItem5);

            LuckyItem luckyItem6 = new LuckyItem();
            luckyItem6.text = "Agung";
            luckyItem6.icon = R.drawable.test6;
            luckyItem6.color = 0xffFFCC80;
            data.add(luckyItem6);
            //////////////////

            //////////////////////
            LuckyItem luckyItem7 = new LuckyItem();
            luckyItem7.text = "Yoda";
            luckyItem7.icon = R.drawable.test7;
            luckyItem7.color = 0xffFFF3E0;
            data.add(luckyItem7);

            LuckyItem luckyItem8 = new LuckyItem();
            luckyItem8.text = "Arif";
            luckyItem8.icon = R.drawable.test8;
            luckyItem8.color = 0xffFFE0B2;
            data.add(luckyItem8);


            LuckyItem luckyItem9 = new LuckyItem();
            luckyItem9.text = "Anis";
            luckyItem9.icon = R.drawable.test9;
            luckyItem9.color = 0xffFFCC80;
            data.add(luckyItem9);
            ////////////////////////

            LuckyItem luckyItem10 = new LuckyItem();
            luckyItem10.text = "Lutfi";
            luckyItem10.icon = R.drawable.test10;
            luckyItem10.color = 0xffFFE0B2;
            data.add(luckyItem10);

            LuckyItem luckyItem11 = new LuckyItem();
            luckyItem11.text = "Marquez";
            luckyItem11.icon = R.drawable.test10;
            luckyItem11.color = 0xffFFE0B2;
            data.add(luckyItem11);

            LuckyItem luckyItem12 = new LuckyItem();
            luckyItem12.text = "Rossi";
            luckyItem12.icon = R.drawable.test10;
            luckyItem12.color = 0xffFFE0B2;
            data.add(luckyItem12);*/

            //myDatabase = new MyDatabase(MainActivity.this);
            //DaftarArrayList = myDatabase.getAnggotax();

            /*if (DaftarArrayList.size() > 0) {
                for(int i=0;i<DaftarArrayList.size();i++){
                    LuckyItem luckyItem1 = new LuckyItem();
                    luckyItem1.text = DaftarArrayList.get(i).nm_kocok;
                    luckyItem1.color = 0xffFFF3E0;
                    luckyItem1.icon = R.drawable.ic_action_name;

                    if ( i == 0) {
                        luckyItem1.color = 0xffFFF3E0;
                        luckyItem1.icon = R.drawable.ic_action_name;
                    }
                    else {
                        if (i%2==1){
                            luckyItem1.color = 0xffFFE0B2;
                            luckyItem1.icon = R.drawable.ic_action_name;
                        }
                        else if (i%2==0) {
                            luckyItem1.color = 0xffFFCC80;
                            luckyItem1.icon = R.drawable.ic_action_name;
                        }
                    }

                    data.add(luckyItem1);
                }
            }
            else {
                LuckyItem luckyItem1 = new LuckyItem();
                luckyItem1.text = "None";
                luckyItem1.icon = R.drawable.ic_action_name;
                luckyItem1.color = 0xffFFF3E0;
                data.add(luckyItem1);*/

                /*LuckyItem luckyItem2 = new LuckyItem();
                luckyItem2.text = "Diana";
                luckyItem2.icon = R.drawable.test2;
                luckyItem2.color = 0xffFFE0B2;
                data.add(luckyItem2);

                LuckyItem luckyItem3 = new LuckyItem();
                luckyItem3.text = "Ais";
                luckyItem3.icon = R.drawable.test3;
                luckyItem3.color = 0xffFFCC80;
                data.add(luckyItem3);

                //////////////////
                LuckyItem luckyItem4 = new LuckyItem();
                luckyItem4.text = "Nisa";
                luckyItem4.icon = R.drawable.test4;
                luckyItem4.color = 0xffFFF3E0;
                data.add(luckyItem4);

                LuckyItem luckyItem5 = new LuckyItem();
                luckyItem5.text = "Eka";
                luckyItem5.icon = R.drawable.test5;
                luckyItem5.color = 0xffFFE0B2;
                data.add(luckyItem5);

                LuckyItem luckyItem6 = new LuckyItem();
                luckyItem6.text = "Agung";
                luckyItem6.icon = R.drawable.test6;
                luckyItem6.color = 0xffFFCC80;
                data.add(luckyItem6);
                //////////////////

                //////////////////////
                LuckyItem luckyItem7 = new LuckyItem();
                luckyItem7.text = "Yoda";
                luckyItem7.icon = R.drawable.test7;
                luckyItem7.color = 0xffFFF3E0;
                data.add(luckyItem7);

                LuckyItem luckyItem8 = new LuckyItem();
                luckyItem8.text = "Arif";
                luckyItem8.icon = R.drawable.test8;
                luckyItem8.color = 0xffFFE0B2;
                data.add(luckyItem8);


                LuckyItem luckyItem9 = new LuckyItem();
                luckyItem9.text = "Anis";
                luckyItem9.icon = R.drawable.test9;
                luckyItem9.color = 0xffFFCC80;
                data.add(luckyItem9);
                ////////////////////////

                LuckyItem luckyItem10 = new LuckyItem();
                luckyItem10.text = "Lutfi";
                luckyItem10.icon = R.drawable.test10;
                luckyItem10.color = 0xffFFE0B2;
                data.add(luckyItem10);

                LuckyItem luckyItem11 = new LuckyItem();
                luckyItem11.text = "Marquez";
                luckyItem11.icon = R.drawable.test10;
                luckyItem11.color = 0xffFFE0B2;
                data.add(luckyItem11);

                LuckyItem luckyItem12 = new LuckyItem();
                luckyItem12.text = "Rossi";
                luckyItem12.icon = R.drawable.test10;
                luckyItem12.color = 0xffFFE0B2;
                data.add(luckyItem12);*/
            //}

            /////////////////////

            //luckyWheelView.setData(data);
            //luckyWheelView.setRound(getRandomRound());


            //webserice
            JsonArrayRequest reqsubkategori = new JsonArrayRequest(gabung,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d(TAG, response.toString());

                            try {
                                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                                hideDialog();

                                if (response.length() > 0) {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject person = (JSONObject) response
                                                .get(i);

                                        if (person.getInt("statuspesan") == 1){
                                            LuckyItem luckyItem1 = new LuckyItem();
                                            luckyItem1.text = person.getString("nm_kocokan");
                                            luckyItem1.color = 0xffFFF3E0;
                                            luckyItem1.icon = R.drawable.ic_action_name;

                                            if ( i == 0) {
                                                luckyItem1.color = 0xffFFF3E0;
                                                luckyItem1.icon = R.drawable.ic_action_name;
                                            }
                                            else {
                                                if (i%2==1){
                                                    luckyItem1.color = 0xffFFE0B2;
                                                    luckyItem1.icon = R.drawable.ic_action_name;
                                                }
                                                else if (i%2==0) {
                                                    luckyItem1.color = 0xffFFCC80;
                                                    luckyItem1.icon = R.drawable.ic_action_name;
                                                }
                                            }

                                            data.add(luckyItem1);
                                        }
                                    }
                                }
                                else {
                                    LuckyItem luckyItem1 = new LuckyItem();
                                    luckyItem1.text = "None";
                                    luckyItem1.icon = R.drawable.ic_action_name;
                                    luckyItem1.color = 0xffFFF3E0;
                                    data.add(luckyItem1);
                                }

                                luckyWheelView.setData(data);
                                luckyWheelView.setRound(getRandomRound());

                            } catch (JSONException e) {
                                hideDialog();
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this,
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideDialog();
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(MainActivity.this,
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Adding request to request queue
            //AppController.getInstance().addToRequestQueue(req);
            Volley.newRequestQueue(this).add(reqsubkategori);

            /////////////////////


        /*luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        luckyWheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/


            findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
                    int index = getRandomIndex();
                    luckyWheelView.startLuckyWheelWithTargetIndex(index);
                }
            });

            luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
                @Override
                public void LuckyRoundItemSelected(int index) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(data.get(index - 1).text), Toast.LENGTH_SHORT).show();
                    /*SQLiteAssetHelper sqLiteAssetHelper = new SQLiteAssetHelper(MainActivity.this, "kocokarisan.db", null, 2);
                    SQLiteDatabase sqliteDatabase = sqLiteAssetHelper.getReadableDatabase();
                    // ContentValues class is used to store a set of values that the ContentResolver can process.
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("status_kocok", "1");
                    String id_lokerx = data.get(index - 1).text;
                    //Toast.makeText(getApplicationContext(), id_lokerx, Toast.LENGTH_SHORT).show();
                    long affectedColumnId = 0;

                    try {
                        affectedColumnId = sqliteDatabase.update("t_anggotaarisan",contentValues," nm_kocok='"+id_lokerx + "'",null);
                        //affectedColumnId = sqliteDatabase.update("t_anggotaarisan","nm_kocok=?",new String[]{id_lokerx});
                        sqliteDatabase.close();
                    }
                    catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                    if (affectedColumnId == 1){
                        SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AppVar.NAMA_SHARED_PENGGUNA, id_lokerx);
                        editor.commit();

                        Toast.makeText(MainActivity.this, "Selamat Anda Berhasil", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Gagal. coba ulang kembali!", Toast.LENGTH_LONG).show();
                    }*/

                    SharedPreferences sharedPreferences2 = getSharedPreferences(AppVar.SHARED_PREF_PILIHPERIODE, Context.MODE_PRIVATE);
                    String hasil = sharedPreferences2.getString(AppVar.ID_GROUPPM_SHARED,"");
                    String hasil2 = sharedPreferences2.getString(AppVar.ID_PERIODE_SHARED,"");

                    String id_lokerx = data.get(index - 1).text;

                    //Creating a shared preference
                    SharedPreferences sharedPreferences = getSharedPreferences(AppVar.SHARED_PREF_PEMENANG, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putString(AppVar.ID_GROUPPM_SHARED_PREF2, id_user);
                    editor.putString(AppVar.NAMA_PEMENANG_SHARED_PREF2, id_lokerx);
                    editor.putString(AppVar.idperiode_SHARED_PREF2, hasil2);
                    editor.commit();

                    Intent menutentang = new Intent(MainActivity.this, PemenangAct.class);
                    startActivity(menutentang);
                    MainActivity.this.finish();
                    //Toast.makeText(getApplicationContext(), String.valueOf(data.get(index - 1).text) + String.valueOf(index - 1) + String.valueOf(index), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(MainActivity.this,
                    e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {
        int index = getRandomIndex();
        luckyWheelView.startLuckyWheelWithTargetIndex(index);
        //Toast.makeText(this, "Motion detected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();

//Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

//Start Accelerometer Listening
            AccelerometerManager.stopListening();

            //Toast.makeText(this, "onStop Accelerometer Stopped", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();

            //Toast.makeText(this, "onDestroy Accelerometer Stopped", Toast.LENGTH_SHORT).show();
        }
    }


    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size()) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
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
                        MainActivity.this.finish();
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
