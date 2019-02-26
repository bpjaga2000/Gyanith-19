package com.barebrains.gyanith19;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class LoginActivity extends AppCompatActivity  {


    EditText uid,pwd;
    Button signin, back;
    Bitmap bitmap;
    String id,pw,pass;
    ProgressBar qrpr, loginprog;
    ImageView qrImage, qrz;
    SharedPreferences sp;
    LinearLayout eventll, workshopll, signup;
    WebView webview;

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
        webview = findViewById(R.id.wvreg);
        signup=findViewById(R.id.signupll);
        sp = getSharedPreferences("com.barebrains.gyanith19", MODE_PRIVATE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((Button)findViewById(R.id.backbutwv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.setVisibility(View.GONE);
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //REGISTER USER
                back.setVisibility(View.GONE);
                loginprog.setVisibility(View.VISIBLE);
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
                webview.setVisibility(View.VISIBLE);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        loginprog.setVisibility(View.GONE);
                    }
                });
                webview.loadUrl("https://gyanith.org/register.php");
                back.setVisibility(View.GONE);
            }
        });


        if (!isNetworkAvailable()){
            Toast.makeText(this, "Network unavailable!", Toast.LENGTH_LONG).show();
        }

        String savedid = sp.getString("userid", "");
        String savedpas = sp.getString("userpass", "");

        if (!savedid.equals("")){

            if (isNetworkAvailable()){
                loginprog.setVisibility(View.VISIBLE);
                ((ScrollView)findViewById(R.id.sign)).setVisibility(View.INVISIBLE);
                signingyan(savedpas, savedid);
            }
            else{
                loadfromSharedpref();
            }
        }


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginprog.setVisibility(View.VISIBLE);
                String pas = pwd.getText().toString();
                String idd = uid.getText().toString();

                if (pas.equals("") || idd.equals("")){
                    loginprog.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Enter credentials!", Toast.LENGTH_LONG).show();
                }
                else {//signingyan
                    signingyan(pas, idd);
                }
            }
        });


        ((TextView)findViewById(R.id.signouttv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setIcon(R.drawable.signout)
                        .setTitle("Sign Out?")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sp.edit().remove("userid").apply();
                                sp.edit().remove("userpass").apply();
                                sp.edit().remove("userpasshash").apply();
                                ((FrameLayout) findViewById(R.id.userdetails)).setVisibility(View.GONE);
                                ((ScrollView) findViewById(R.id.sign)).setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "User signed out!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

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

    public void signingyan(String pas, String idd){
        pass = pas;
        pw = md5(pas);
        Log.i("passmd5",pw);
        id = idd;
        final String urls[] = new String[2];
        urls[0] = "http://gyanith.org/mobileapi/login_mobile.php?email=" + id + "&id=" + pw;//dataSnapshot.child("loginurl").getValue().toString();
        urls[1] = "http://gyanith.org/mobileapi/profile.php?q=";

        Log.i("JSONURL",urls[0]);

        RequestQueue q = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest j=new JsonObjectRequest(Request.Method.GET,
                urls[0], null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("JSON",response.toString());
                if (response.has("token")){
                    String accesstoken = "";

                    Log.i("JSON", "Received response" + response.toString());
                    try {
                        accesstoken = response.getString("token");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    urls[1] = urls[1] + accesstoken;

                    Log.i("JSONURL",urls[0]);

                    RequestQueue qq = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest jj = new JsonObjectRequest(Request.Method.GET, urls[1], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.i("JSON",response.toString());
                            sp.edit().putString("userid", id).apply();
                            sp.edit().putString("userpass", pass).apply();
                            sp.edit().putString("userpasshash", pw).apply();
                            ((CardView) findViewById(R.id.qrcard)).setEnabled(false);
                            ((FrameLayout) findViewById(R.id.userdetails)).setVisibility(View.VISIBLE);
                            ((ScrollView) findViewById(R.id.sign)).setVisibility(View.GONE);
                            loginprog.setVisibility(View.GONE);

                            sp.edit().putString("jsonresponse", response.toString()).apply();
                            try {
                                String gid = response.getString("gid");
                                ((TextView) findViewById(R.id.gidtv)).setText(gid);
                                String username = response.getString("name");
                                ((TextView) findViewById(R.id.usernametv)).setText(username);
                                ((TextView)findViewById(R.id.collegetv)).setText(response.getString("college"));
                                JSONArray events = response.getJSONArray("event");
                                JSONArray workshops = response.getJSONArray("work");

                                int workshopcount = workshops.length();
                                int eventscount = events.length();

                                String qrdata = "GyanithId: " + gid + "\nCollege: " + response.getString("college") + "\nContact: " + response.getString("mobile")
                                        + "\nName: " + username + "\nEventsReg:";

                                if(eventscount == 0){
                                    TextView tv = new TextView(getApplicationContext());
                                    tv.setText("No registered events found!");
                                    tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                    tv.setTextSize(18);
                                    tv.setTextColor(Color.WHITE);
                                    eventll.addView(tv);
                                }
                                for (int i=0; i<eventscount; i++){
                                    String event = events.getString(i);
                                    Log.i("JSONN", event );
                                    qrdata = qrdata + " " + event + ",";
                                    TextView tv = new TextView(getApplicationContext());
                                    tv.setText("• "+event);
                                    tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                    tv.setTextSize(18);
                                    tv.setTextColor(Color.WHITE);
                                    eventll.addView(tv);
                                }
                                qrdata = qrdata + "\n" + "Workshops Reg:";

                                if(workshopcount == 0){
                                    TextView tv = new TextView(getApplicationContext());
                                    tv.setText("No registered workshops found!");
                                    tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                    tv.setTextSize(18);
                                    tv.setTextColor(Color.WHITE);
                                    workshopll.addView(tv);
                                }
                                for (int i=0; i<workshopcount; i++){
                                    String work = workshops.getString(i);
                                    Log.i("JSONN", work);
                                    qrdata = qrdata + " " + work + ",";
                                    TextView tv = new TextView(getApplicationContext());
                                    tv.setText("• "+work);
                                    tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                                    tv.setTextSize(18);
                                    tv.setTextColor(Color.WHITE);
                                    workshopll.addView(tv);
                                }

                                qrdata = qrdata.substring(0,qrdata.length()-1) + ".";

                                sp.edit().putString("qrdata", qrdata).apply();
                                QRGEncoder qrgEncoder = new QRGEncoder(qrdata, null, QRGContents.Type.TEXT, 300);
                                try {
                                    // Getting QR-Code as Bitmap
                                    bitmap = qrgEncoder.encodeAsBitmap();
                                    // Setting Bitmap to ImageView
                                    qrImage.setImageBitmap(bitmap);
                                    qrz.setImageBitmap(bitmap);
                                    ((CardView) findViewById(R.id.qrcard)).setEnabled(true);
                                    qrpr.setVisibility(View.GONE);
                                } catch (WriterException e) {
                                    Log.v("Exception", e.toString());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    qq.add(jj);
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
            ((TextView)findViewById(R.id.collegetv)).setText(response.getString("college"));

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
                tv.setText("• "+event);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                eventll.addView(tv);
            }

            if(workshopcount == 0){
                TextView tv = new TextView(getApplicationContext());
                tv.setText("No workshops registered!");
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/sofiaprolight.otf"));
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                workshopll.addView(tv);
            }
            for (int i=0; i<workshopcount; i++){
                String work = workshops.getString(i);
                TextView tv = new TextView(getApplicationContext());
                tv.setText("• "+work);
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

    public static String md5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

// Login https://api.jsonbin.io/b/5c67b234a83a29317735e26c/1
// Details https://api.jsonbin.io/b/5c67b201a83a29317735e24c/1