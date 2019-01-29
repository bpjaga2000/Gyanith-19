package com.barebrains.gyanith19;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class event_categories extends AppCompatActivity {

    String s;
    DatabaseReference ref;
    event_cat_ada ada;
    ArrayList<eventitem> items;
    eventitem it;
    ListView lvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref= FirebaseDatabase.getInstance().getReference();
        items=new ArrayList<eventitem>();
        setContentView(R.layout.activity_event_categories);
        lvi=findViewById(R.id.eveitlv);
        final Intent i1=new Intent(this,event_details.class);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        }



        ((Button)findViewById(R.id.backbut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i=getIntent();
        ((TextView)findViewById(R.id.cattitle)).setText(i.getStringExtra("category"));
        s=i.getStringExtra("category");
        ada=new event_cat_ada(R.layout.eve_cat_item,items,this);
        ref.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    it=new eventitem(snapshot.child("name").getValue().toString(),snapshot.child("timestamp").getValue().toString());
                    items.add(it);

                }
                ada.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvi.setAdapter(ada);

        lvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i1.putExtra("category",s);
                i1.putExtra("tag",s.charAt(0)+Integer.toString(position+1));
                startActivity(i1);
            }
        });

    }
}
