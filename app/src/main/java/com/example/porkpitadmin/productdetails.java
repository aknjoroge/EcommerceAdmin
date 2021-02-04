package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class productdetails extends AppCompatActivity {
    String pid;
    String catego;
    String htmls;
    FirebaseFirestore fStore;
    Uri imageuri;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    ImageView detimg;
    FirebaseUser using;
    String dfilepath;
    TextView forname,forprice,forcats;
    String userid;
    Button pprice,ppicture,pdelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userid = fAuth.getCurrentUser().getUid();
        ppicture=findViewById(R.id.pchnageimbtn);
        pprice=findViewById(R.id.pchangepricebtn);
        pdelete=findViewById(R.id.pdeletbtn);

forname=findViewById(R.id.detailproductname);
detimg=findViewById(R.id.detailproductimage);
        forcats=findViewById(R.id.detailproductprice);
        forprice=findViewById(R.id.detailproductdescription);
        pid=getIntent().getStringExtra("pid");
        catego=getIntent().getStringExtra("Cats");


        uploaddate();

       ppicture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
changeppic();
           }
       });

        pprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
changeprice();
            }
        });
        pdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oncartclick();
            }
        });



    }

    private void changeppic() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    private void changepicrootpath() {

        Map<String,Object> newpath =new HashMap<>();
        newpath.put("filepath",dfilepath);

        final DocumentReference documentReference =  fStore.collection("Products").document("all").collection(catego).document(pid);
        documentReference.set(newpath, SetOptions.merge());
        Toast.makeText(productdetails.this, "updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Toast.makeText(this, "Data received", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
                imageuri = data.getData();
                detimg.setImageURI(imageuri);
                uploadimage(imageuri);

            }catch (Exception e){

                Toast.makeText(this, "Run time exception gained", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }
    public void uploadimage(final Uri imageuri) {

        final StorageReference fileref = storageReference.child("productsupload/"+pid+"/product.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Uri downloadUrl = uri;
                        String durl =downloadUrl.toString();



                        Map<String,Object> newpath =new HashMap<>();
                        newpath.put("filepath",durl);

                        final DocumentReference documentReference =  fStore.collection("Products").document("all").collection(catego).document(pid);
                        documentReference.set(newpath, SetOptions.merge());
                        Toast.makeText(productdetails.this, "updated successfully", Toast.LENGTH_SHORT).show();



                        //Do what you want with the url
                    }


                });
            }

        });




//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//
//                        dfilepath=fileref.getDownloadUrl().toString();
//
//                        Map<String,Object> newpath =new HashMap<>();
//                        newpath.put("filepath",dfilepath);
//
//                        final DocumentReference documentReference =  fStore.collection("Products").document("all").collection(catego).document(pid);
//                        documentReference.set(newpath, SetOptions.merge());
//                        Toast.makeText(productdetails.this, "updated successfully", Toast.LENGTH_SHORT).show();
//
//
//                       // changepicrootpath();
//
//
//                        //  Picasso.get().load(uri).into(profileimage);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(productdetails.this, "Failed in getting url", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if(task.isSuccessful()){
//                            dfilepath=task.getResult().toString();
//
//                        }
//
//                    }
//                });
//                // b = imageuri.toString();
//                Toast.makeText(productdetails.this, "IMAGE UPLOADING", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(productdetails.this, "UPLOAD ERROOR", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void changeprice() {
        final EditText resetpassw= new EditText(this);
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Update Price?")
                .setMessage("Enter Amount.")
                .setView(resetpassw)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(resetpassw.getText().toString())) {
                            String takenew = resetpassw.getText().toString();

                            Map<String,Object> newp =new HashMap<>();
                            newp.put("price",takenew);

                            final DocumentReference documentReference =  fStore.collection("Products").document("all").collection(catego).document(pid);

                            documentReference.set(newp, SetOptions.merge());


                        }
                        else {
                            Toast.makeText(productdetails.this, "Field is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(productdetails.this, "Price change Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


    }

    public void oncartclick(){

        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Delete Product")
                .setMessage("Remove ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fStore.collection("Products").document("all").collection(catego).document(pid)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(productdetails.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),allproducts.class));

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(productdetails.this, "Error deleting document", Toast.LENGTH_SHORT).show();
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

    private void uploaddate() {


            final DocumentReference documentReference = fStore.collection("Products").document("all").collection(catego).document(pid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    String nametake=documentSnapshot.getString("name");
                    String pricetake =documentSnapshot.getString("price");
                    String cattake =documentSnapshot.getString("category");

                    forname.setText(nametake);
                    forcats.setText(cattake);
                    forprice.setText(pricetake);

                    htmls=documentSnapshot.getString("filepath");
                    // Toast.makeText(productdetails.this, htmls, Toast.LENGTH_SHORT).show();
                    Picasso.get().load(htmls).into(detimg);



                }
            });

    }
}