package com.example.porkpitadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class paymentdetails extends AppCompatActivity {
    FirebaseUser using;
    FirebaseAuth fAuth;
    String userid;
    FirebaseFirestore fStore;
    Button fordone;


    TextView uname,uprice,utime,uorder,umail,uloca,utype,uphone;
    String pid,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetails);
        fAuth = FirebaseAuth.getInstance();
        using=fAuth.getCurrentUser();
        userid = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();
        pid=getIntent().getStringExtra("pid");
        uid=getIntent().getStringExtra("userid");
fordone=findViewById(R.id.detailpaybtn);
fordone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(paymentdetails.this, "contact admin", Toast.LENGTH_SHORT).show();
    }
});

        uname=findViewById(R.id.payordertnametxt);
        uprice=findViewById(R.id.payorderpricetxt);
        uphone=findViewById(R.id.payorderphonetxt);
        utime=findViewById(R.id.payordertimetxt);
        uorder=findViewById(R.id.payorderorderstxt);
        umail=findViewById(R.id.payordermailtxt);
        uloca=findViewById(R.id.payorderloctxt);
        utype=findViewById(R.id.payordermesotxt);



        loaduserdata();
        getlist();
    }

    private void loaduserdata() {

        final DocumentReference documentReference = fStore.collection("Paidorders").document(pid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String nametake=documentSnapshot.getString("paidname");
                String pricetake =documentSnapshot.getString("tobepaid");
                String cattake =documentSnapshot.getString("time");
                String datetake=documentSnapshot.getString("date");
                String phonetake =documentSnapshot.getString("userphone");
                String mailtake =documentSnapshot.getString("mail");
                String loctake =documentSnapshot.getString("location");
                String transtake=documentSnapshot.getString("transactiontype");

uname.setText("name "+nametake);
uprice.setText("price " +pricetake);
uphone.setText("phone "+phonetake);
utime.setText("time is "+cattake+"date is "+datetake);
umail.setText("mail" +mailtake);
uloca.setText(" location "+loctake);
utype.setText("transaction " +transtake);




            }
        });




    }

    private void getlist() {
        userid = fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fStore.collection("Paidorders").document("all").collection("saveone").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String ordertake=documentSnapshot.getString("orders");

                uorder.setText("orders : "+ordertake);

            }
        });





    }
}