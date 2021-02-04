package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class orderpaid extends AppCompatActivity {
    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseUser using;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String keptkey;
    String userid;
    int totalprice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpaid);
        fAuth = FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recyclermenuorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        using=fAuth.getCurrentUser();

        userid = fAuth.getCurrentUser().getUid();

        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirestoreRecyclerOptions<fororders> options = new FirestoreRecyclerOptions.Builder<fororders>()
                .setQuery(fStore.collection("Paidorders"),fororders.class).build();
        FirestoreRecyclerAdapter<fororders,ordersviewholder> adapter= new FirestoreRecyclerAdapter<fororders, ordersviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ordersviewholder holder, int position, @NonNull final fororders model) {



                holder.txtousrname.setText("Name: "+model.getPaidname());
                holder.txtophone.setText("Phone: "+model.getUserphone());
                holder.txtotime.setText("time: "+model.getTime());
                holder.txtodate.setText("Date: "+model.getDate());
                holder.txtomailzshh.setText("Mail: "+model.getMail());
                holder.txrsolocation.setText("Location: "+model.getLocation());
                holder.txtorallpaid.setText("amount: "+model.getTobepaid()+"/=");
                holder.txtotransacts.setText("Transaction :"+model.getTransactiontype());






                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(orderpaid.this,paymentdetails.class);
                        intent.putExtra("pid",model.getRandomkey());
                        intent.putExtra("userid",model.getUserid());
                        startActivity(intent);


                    }
                });
            }

            @NonNull
            @Override
            public ordersviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersitem,parent,false);
                ordersviewholder holder =new ordersviewholder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    public void oncartclick(){

        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Completed ?")
                .setMessage("End Order")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(orderpaid.this, "Contact admin", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}