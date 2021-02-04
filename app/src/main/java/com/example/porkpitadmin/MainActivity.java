package com.example.porkpitadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button forlogin;
    EditText email,pass;
    String  takeemail,takepass;
    FirebaseAuth fAuth;
    ProgressBar logadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        forlogin=findViewById(R.id.tohomebtn);

        email=findViewById(R.id.loginemail);
        pass=findViewById(R.id.loginpass);
        logadmin=findViewById(R.id.progressBarlog);
        takeemail=email.getText().toString();
        takepass=pass.getText().toString();

        fAuth = FirebaseAuth.getInstance();
        forlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(takeemail=email.getText().toString())){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty( takepass=pass.getText().toString())){
                    pass.setError("password required");
                    return;
                }
                takepass=pass.getText().toString();
                if(takepass.length()< 6 ){
                    pass.setError("password need to be above 6 characters");
                }

                if(takepass.equals("admin150") && takeemail.equals("admin@porkpit.com")){

                    logadmin.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword("admin@porkpit.com","admin150").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Admin Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),home.class));
                        }else {
                            logadmin.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "An error occured"
                                    +task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                        }

                    }
                });}
                else {
                    Toast.makeText(MainActivity.this, "incorrect Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}