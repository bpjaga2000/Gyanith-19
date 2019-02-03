package com.barebrains.gyanith19;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.animation.DecelerateInterpolator;


public class home extends Fragment {
    private long delay1=0;
    private long delay2=0;



    public home() {
        // Required empty public constructor
    }


    public static home newInstance() {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_home, container, false);


        ((CardView)root.findViewById(R.id.w)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Workshop");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.te)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Technical Events");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.nte)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Non Technical Events");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.ps)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Pro Shows");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.ud)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),about.class);
              //  i.putExtra("category","Unnamed");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.gl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Guest Lectures");
                startActivity(i);
            }
        });

        Intent n=new Intent("gyanith.notify");
        getContext().sendBroadcast(n);


        CardView w=(CardView)root.findViewById(R.id.w);
        CardView te=(CardView)root.findViewById(R.id.te);
        CardView nte=(CardView)root.findViewById(R.id.nte);
        nte.setAlpha(0);
        CardView ps=(CardView)root.findViewById(R.id.ps);
       ps.setAlpha(0);
        CardView gl=(CardView)root.findViewById(R.id.gl);
       gl.setAlpha(0);
        CardView un=(CardView)root.findViewById(R.id.ud);
        un.setAlpha(0);

        ObjectAnimator wa=ObjectAnimator.ofFloat(w,"translationX",-300f,0f);
        wa.setInterpolator(new DecelerateInterpolator());
        wa.setStartDelay(delay1);
        wa.setDuration(300);
        wa.start();
        delay1+=150;

        ObjectAnimator nta=ObjectAnimator.ofFloat(nte,"translationX",-300f,0f);
        nta.setInterpolator(new DecelerateInterpolator());
        nta.setStartDelay(delay1);
        nta.setDuration(300);
        nta.start();
        ObjectAnimator ntl=ObjectAnimator.ofFloat(nte,"alpha",0,1);
        ntl.setStartDelay(delay1);
        ntl.start();
        delay1+=150;

        ObjectAnimator ua=ObjectAnimator.ofFloat(un,"translationX",-300f,0f);
        ua.setInterpolator(new DecelerateInterpolator());
        ua.setStartDelay(delay1);
        ua.setDuration(300);
        ua.start();
        ObjectAnimator ul=ObjectAnimator.ofFloat(un,"alpha",0,1);
        ul.setStartDelay(delay1);
        ul.start();
        delay1+=150;

        ObjectAnimator ta=ObjectAnimator.ofFloat(te,"translationX",300f,0f);
        ta.setInterpolator(new DecelerateInterpolator());
        ta.setStartDelay(delay2);
        ta.setDuration(300);
        ta.start();
        delay2+=150;

        ObjectAnimator pa=ObjectAnimator.ofFloat(ps,"translationX",300f,0f);
        pa.setInterpolator(new DecelerateInterpolator());
        pa.setStartDelay(delay2);
        pa.setDuration(300);
        pa.start();
        ObjectAnimator pl=ObjectAnimator.ofFloat(ps,"alpha",0,1);
        pl.setStartDelay(delay2);
        pl.start();
        delay2+=150;

        ObjectAnimator ga=ObjectAnimator.ofFloat(gl,"translationX",300f,0f);
        ga.setInterpolator(new DecelerateInterpolator());
        ga.setStartDelay(delay2);
        ga.setDuration(300);
        ga.start();
        ObjectAnimator gll=ObjectAnimator.ofFloat(gl,"alpha",0,1);
        gll.setStartDelay(delay2);
        gll.start();
        delay2+=150;




        return root;
    }






}
