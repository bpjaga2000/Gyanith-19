package com.barebrains.gyanith19;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
    Button bb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_event_details);


        sp= this.getSharedPreferences("com.barebrains.Gyanith19",MODE_PRIVATE);

        bb2=findViewById(R.id.backbut2);
        intent = getIntent();
        child=intent.getStringExtra("category");
        tag= intent.getStringExtra("tag");
        title=findViewById(R.id.evedttitle);
        desc=findViewById(R.id.evedesc);

        eveimage=findViewById(R.id.eveimv);
        favtb=findViewById(R.id.favButton);

        bb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child(child).child(tag);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title.setText(dataSnapshot.child("name").getValue().toString());
                    desc.setText(dataSnapshot.child("desc").getValue().toString());
                    int id = getResources().getIdentifier("com.barebrains.gyanith19:drawable/" + tag.toLowerCase()+'b', null, null);
                    if(id!=0)
                    ((ImageView)findViewById(R.id.eveimv)).setBackgroundResource(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final ImageView f=(ImageView)findViewById(R.id.fh) ;

        favtb.setChecked(sp.getBoolean(tag,false));
        favtb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sp.edit().putBoolean(tag,favtb.isChecked()).commit();
                if(isChecked){
                    ObjectAnimator fa=ObjectAnimator.ofFloat(f,"alpha",1,0);
                    fa.setDuration(500);
                    fa.start();
                    ObjectAnimator fa1=ObjectAnimator.ofFloat(f,"scaleX",1,5);
                    fa1.setDuration(500);
                    fa1.start();
                    ObjectAnimator fa2=ObjectAnimator.ofFloat(f,"scaleY",1,5);
                    fa2.setDuration(500);
                    fa2.start();

                }
            }
        });



    }
}
