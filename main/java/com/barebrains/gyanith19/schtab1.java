package com.barebrains.gyanith19;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class schtab1 extends Fragment {

    private ListView scheduleList;
    private DatabaseReference ref;
    private schItem it1;
    private schAdapter adapter;
    private ArrayList<schItem> list;

    public schtab1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_schtab1, container, false);

        scheduleList = root.findViewById(R.id.sch1);
        ref = FirebaseDatabase.getInstance().getReference().child("schedule");
        list = new ArrayList<schItem>();
        adapter = new schAdapter(getContext(), list, R.layout.schitem);

        ref.child("day0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    it1 = new schItem(snapshot.child("name").getValue().toString(), timeFormatter(snapshot.child("startTime").getValue().toString()), snapshot.child("venue").getValue().toString());
                    list.add(it1);
                }
                adapter.notifyDataSetChanged();
                schedule.gone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        scheduleList.setAdapter(adapter);

        return root;
    }

    public String timeFormatter(String time)
    {
        long timeInt = Long.parseLong(time);
        SimpleDateFormat s=new SimpleDateFormat("HH:MM");
        Date d=new Date(timeInt);
        return s.format(d);
    }


}


