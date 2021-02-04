package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addproduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
Button cancels ,uploading;
    Spinner forlocations;
ProgressBar uploadprog;
ImageView forupload;
    Uri imageuri;
    String a;
    String loc;
    Boolean hasimageset=false;
    String cdate,ctime;
    String randomkey;
    String dfilepath;

EditText name,price,description;
    FirebaseFirestore fStore;
    String forname,forprice,forDecription,forpath,forcategory;
    String userid;
    FirebaseAuth fAuth;
    String b;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        cancels=findViewById(R.id.cancel);
        uploading=findViewById(R.id.upload);
        forupload=findViewById(R.id.uploadimage);
        uploadprog=findViewById(R.id.adminprogressBar);
        storageReference = FirebaseStorage.getInstance().getReference();
        name=findViewById(R.id.uploadname);
        price=findViewById(R.id.uploadprice);
        forlocations=findViewById(R.id.locspinner);
        ArrayAdapter<CharSequence> arrayAdapter =ArrayAdapter.createFromResource(this,R.array.categoryarray,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forlocations.setAdapter(arrayAdapter);
        forlocations.setOnItemSelectedListener(this);
        description=findViewById(R.id.uploaddescription);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();

        cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
            }
        });
        uploading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty( forname=name.getText().toString())){
                   name.setError("field is required");

                }
                if(TextUtils.isEmpty(forDecription=description.getText().toString())){
                    description.setError("field is required");

                }
                if(TextUtils.isEmpty(forprice=price.getText().toString())){
                   price.setError("field is required");

                }
                if(hasimageset==false){
                    Toast.makeText(addproduct.this, "Select image to upload", Toast.LENGTH_SHORT).show();
                }
                if(loc.equals("Choose Category")){
                    Toast.makeText(addproduct.this, "choose category", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    Calendar calendar= Calendar.getInstance();
                    SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
                    cdate =currentdate.format(calendar.getTime());

                    SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                    ctime =currenttime.format(calendar.getTime());
                    randomkey= ctime+cdate;
uploadprog.setVisibility(View.VISIBLE);
                    uploadimage(imageuri);


            }}
        });

        forupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });



    }



    public void uploaddetails(){


        forname=name.getText().toString();
        forprice=price.getText().toString();
        forDecription=description.getText().toString();
        forpath=dfilepath;
        forcategory=loc;
        String id=userid;

        Map<String,Object> ordermap =new HashMap<>();
        ordermap.put("name",forname);
        ordermap.put("price",forprice);
        ordermap.put("randomKey",randomkey);
        ordermap.put("filepath",dfilepath);
        ordermap.put("category",forcategory);
        ordermap.put("description",forDecription);





        fStore.collection("Products").document("all").collection(loc).document(randomkey).set(ordermap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(addproduct.this, "All done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),home.class) );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(addproduct.this, "error updating Data one"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Toast.makeText(this, "Data received", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
                 imageuri = data.getData();
               forupload.setImageURI(imageuri);
               hasimageset=true;

               // uploadimage(imageuri);
            }catch (Exception e){

                Toast.makeText(this, "Run time exception gained", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }


    public void uploadimage(final Uri imageuri) {

        final StorageReference fileref = storageReference.child("productsupload/"+randomkey+"/product.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        dfilepath=fileref.getDownloadUrl().toString();
                        //    a = fileref.toString();

                        //  Picasso.get().load(uri).into(profileimage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addproduct.this, "Failed in getting url", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            dfilepath=task.getResult().toString();
                            uploaddetails();
                        }

                    }
                });
                // b = imageuri.toString();
                Toast.makeText(addproduct.this, "IMAGE UPLOADING", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addproduct.this, "UPLOAD ERROOR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loc=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, loc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}