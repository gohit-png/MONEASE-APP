package com.example.monease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class signupActivity extends AppCompatActivity {

    EditText tu1;
    EditText tn1;
    Button b1;
    CountryCodePicker cpp;

    boolean valdate(){
        if (tu1.getText().toString().isEmpty()){
            Toast.makeText(signupActivity.this, "Enter Username Please!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tn1.getText().toString().isEmpty()){
            Toast.makeText(signupActivity.this, "Enter Phone number Please!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        tu1=findViewById(R.id.textUsername);
        tn1=findViewById(R.id.textPhoneNumber);
        b1=(Button)findViewById(R.id.btnSignUp);
        cpp=(CountryCodePicker)findViewById(R.id.cpp);
        cpp.registerCarrierNumberEditText(tn1);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (valdate()) {

                    Intent intent = new Intent(signupActivity.this, Authenticate.class);
                    intent.putExtra("mobile", cpp.getFullNumberWithPlus().replace(" ", ""));
                    intent.putExtra("uname", tu1.getText().toString());
                    startActivity(intent);

                }
            }
        });

        if(mAuth.getCurrentUser()!=null){
        Intent intent= new Intent(signupActivity.this,MainActivity1.class);
        startActivity(intent);
        }
    }
}