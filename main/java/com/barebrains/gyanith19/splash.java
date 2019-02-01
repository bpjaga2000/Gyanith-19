package com.barebrains.gyanith19;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.activity_splash);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        ImageView l=(ImageView)findViewById(R.id.imageView4);


        registerReceiver(new notreceiver(),new IntentFilter("gyanith.notify"));
        //registerReceiver(new notreceiver(),new IntentFilter("BOOT_COMPLETED"));


       /* ObjectAnimator a=ObjectAnimator.ofFloat(l,"alpha",0,1);
        a.setDuration(500);
        a.setInterpolator(new AccelerateInterpolator());
        a.start();
        ObjectAnimator s=ObjectAnimator.ofFloat(l,"scaleX",2,1);
        s.setDuration(500);
        s.setInterpolator(new AccelerateInterpolator());
        s.start();
        ObjectAnimator s1=ObjectAnimator.ofFloat(l,"scaleY",2,1);
        s1.setDuration(500);
        s1.setInterpolator(new AccelerateInterpolator());
        s1.start();*/

        Timer t=new Timer();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(splash.this,MainActivity.class);

                startActivity(i);
                finish();


            }
        },2000);
    }
}
