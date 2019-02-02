package com.barebrains.gyanith19;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int numoffragments=4;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Fragment fragment;
    BottomNavigationView botnav;
    List s= Arrays.asList("Gyanith 19","Schedule","Favourites","Notifications");
    List itsl=Arrays.asList(R.id.navigation_home,R.id.navigation_schedule,R.id.navigation_favourites,R.id.navigation_notifications);
    private TextView title;
   private FrameLayout parent;




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
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.main_layout);


        botnav=findViewById(R.id.navigation);
        title=findViewById(R.id.title);
        /*viewPager=findViewById(R.id.gestureelement);
        pagerAdapter= new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);

       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        }*/



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
                Toast.makeText(MainActivity.this,"Will be updated soon",Toast.LENGTH_LONG).show();
               // Intent i=new Intent(getApplicationContext(),LoginActivity.class);
               // startActivity(i);
            }
        });
       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ((TextView)findViewById(R.id.title)).setText(s.get(i).toString());
                botnav.setSelectedItemId(Integer.parseInt(itsl.get(i).toString()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            fragment=null;
            switch (i) {
                case 0:
                    fragment = new home();
                    break;
                case 1:
                    fragment = new schedule();
                    break;
                case 2:
                    fragment = new favourites();
                    break;
                case 3:
                    fragment = new notifications();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return numoffragments;
        }
    }*/


}}
