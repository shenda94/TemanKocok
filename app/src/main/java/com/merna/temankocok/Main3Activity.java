package com.merna.temankocok;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarid);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tv = (TextView) findViewById(R.id.tv);
        iv2 = (ImageView) findViewById(R.id.iv);



        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        //tv.startAnimation(myanim);
        iv2.startAnimation(myanim);
        final Intent tes = new Intent(this, HalamanUtamaActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(tes);
                    finish();
                }
            }
        };

        timer.start();


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {


            return true;
        }


        return super.onOptionsItemSelected(item);
    }*/
}
