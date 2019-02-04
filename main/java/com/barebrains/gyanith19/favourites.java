package com.barebrains.gyanith19;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class favourites extends Fragment {


    DatabaseReference ref;
    event_cat_ada ada;
    ArrayList<eventitem> items;
    eventitem it;
    ListView lvi;
    SharedPreferences sp;
    ArrayList tag;
    String cat;

    public favourites() {
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
        final View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        sp = getContext().getSharedPreferences("com.barebrains.Gyanith19", Context.MODE_PRIVATE);
        ref= FirebaseDatabase.getInstance().getReference();
        items=new ArrayList<eventitem>();
        lvi=root.findViewById(R.id.favlv);
        final Intent i1=new Intent(this.getContext(),event_details.class);
        ada=new event_cat_ada(R.layout.eve_cat_item,items,this.getContext());
        tag = new ArrayList();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot sh:dataSnapshot.getChildren()){
                    for(DataSnapshot snapshot:sh.getChildren()){
                        if(snapshot.child("desc").exists()){

                            Log.i("tagy",snapshot.getKey());
                            if(sp.getBoolean(snapshot.getKey(),false)) {
                                try{
                                    it = new eventitem(snapshot.child("name").getValue().toString(),timeFormatter(snapshot.child("timestamp").getValue().toString()), snapshot.getKey());
                                    items.add(it);
                                    //((TextView)root.findViewById(R.id.textView13)).setVisibility(View.GONE);
                                    tag.add(snapshot.getKey());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                            }
                        }

                        ((ProgressBar)root.findViewById(R.id.favload)).setVisibility(View.GONE);
                        if(items.isEmpty()){
                            ((TextView)root.findViewById(R.id.textView13)).setVisibility(View.VISIBLE);

                        }else((TextView)root.findViewById(R.id.textView13)).setVisibility(View.GONE);

                        ada.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvi.setAdapter(ada);

        lvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(tag.get(position).toString().charAt(0)){
                    case 'W':
                        cat= "Workshop";
                        break;
                    case 'G':
                        cat="Guest Lectures";
                        break;
                    case 'T':
                        cat="Technical Events";
                        break;
                    case 'N':
                        cat="Non Technical Events";
                        break;
                    case 'P':
                        cat="Pro Shows";
                        break;
                }
                Intent i1= new Intent(getContext(),event_details.class);
                i1.putExtra("category",cat);
                i1.putExtra("tag",tag.get(position).toString());
                startActivity(i1);
            }
        });



        return root;
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
