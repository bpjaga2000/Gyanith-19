package com.barebrains.gyanith19;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    Context context;
    WebView webview;
    String s,token="",id="",ex="";
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        i=getIntent();
        id=i.getStringExtra("id");
        ex=i.getStringExtra("ex");

        context=this;

        webview=findViewById(R.id.wv);
        webview.getSettings().setJavaScriptEnabled(true);

        s="https://gyanith.org/register_form.php?id="+id+"&q="+token;

        if(ex.equals("Tg"))
            s="https://gyanith.org/assets/files/topics.pdf";
        else if(ex.equals("W7"))
            s="https://www.thecollegefever.com/events/3d-printing-workshop-cOWPj0G8sy";

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ((ProgressBar)findViewById(R.id.progressBar2)).setVisibility(View.GONE);
            }
        });
        webview.loadUrl(s);

    }
}
