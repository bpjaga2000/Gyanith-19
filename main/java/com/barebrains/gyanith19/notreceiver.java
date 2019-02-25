package com.barebrains.gyanith19;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class notreceiver extends BroadcastReceiver {
    private DatabaseReference ndb,sdb;
    private SharedPreferences sp;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("not","triggered");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
        sp = context.getSharedPreferences("com.barebrains.Gyanith19", Context.MODE_PRIVATE);

        ndb= FirebaseDatabase.getInstance().getReference().child("notifications");
       sdb= FirebaseDatabase.getInstance().getReference();
        ndb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Long count=dataSnapshot.getChildrenCount();
                DataSnapshot upd=null;
                if(count>sp.getLong("notcount",0)){
                    for(DataSnapshot chi:dataSnapshot.getChildren()){
                        upd=chi;
                    }
                    NotificationCompat.Builder b=new NotificationCompat.Builder(context,"gyanith").setAutoCancel(true).setSmallIcon(R.drawable.l2).setContentTitle(upd.child("sender").getValue().toString());
                    b.setContentText(upd.child("text").getValue().toString());
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        int imp= NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel c=new NotificationChannel("gyanith","notification",imp);
                        NotificationManager noti=context.getSystemService(NotificationManager.class);
                        noti.createNotificationChannel(c);
                    }
                    b.setContentIntent(PendingIntent.getActivity(context, 0,
            new Intent(context, splash.class), PendingIntent.FLAG_UPDATE_CURRENT));

                    MediaPlayer mp=MediaPlayer.create(context,R.raw.noti);
                    mp.setLooping(false);
                    NotificationManagerCompat nmc=NotificationManagerCompat.from(context);
                    nmc.notify(1000,b.build());
                    mp.start();
                    sp.edit().putLong("notcount",count).commit();
                    sp.edit().putBoolean("newnot",true).commit();

                }
                else
                    sp.edit().putLong("notcount",count).commit();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot chi:dataSnapshot.getChildren()){
                    for(DataSnapshot ich:chi.getChildren()){
                        if(ich.child("desc").exists()){
                            if(sp.getBoolean(ich.getKey(),false)) {
                                if(sp.getString("gy"+ich.getKey(),"unotified").equals("unotified")){


                                Long st = Long.parseLong(ich.child("timestamp").getValue().toString());
                                Long nt = st - 900000;
                                Calendar ca = Calendar.getInstance();
                                if ((ca.getTimeInMillis() <= st) && (ca.getTimeInMillis() >= nt)) {


                                    NotificationCompat.Builder b = new NotificationCompat.Builder(context, "gyanith").setAutoCancel(true).setSmallIcon(R.drawable.l2).setContentTitle(ich.child("name").getValue().toString());
                                    Date d = new Date(st);
                                    SimpleDateFormat f = new SimpleDateFormat("hh:MM a");
                                    b.setContentText("Starting at " + f.format(d));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        int imp = NotificationManager.IMPORTANCE_DEFAULT;
                                        NotificationChannel c = new NotificationChannel("gyanith", "notification", imp);
                                        NotificationManager noti = context.getSystemService(NotificationManager.class);
                                        noti.createNotificationChannel(c);
                                    }
                                    MediaPlayer mp = MediaPlayer.create(context,R.raw.noti);
                                    mp.setLooping(false);
                                    NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
                                    nmc.notify(1000, b.build());
                                    mp.start();


                                }
                                sp.edit().putString("gy"+ich.getKey(),"notified");
                            }

                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






















        Intent i=new Intent(context,notreceiver.class);
        PendingIntent p=PendingIntent.getBroadcast(context,1000,i,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager a=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        a.setRepeating(AlarmManager.RTC_WAKEUP,0,60000,p);
    }
}
