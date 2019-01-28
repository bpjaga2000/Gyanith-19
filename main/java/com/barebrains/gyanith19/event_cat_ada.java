package com.barebrains.gyanith19;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.barebrains.gyanith19.R;
import com.barebrains.gyanith19.eventitem;

import java.util.ArrayList;
import java.util.List;

public class event_cat_ada extends ArrayAdapter{

    int res;
    ArrayList<eventitem> ei;
    Context c;

    public event_cat_ada( int res, ArrayList<eventitem> ei, Context c) {
        super(c, res, ei);
        this.res = res;
        this.ei = ei;
        this.c = c;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(c);
        View root = li.inflate(res,null,false);

        ((TextView)root.findViewById(R.id.eveitname)).setText(ei.get(position).getName());
        ((TextView)root.findViewById(R.id.eveittime)).setText(ei.get(position).getTime());

        return root;
    }
}
