package com.barebrains.gyanith19;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class event_details extends AppCompatActivity {

    TextView title,desc;
    ImageView eveimage,favimage,back;
    DatabaseReference reference;
    Intent intent;
    String child,tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        intent = getIntent();
        child=intent.getStringExtra("category");
        tag= intent.getStringExtra("tag");


        title=findViewById(R.id.evedttitle);
        desc=findViewById(R.id.evedesc);

        eveimage=findViewById(R.id.eveimv);
        Log.i("item",tag);

        reference = FirebaseDatabase.getInstance().getReference().child(child).child(tag);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.child("name").getValue().toString());
                desc.setText(dataSnapshot.child("desc").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
