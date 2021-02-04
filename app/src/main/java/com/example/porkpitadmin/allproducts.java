package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class allproducts extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner forlocations2;
    DrawerLayout nav;
    FirebaseAuth fAuth;
    String htm;
    String loc ;
    String delkey;
    StorageReference storageReference;
    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseUser using;
    FirebaseFirestore fStore;
    ImageView himage;
    String userid;
    TextView pname, pmail;

    @Override
    protected void onStart() {

        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allproducts);


        fAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclermenumain);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        using = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        forlocations2 = findViewById(R.id.locspinner2);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoryarray2, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forlocations2.setAdapter(arrayAdapter);
        forlocations2.setOnItemSelectedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loaditems();
            }
        },500);




    }

    private void loaditems() {

        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(fStore.collection("Products").document("all").collection(loc), products.class).build();
        FirestoreRecyclerAdapter<products, productviewholder> adapter = new FirestoreRecyclerAdapter<products, productviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productviewholder holder, int position, @NonNull final products model) {
                holder.txtpname.setText("Product " + model.getName());
                holder.txtpprice.setText("Price " + model.getPrice());
                holder.txtpdescription.setText("Category " + model.getCategory());
                delkey = model.getRandomKey();

//htm="https://firebasestorage.googleapis.com/v0/b/porkpit-fe8ac.appspot.com/o/Users%2FbG6dcaszUfaJchQOBGLhxbLMDHG3%2Fprofile.jpg?alt=media&token=206700b5-1258-456c-bf8f-3de94457af3d";


                Picasso.get().load(model.getFilepath()).into(holder.pimage);
                //   Toast.makeText(home.this, htm, Toast.LENGTH_SHORT).show();
                //Toast.makeText(home.this, model.getFilepath(), Toast.LENGTH_SHORT).show();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(allproducts.this,productdetails.class);
                        intent.putExtra("pid",model.getRandomKey());
                        intent.putExtra("Cats",model.getCategory());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem, parent, false);
                productviewholder holder = new productviewholder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public void oncartclick() {

        android.app.AlertDialog dialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle("Cart Opptions")
                .setMessage("Remove Product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        fStore.collection("Products").document(delkey)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(allproducts.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(allproducts.this, "Error deleting document", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        loc=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, loc, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loaditems();
            }
        },500);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}







