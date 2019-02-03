package com.barebrains.gyanith19;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notifications extends Fragment {

    private ListView notificationList;
    private DatabaseReference ref;
    private notificationItem item1;
    private notificationAdapter madapter;
    private ArrayList<notificationItem> arrListItem;

    public notifications() {
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
        final View root= inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationList = root.findViewById(R.id.notificationListView);
        ref = FirebaseDatabase.getInstance().getReference().child("notifications");
        Log.d("qwer",ref.toString());
        arrListItem = new ArrayList<notificationItem>();

        madapter = new notificationAdapter(getContext(), arrListItem, R.layout.notitem);

        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("asd",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrListItem.clear();
                Log.d("qwer",dataSnapshot.toString());
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    item1 = new notificationItem(snapshot.child("sender").getValue().toString(), snapshot.child("time").getValue().toString(), snapshot.child("text").getValue().toString());
                    arrListItem.add(0,item1);
                    Log.d("qwer","noti");
                }
                madapter.notifyDataSetChanged();
                ((ProgressBar)root.findViewById(R.id.notload)).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        notificationList.setAdapter(madapter);
        return root;
    }
}
