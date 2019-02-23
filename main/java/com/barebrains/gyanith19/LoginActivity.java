package com.barebrains.gyanith19;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Dimension;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class LoginActivity extends AppCompatActivity  {


    EditText uid,pwd;
    Button signin, back;
    Bitmap bitmap;
    String id,pw;
    ProgressBar qrpr, loginprog;
    ImageView qrImage, qrz;
    SharedPreferences sp;
    LinearLayout eventll, workshopll, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back=findViewById(R.id.backbutlogin);
        qrImage=findViewById(R.id.qrimage);
        qrpr=findViewById(R.id.qrprog);
        loginprog=findViewById(R.id.loginprog);
        qrz=findViewById(R.id.qrz);
        uid=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        pwd.setTransformationMethod(new PasswordTransformationMethod());
        signin=findViewById(R.id.email_sign_in_button);
        eventll=findViewById(R.id.eventsll);
        workshopll=findViewById(R.id.workshopll);
        signup=findViewById(R.id.signupll);

        final String urls[] = new String[2];
        sp = getSharedPreferences("com.barebrains.gyanith19", MODE_PRIVATE);
        String storedid, storedpass;
        storedid = sp.getString("userid","");
        storedpass = sp.getString("userpass","");

       // if(!storedid.equals(""))

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //REGISTER USER
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
                ((CardView)findViewById(R.id.regcard)).setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
            }
        });

        ((Button)findViewById(R.id.closereg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CardView)findViewById(R.id.regcard)).setVisibility(View.GONE);
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });






        if (!isNetworkAvailable() && storedid.equals("")){
            Toast.makeText(getApplicationContext(),"Network data unavailable!", Toast.LENGTH_LONG).show();
        }else
            if (!isNetworkAvailable() && !storedid.equals("")){
                Toast.makeText(getApplicationContext(), "Network unavailable. Showing old data..", Toast.LENGTH_LONG).show();
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
                loadfromSharedpref();
            }else
            {
                if(!storedid.equals("")){

                    urls[0]= "";  // link to receive access token
                    urls[1] = ""; // link to receive userdetails json
                    ((FrameLayout)findViewById(R.id.userdetails)).setVisibility(View.VISIBLE);
                    ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
                    loginprog.setVisibility(View.VISIBLE);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("misc");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            urls[0] = dataSnapshot.child("loginurl").getValue().toString();
                            urls[1] = dataSnapshot.child("userdeturl").getValue().toString();

                            pw = md5(pwd.getText().toString());
                            id = md5(uid.getText().toString());

                            urls[0].replaceAll("UserId=", ("UserId=" + id));

                            RequestQueue q = Volley.newRequestQueue(getApplicationContext());

                            JsonObjectRequest j=new JsonObjectRequest(Request.Method.GET,
                                    urls[0], null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response.has("token")){
                                        String accesstoken = "";
                                        Log.i("JSON", "Received response");
                                        try {
                                            accesstoken = response.getString("token");
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        if (!accesstoken.equals("")){
                                            sp.edit().putString("userid", id).apply();
                                            sp.edit().putString("userpass", pw).apply();
                                            ((CardView)findViewById(R.id.qrcard)).setEnabled(false);
                                            ((FrameLayout)findViewById(R.id.userdetails)).setVisibility(View.VISIBLE);
                                            loginprog.setVisibility(View.GONE);
                                            urls[1].replaceAll("token=", ( "token" + accesstoken ));
                                            Log.i("URLL", urls[1]);

                                            RequestQueue qq = Volley.newRequestQueue(getApplicationContext());
                                            JsonObjectRequest jj = new JsonObjectRequest(Request.Method.GET,
                                                    urls[1], null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Log.i("JSONN", response.toString());
                                                    sp.edit().putString("jsonresponse", response.toString()).apply();
                                                    try{
                                                        String gid = response.getString("gyanithid");
                                                        ((TextView)findViewById(R.id.gidtv)).setText(gid);
                                                        String username = response.getString("username");
                                                        ((TextView)findViewById(R.id.usernametv)).setText(username);
                                                        JSONArray events = response.getJSONArray("eventsreg");
                                                        JSONArray workshops = response.getJSONArray("workreg");
                                                        int workshopcount = workshops.length();
                                                        int eventscount = events.length();
                                                        String qrdata = "GyanithId: "+ gid + "\n" + "EventsReg:";

                                                        if(eventscount == 0){
                                                            TextView tv = new TextView(getApplicationContext());
                                                            tv.setText("No events registered!");
                                                            tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                            tv.setTextSize(18);
                                                            tv.setTextColor(Color.WHITE);
                                                            eventll.addView(tv);
                                                        }
                                                        for (int i=0; i<eventscount; i++){
                                                            String event =events.getString(i);
                                                            Log.i("JSONN", event );
                                                            qrdata = qrdata + " " + event + ",";
                                                            TextView tv = new TextView(getApplicationContext());
                                                            tv.setText(event);
                                                            tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                            tv.setTextSize(18);
                                                            tv.setTextColor(Color.WHITE);
                                                            eventll.addView(tv);
                                                        }
                                                        qrdata = qrdata + "\n" + "Workshops Reg:";


                                                        if(workshopcount == 0){
                                                            TextView tv = new TextView(getApplicationContext());
                                                            tv.setText("No workshops registered!");
                                                            //tv.setTypeface(Typeface.createFromAsset(getAssets(), "sofiaprolight.otf"));
                                                            tv.setTextSize(18);
                                                            tv.setTextColor(Color.WHITE);
                                                            workshopll.addView(tv);
                                                        }
                                                        for (int i=0; i<workshopcount; i++){
                                                            String work = workshops.getString(i);
                                                            Log.i("JSONN", work);
                                                            qrdata = qrdata + " " + work + ",";
                                                            TextView tv = new TextView(getApplicationContext());
                                                            tv.setText(work);
                                                            tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                            tv.setTextSize(18);
                                                            tv.setTextColor(Color.WHITE);
                                                            workshopll.addView(tv);
                                                        }

                                                        qrdata = qrdata.substring(0,qrdata.length()-1) + ".";
                                                        sp.edit().putString("qrdata",qrdata).apply();
                                                        QRGEncoder qrgEncoder = new QRGEncoder(qrdata, null, QRGContents.Type.TEXT, 300);
                                                        try {
                                                            // Getting QR-Code as Bitmap
                                                            bitmap = qrgEncoder.encodeAsBitmap();
                                                            // Setting Bitmap to ImageView
                                                            qrImage.setImageBitmap(bitmap);
                                                            qrz.setImageBitmap(bitmap);
                                                            ((CardView)findViewById(R.id.qrcard)).setEnabled(true);
                                                            qrpr.setVisibility(View.GONE);
                                                        } catch (WriterException e) {
                                                            Log.v("Exception", e.toString());
                                                        }

                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });
                                            qq.add(jj);

                                        }
                                    }
                                    else Toast.makeText(getApplicationContext(), "User credentials invalid!", Toast.LENGTH_LONG).show();
                                    loginprog.setVisibility(View.GONE);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Toast.makeText(getApplicationContext(), "User credentials invalid!", Toast.LENGTH_LONG).show();
                                }
                            });
                            q.add(j);

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Check network connection and try again!" , Toast.LENGTH_LONG).show();
                            loginprog.setVisibility(View.GONE);
                        }
                    });
                }else{
                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loginprog.setVisibility(View.VISIBLE);
                            String pas = pwd.getText().toString();
                            String idd = uid.getText().toString();
                            if (pas.equals("") || idd.equals("")){
                                loginprog.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Enter credentials!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                pw = md5(pas);
                                id = md5(idd);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("misc");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        urls[0] = dataSnapshot.child("loginurl").getValue().toString();
                                        urls[1] = dataSnapshot.child("userdeturl").getValue().toString();


                                        urls[0].replaceAll("UserId=", ("UserId=" + id));

                                        RequestQueue q = Volley.newRequestQueue(getApplicationContext());

                                        JsonObjectRequest j=new JsonObjectRequest(Request.Method.GET,
                                                urls[0], null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                if (response.has("token")){
                                                    String accesstoken = "";
                                                    Log.i("JSON", "Received response");
                                                    try {
                                                        accesstoken = response.getString("token");
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                    }

                                                    if (!accesstoken.equals("")){
                                                        sp.edit().putString("userid", id).apply();
                                                        sp.edit().putString("userpass", pw).apply();
                                                        ((CardView)findViewById(R.id.qrcard)).setEnabled(false);
                                                        ((FrameLayout)findViewById(R.id.userdetails)).setVisibility(View.VISIBLE);
                                                        ((ScrollView)findViewById(R.id.sign)).setVisibility(View.GONE);
                                                        loginprog.setVisibility(View.GONE);
                                                        urls[1].replaceAll("token=", ( "token" + accesstoken ));
                                                        Log.i("URLL", urls[1]);

                                                        RequestQueue qq = Volley.newRequestQueue(getApplicationContext());
                                                        JsonObjectRequest jj = new JsonObjectRequest(Request.Method.GET,
                                                                urls[1], null, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Log.i("JSONN", response.toString());
                                                                sp.edit().putString("jsonresponse", response.toString()).apply();
                                                                try{
                                                                    String gid = response.getString("gyanithid");
                                                                    ((TextView)findViewById(R.id.gidtv)).setText(gid);
                                                                    String username = response.getString("username");
                                                                    ((TextView)findViewById(R.id.usernametv)).setText(username);
                                                                    JSONArray events = response.getJSONArray("eventsreg");
                                                                    JSONArray workshops = response.getJSONArray("workreg");
                                                                    int workshopcount = workshops.length();
                                                                    int eventscount = events.length();
                                                                    String qrdata = "GyanithId: "+ gid + "\nName: " + username + "\nEventsReg:";

                                                                    if(eventscount == 0){
                                                                        TextView tv = new TextView(getApplicationContext());
                                                                        tv.setText("No registered events found!");
                                                                        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                                        tv.setTextSize(18);
                                                                        tv.setTextColor(Color.WHITE);
                                                                        eventll.addView(tv);
                                                                    }
                                                                    for (int i=0; i<eventscount; i++){
                                                                        String event =events.getString(i);
                                                                        Log.i("JSONN", event );
                                                                        qrdata = qrdata + " " + event + ",";
                                                                        TextView tv = new TextView(getApplicationContext());
                                                                        tv.setText(event);
                                                                        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                                        tv.setTextSize(18);
                                                                        tv.setTextColor(Color.WHITE);
                                                                        eventll.addView(tv);
                                                                    }
                                                                    qrdata = qrdata + "\n" + "Workshops Reg:";


                                                                    if(workshopcount == 0){
                                                                        TextView tv = new TextView(getApplicationContext());
                                                                        tv.setText("No registered workshops found!");
                                                                        //tv.setTypeface(Typeface.createFromAsset(getAssets(), "sofiaprolight.otf"));
                                                                        tv.setTextSize(18);
                                                                        tv.setTextColor(Color.WHITE);
                                                                        workshopll.addView(tv);
                                                                    }
                                                                    for (int i=0; i<workshopcount; i++){
                                                                        String work = workshops.getString(i);
                                                                        Log.i("JSONN", work);
                                                                        qrdata = qrdata + " " + work + ",";
                                                                        TextView tv = new TextView(getApplicationContext());
                                                                        tv.setText(work);
                                                                        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                                                        tv.setTextSize(18);
                                                                        tv.setTextColor(Color.WHITE);
                                                                        workshopll.addView(tv);
                                                                    }

                                                                    qrdata = qrdata.substring(0,qrdata.length()-1) + ".";
                                                                    sp.edit().putString("qrdata",qrdata).apply();
                                                                    QRGEncoder qrgEncoder = new QRGEncoder(qrdata, null, QRGContents.Type.TEXT, 300);
                                                                    try {
                                                                        // Getting QR-Code as Bitmap
                                                                        bitmap = qrgEncoder.encodeAsBitmap();
                                                                        // Setting Bitmap to ImageView
                                                                        qrImage.setImageBitmap(bitmap);
                                                                        qrz.setImageBitmap(bitmap);
                                                                        ((CardView)findViewById(R.id.qrcard)).setEnabled(true);
                                                                        qrpr.setVisibility(View.GONE);
                                                                    } catch (WriterException e) {
                                                                        Log.v("Exception", e.toString());
                                                                    }

                                                                }catch (Exception e){
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        },new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {

                                                            }
                                                        });
                                                        qq.add(jj);

                                                    }
                                                }
                                                else {
                                                    pwd.setText("");
                                                    Toast.makeText(getApplicationContext(), "User credentials invalid!", Toast.LENGTH_LONG).show();
                                                }
                                                loginprog.setVisibility(View.GONE);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // Toast.makeText(getApplicationContext(), "User credentials invalid!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        q.add(j);

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(), "Check network connection and try again!" , Toast.LENGTH_LONG).show();
                                        loginprog.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
                    });
                }

        }


        ((CardView)findViewById(R.id.qrcard)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FrameLayout)findViewById(R.id.qrzoomed)).setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
            }
        });

        ((Button)findViewById(R.id.closezoom)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FrameLayout)findViewById(R.id.qrzoomed)).setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadfromSharedpref(){
        ((FrameLayout)findViewById(R.id.userdetails)).setVisibility(View.VISIBLE);
        ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
        String jsonres = sp.getString("jsonresponse", "");
        JSONObject response = null;
        try{
          response = new JSONObject(jsonres);
          ((TextView)findViewById(R.id.gidtv)).setText(response.getString("gyanithid"));
          ((TextView)findViewById(R.id.usernametv)).setText(response.getString("username"));

            JSONArray events = response.getJSONArray("eventsreg");
            JSONArray workshops = response.getJSONArray("workreg");
            int workshopcount = workshops.length();
            int eventscount = events.length();

            if(eventscount == 0){
                TextView tv = new TextView(getApplicationContext());
                tv.setText("No events registered!");
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                eventll.addView(tv);
            }
            for (int i=0; i<eventscount; i++){
                String event =events.getString(i);
                Log.i("JSONN", event );
                TextView tv = new TextView(getApplicationContext());
                tv.setText(event);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                eventll.addView(tv);
            }

            if(workshopcount == 0){
                TextView tv = new TextView(getApplicationContext());
                tv.setText("No workshops registered!");
                //tv.setTypeface(Typeface.createFromAsset(getAssets(), "sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                workshopll.addView(tv);
            }
            for (int i=0; i<workshopcount; i++){
                String work = workshops.getString(i);
                TextView tv = new TextView(getApplicationContext());
                tv.setText(work);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                workshopll.addView(tv);
            }

            QRGEncoder qrgEncoder = new QRGEncoder(sp.getString("qrdata",""), null, QRGContents.Type.TEXT, 300);
            try {
                // Getting QR-Code as Bitmap
                bitmap = qrgEncoder.encodeAsBitmap();
                // Setting Bitmap to ImageView
                qrImage.setImageBitmap(bitmap);
                qrz.setImageBitmap(bitmap);
                ((CardView)findViewById(R.id.qrcard)).setEnabled(true);
                qrpr.setVisibility(View.GONE);
            } catch (WriterException e) {
                Log.v("Exception", e.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

// Login https://api.jsonbin.io/b/5c67b234a83a29317735e26c/1
// Details https://api.jsonbin.io/b/5c67b201a83a29317735e24c/1