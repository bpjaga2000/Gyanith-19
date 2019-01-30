package com.barebrains.gyanith19;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity  {


    EditText uid,pwd;
    Button signin;

    MessageDigest m;
    String hash,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uid=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        signin=findViewById(R.id.email_sign_in_button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw=pwd.getText().toString();

                try{
                    m=MessageDigest.getInstance("MD5");
                }catch (Exception e){}

                m.update(pw.getBytes(),0, pw.length());
                hash=String.format("%032X",new BigInteger(1,m.digest()));
            }
        });

    }

}

