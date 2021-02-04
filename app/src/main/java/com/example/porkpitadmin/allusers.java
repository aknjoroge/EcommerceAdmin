package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class allusers extends AppCompatActivity {
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
        setContentView(R.layout.activity_allusers);

        fAuth = FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recycleruseruser);
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

        FirestoreRecyclerOptions<forusers> options = new FirestoreRecyclerOptions.Builder<forusers>()
                .setQuery(fStore.collection("users"),forusers.class).build();
        FirestoreRecyclerAdapter<forusers,usersviewholder> adapter= new FirestoreRecyclerAdapter<forusers, usersviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull usersviewholder holder, int position, @NonNull final forusers model) {

                holder.txtusruname.setText("Name: "+model.getName());
                holder.txtuphone.setText("Phone: "+model.getPhone());
                holder.txtumail.setText("Mail :"+model.getmail());
                holder.txtuloc.setText("location: "+model.getLocation());



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oncartclick();
                    }
                });
            }

            @NonNull
            @Override
            public usersviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.useritem,parent,false);
                usersviewholder holder =new usersviewholder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }




    public void oncartclick(){

        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("User Opptions")
                .setMessage("Edit User?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(allusers.this, "Contact admin", Toast.LENGTH_SHORT).show();

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