package com.barebrains.gyanith19;

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


public class home extends Fragment {



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
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Unnamed");
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




        return root;
    }






}
