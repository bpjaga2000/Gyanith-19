package com.barebrains.gyanith19;

import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class about extends AppCompatActivity {
    private Boolean show=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ((Button)findViewById(R.id.backabt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final FloatingActionButton main=(FloatingActionButton)findViewById(R.id.mainbut);
        final FloatingActionButton share=(FloatingActionButton)findViewById(R.id.sharebut);
        final FloatingActionButton directions=(FloatingActionButton)findViewById(R.id.direcbut);
        final FloatingActionButton feed=(FloatingActionButton)findViewById(R.id.feebut);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show){
                ObjectAnimator s=ObjectAnimator.ofFloat(share,"translationY",0f,-130f);
                s.setDuration(400);
                s.setInterpolator(new DecelerateInterpolator());
                s.start();
                ObjectAnimator d=ObjectAnimator.ofFloat(directions,"translationY",0f,-260f);
                d.setDuration(400);
                    d.setInterpolator(new DecelerateInterpolator());
                    d.start();
                ObjectAnimator f=ObjectAnimator.ofFloat(feed,"translationY",0f,-390f);
                f.setDuration(400);
                    f.setInterpolator(new DecelerateInterpolator());

                    f.start();
                    ObjectAnimator m=ObjectAnimator.ofFloat(main,"rotation",0f,-45f);
                    m.setDuration(400);
                    m.setInterpolator(new DecelerateInterpolator());
                    m.start();
                    show=!show;
            }else {

                    ObjectAnimator s=ObjectAnimator.ofFloat(share,"translationY",-130f,0f);
                    s.setDuration(400);
                    s.setInterpolator(new DecelerateInterpolator());
                    s.start();
                    ObjectAnimator d=ObjectAnimator.ofFloat(directions,"translationY",-260f,0f);
                    d.setDuration(400);
                    d.setInterpolator(new DecelerateInterpolator());
                    d.start();
                    ObjectAnimator f=ObjectAnimator.ofFloat(feed,"translationY",-390f,0f);
                    f.setDuration(400);
                    f.setInterpolator(new DecelerateInterpolator());

                    f.start();
                    ObjectAnimator m=ObjectAnimator.ofFloat(main,"rotation",-45f,0f);
                    m.setDuration(400);
                    m.setInterpolator(new DecelerateInterpolator());
                    m.start();
                    show=!show;

                }
            }

        });




    }
}
