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

public class notificationAdapter extends ArrayAdapter {
    private Context c;
    private ArrayList<notificationItem> list;
    private int res;
    int pos=0;

    public notificationAdapter(Context c, ArrayList<notificationItem> list, int res) {
        super(c, res, list);
        this.c = c;
        this.list = list;
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(c);
        View root = li.inflate(res, null, false);

        ((TextView) root.findViewById(R.id.notificationSender)).setText(list.get(position).getSender());
        ((TextView) root.findViewById(R.id.notificationTime)).setText(list.get(position).getTime());
        ((TextView) root.findViewById(R.id.notificationText)).setText(list.get(position).getText());



        return root;
    }
}