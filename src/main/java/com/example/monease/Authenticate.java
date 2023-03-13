package com.example.monease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monease.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Authenticate extends AppCompatActivity {

    TextView v2;
    EditText t2;
    Button b2;
    String Phonenumber;
    String Username;
    String otpid;
    FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        getSupportActionBar().hide();


        t2=(EditText) findViewById(R.id.t2);
        b2=(Button) findViewById(R.id.b2);

        Phonenumber=getIntent().getStringExtra("mobile").toString();
        Username=getIntent().getStringExtra("uname").toString();

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        v2=findViewById(R.id.uname);
        v2.setText("Welcome " +Username+" !");



        initiateOtp();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t2.getText().toString().isEmpty())
                    Toast.makeText(Authenticate.this, "BLANK FILED CANNOT BE PROCESSED", Toast.LENGTH_SHORT).show();
                else if(t2.getText().toString().length()!=6)
                    Toast.makeText(Authenticate.this, "INVALID OTP", Toast.LENGTH_SHORT).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void initiateOtp(){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Phonenumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;
                            }
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Authenticate.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Users user = new Users(Username,Phonenumber);

                    String id = task.getResult().getUser().getUid();

                    database.getReference().child("Users").child(id).setValue(user);

                    Dialog dialog2=new Dialog(Authenticate.this);
                    dialog2.setContentView(R.layout.balance_f);
                    dialog2.show();
                    Button btn=dialog2.findViewById(R.id.dialogOk);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText txt=dialog2.findViewById(R.id.bal);
                            database.getReference().child("Users").child("balance").setValue(txt.getText().toString());
//                            database.getReference().child("Users").child("todaytot").setValue(" ");

                            dialog2.dismiss();
                            startActivity(new Intent(Authenticate.this,MainActivity1.class));
                            finish();
                        }
                    });



                } else {
                    Toast.makeText(Authenticate.this, "SIGN UP FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}