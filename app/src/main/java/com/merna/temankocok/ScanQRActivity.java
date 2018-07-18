package com.merna.temankocok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;
import static com.merna.temankocok.R.raw.success;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 1;
    private String URL_UPDATE = "http://markaskerja.000webhostapp.com/temankocok/Tambahgroup/gabunggroup?";
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan_qr);
        mScannerView = new ZXingScannerView(getApplicationContext());
        //mScannerView.startCamera();
        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }
        }
        mScannerView.setResultHandler(this);

    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(ScanQRActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //mScannerView.setResultHandler(this);
        //mScannerView.startCamera();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        /*Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        mScannerView.resumeCameraPreview(this);*/
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        //update
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String url = URL_UPDATE + "id_user=" + sharedPreferences.getString(AppVar.IDUSER_SHARED_PREF,"") + "&id_group_arisan=" + rawResult.getText();
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
                                final SweetAlertDialog pDialog = new SweetAlertDialog(ScanQRActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitleText("Informasi");
                                pDialog.setContentText("Gabung Ke group Berhasil!");
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
                            else if (success == 2) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(ScanQRActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitleText("Informasi");
                                pDialog.setContentText("Anda Sudah Bergabung");
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
                                final SweetAlertDialog pDialog = new SweetAlertDialog(ScanQRActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitleText("Informasi");
                                pDialog.setContentText("Gagal Gabung Ke group. Coba Lagi!");
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

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mScannerView.resumeCameraPreview(ScanQRActivity.this);
            }
        });*/
        /*builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                startActivity(browserIntent);
            }
        });*/
        //builder.setMessage(rawResult.getText());

        //AlertDialog alert1 = builder.create();
        //alert1.show();
    }
}
