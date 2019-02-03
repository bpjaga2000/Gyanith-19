package com.barebrains.gyanith19;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class event_categories extends AppCompatActivity {

    String s;
    DatabaseReference ref;
    event_cat_ada ada;
    ArrayList<eventitem> items;
    eventitem it;
    ListView lvi;
    ArrayList tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        ref= FirebaseDatabase.getInstance().getReference();
        items=new ArrayList<eventitem>();
        setContentView(R.layout.activity_event_categories);
        lvi=findViewById(R.id.eveitlv);
        final Intent i1=new Intent(this,event_details.class);
        /*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        }*/



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
        tag = new ArrayList();
        ref.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    try{
                        it=new eventitem(snapshot.child("name").getValue().toString(),timeFormatter(snapshot.child("timestamp").getValue().toString()),snapshot.getKey().toString());
                        items.add(it);
                        tag.add(snapshot.getKey());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                if(items.isEmpty()) ((TextView)findViewById(R.id.textView14)).setVisibility(View.VISIBLE);
                else ((TextView)findViewById(R.id.textView14)).setVisibility(View.GONE);
                ((ProgressBar)findViewById(R.id.catload)).setVisibility(View.GONE);
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
                i1.putExtra("tag",tag.get(position).toString());
                startActivity(i1);
            }
        });



    }

    public String timeFormatter(String time)
    {
        long timeInt = Long.parseLong(time);
        SimpleDateFormat s=new SimpleDateFormat("MMM dd");

        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(timeInt);
        if(c.getTime().getDate()== Calendar.getInstance().getTime().getDate())
            return "Today";
        else if(c.getTime().getDate()== Calendar.getInstance().getTime().getDate()+1)
            return "Tommorow";

        Date d=new Date(timeInt);
        return s.format(d);
    }
}
