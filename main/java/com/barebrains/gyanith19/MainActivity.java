package com.barebrains.gyanith19;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botnav;
    private TextView title;
    boolean doubleBackToExitPressedOnce;
    private ImageView imageView;
    Context context;
    SharedPreferences notif;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replace(new home());
                    title.setText("Gyanith 19");
                    return true;
                case R.id.navigation_schedule:
                    replace(new schedule());
                    title.setText("Schedule");
                    return true;
                case R.id.navigation_favourites:
                    replace(new favourites());
                    title.setText("Favourites");
                    return true;
                case R.id.navigation_notifications:
                   replace(new notifications());
                    title.setText("Notifications");
                    item.setIcon(R.drawable.ic_baseline_notifications_24px);
                    notif.edit().putBoolean("newnot",false).commit();
                    return true;
            }
            return false;
        }
    };

    private void replace(Fragment m){
        Fragment f=m;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.mainframe,f).commit();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.main_layout);
        doubleBackToExitPressedOnce = false;
        imageView=findViewById(R.id.topbaricon);
        context=this;

        notif = context.getSharedPreferences("com.barebrains.Gyanith19", Context.MODE_PRIVATE);
        botnav=findViewById(R.id.navigation);
        title=findViewById(R.id.title);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment f=new home();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.mainframe,f).commit();

        ((TextView)findViewById(R.id.title)).setText("Gyanith 19");

        ((Button)findViewById(R.id.account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//Toast.makeText(getApplicationContext(), "Will be updated soon!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Presented to you by HV,BP,KR,SR,AM",Toast.LENGTH_SHORT).show();
            }
        });

        if(notif.getBoolean("newnot",false)){
            navigation.getMenu().getItem(3).setIcon(R.drawable.ic_notification);
        }
}

}
