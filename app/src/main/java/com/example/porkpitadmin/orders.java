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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class orders extends AppCompatActivity {
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
        setContentView(R.layout.activity_orders);


        fAuth = FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recyclermenucart);
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

        FirestoreRecyclerOptions<forcart> options = new FirestoreRecyclerOptions.Builder<forcart>()
                .setQuery(fStore.collection("AdminOrderList"),forcart.class).build();
        FirestoreRecyclerAdapter<forcart,cartviewholder> adapter= new FirestoreRecyclerAdapter<forcart, cartviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartviewholder holder, int position, @NonNull final forcart model) {

                holder.txtusrname.setText("User "+model.getUsername());
                holder.txtphone.setText("# "+model.getPhone());
                holder.txttime.setText("Time "+model.getTime());
                holder.txtdate.setText("Date "+model.getDate());
                holder.txtproductname.setText(model.getProductname());
                holder.txtpprice.setText("Price "+model.getPrice());
                holder.txtpamount.setText("Total "+model.getAmount());
                holder.txtproductid.setText("PID"+model.getProductid());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      oncartclick();



                    }
                });
            }

            @NonNull
            @Override
            public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
                cartviewholder holder =new cartviewholder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }



    public void oncartclick(){

        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Order Opptions")
                .setMessage("Order Done?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(orders.this, "Contact admin", Toast.LENGTH_SHORT).show();

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