package com.barebrains.gyanith19;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
   private FrameLayout parent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ((TextView)findViewById(R.id.title)).setText("Gyanith 19");
                    replace(new home());

                  return true;
                case R.id.navigation_schedule:
                    ((TextView)findViewById(R.id.title)).setText("Schedule");
                    replace(new schedule());
                    return true;
                case R.id.navigation_favourites:
                    ((TextView)findViewById(R.id.title)).setText("Favorites");
                    replace(new favourites());
                    return true;
                case R.id.navigation_notifications:
                    ((TextView)findViewById(R.id.title)).setText("Notifications");
                    replace(new notifications());
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        }

        parent=(FrameLayout)findViewById(R.id.mainframe);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment f=new home();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK);
        ft.replace(R.id.mainframe,f).commit();

        ((TextView)findViewById(R.id.title)).setText("Gyanith 19");

        ((Button)findViewById(R.id.account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),profile.class);
                startActivity(i);
            }
        });
    }

}
