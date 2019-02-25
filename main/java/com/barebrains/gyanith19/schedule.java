package com.barebrains.gyanith19;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class schedule extends Fragment {

    private TabLayout mtabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private static View gone;

    public schedule() {
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
        final View root= inflater.inflate(R.layout.fragment_schedule, container, false);

        gone=root;
        mtabLayout = root.findViewById(R.id.schtabLayout);
        viewPager=root.findViewById(R.id.viewpager);
        pagerAdapter=new pager(getActivity().getSupportFragmentManager(),mtabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        mtabLayout.setupWithViewPager(viewPager);


        mtabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int a = tab.getPosition();
                viewPager.setCurrentItem(a);
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    public class pager extends FragmentStatePagerAdapter{

        private String[] tabTitles = new String[]{"Live", "Day 1", "Day 2","Day 3"};

        public pager(FragmentManager fm,int tabs) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment=new schtab0();
            switch (i){
                case 0:
                    fragment=new schtab0();
                    break;
                case 1:
                    fragment=new schtab1();
                    break;
                case 2:
                    fragment=new schtab2();
                    break;
                case 3:
                    fragment=new schtab3();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    public static void gone(){
        ((ProgressBar)gone.findViewById(R.id.schload)).setVisibility(View.GONE);
        //((TextView)gone.findViewById(R.id.up)).setVisibility(View.GONE);
    }

}
