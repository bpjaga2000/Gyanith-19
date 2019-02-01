package com.barebrains.gyanith19;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class schAdapter extends ArrayAdapter {

    private Context c;
    private ArrayList<schItem> list;
    private int res;
    int pos=0;

    public schAdapter(Context c, ArrayList<schItem> list, int res) {
        super(c, res, list);
        this.c = c;
        this.list = list;
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(c);
        View root = li.inflate(res, null, false);

        ((TextView)root.findViewById(R.id.time)).setText(list.get(position).getTime());
        ((TextView)root.findViewById(R.id.title)).setText(list.get(position).getTitle());
        ((TextView)root.findViewById(R.id.venue)).setText(list.get(position).getVenue());
        if (position==pos) {


            root.setAlpha(0);
            Long delay = Long.valueOf(position * 150);
            ObjectAnimator a = ObjectAnimator.ofFloat(root, "alpha", 0, 1);
            a.setDuration(300);
            a.setStartDelay(delay);
            a.start();
            ObjectAnimator o = ObjectAnimator.ofFloat(root, "scaleX",1,2);
            o.setStartDelay(delay);
            o.setInterpolator(new DecelerateInterpolator());
            o.setDuration(500);
            o.start();
           pos++;
        }

        return root;
    }
}
