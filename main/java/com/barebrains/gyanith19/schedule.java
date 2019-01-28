package com.barebrains.gyanith19;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class schedule extends Fragment {

    private ListView scheduleList;
    private DatabaseReference ref;
    private schItem it1;
    private schAdapter d1,d2;
    private ArrayList<schItem> itemd1, itemd2;
    private TabLayout mtabLayout;

    public schedule() {
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
        final View root= inflater.inflate(R.layout.fragment_schedule, container, false);
        scheduleList = root.findViewById(R.id.schlv);
        ref = FirebaseDatabase.getInstance().getReference().child("schedule");
        itemd1 = new ArrayList<schItem>();
        itemd2 = new ArrayList<schItem>();

        d1 = new schAdapter(getContext(), itemd1, R.layout.schitem);
        d2 = new schAdapter(getContext(), itemd2, R.layout.schitem);

        mtabLayout = root.findViewById(R.id.schtabLayout);

        ref.child("day0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    it1 = new schItem(snapshot.child("name").getValue().toString(), timeFormatter(snapshot.child("startTime").getValue().toString()), snapshot.child("venue").getValue().toString());
                    itemd1.add(it1);
                }
                d1.notifyDataSetChanged();
                ((ProgressBar)root.findViewById(R.id.schload)).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("day1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    it1 = new schItem(snapshot.child("name").getValue().toString(), timeFormatter(snapshot.child("startTime").getValue().toString()), snapshot.child("venue").getValue().toString());
                    itemd2.add(it1);
                }
                d2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        scheduleList.setAdapter(d1);

        mtabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int a = tab.getPosition();
                if(a == 0)
                    scheduleList.setAdapter(d1);
                else
                    scheduleList.setAdapter(d2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    public String timeFormatter(String time)
    {
        long timeInt = Long.parseLong(time);
        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInt),
                TimeUnit.MILLISECONDS.toMinutes(timeInt) % TimeUnit.HOURS.toMinutes(1));
        return hms;
    }
}