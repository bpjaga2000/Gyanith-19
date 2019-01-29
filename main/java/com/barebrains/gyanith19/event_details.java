package com.barebrains.gyanith19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class event_details extends AppCompatActivity {

    TextView title,desc;
    ImageView eveimage,back;
    ToggleButton favtb;
    DatabaseReference reference;
    Intent intent;
    String child,tag;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        sp= this.getSharedPreferences("com.barebrains.Gyanith19",MODE_PRIVATE);

        intent = getIntent();
        child=intent.getStringExtra("category");
        tag= intent.getStringExtra("tag");


        title=findViewById(R.id.evedttitle);
        desc=findViewById(R.id.evedesc);

        eveimage=findViewById(R.id.eveimv);
        favtb=findViewById(R.id.favButton);

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

        favtb.setChecked(sp.getBoolean(tag,false));
        favtb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sp.edit().putBoolean(tag,favtb.isChecked()).commit();
            }
        });

    }
}
