package com.example.porkpitadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {
Button toorder,toadd,viewss,topays,tousr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toadd=findViewById(R.id.addbtn);
        toorder=findViewById(R.id.orderbtn);
        tousr=findViewById(R.id.viewusrs);
        topays=findViewById(R.id.checkpaid);

viewss=findViewById(R.id.viewallbtn);
viewss.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),allproducts.class));
    }
});
        toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),addproduct.class));
            }
        });
        toorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),orders.class));
            }
        });



        tousr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),allusers.class));
            }
        });
        topays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),orderpaid.class));
            }
        });


    }
}