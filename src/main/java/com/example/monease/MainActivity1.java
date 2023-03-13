package com.example.monease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.monease.Models.Transactions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity1<onRequestPermissionResult> extends AppCompatActivity {

    Button right;
    ArrayList<contact_model> arrcontact=new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    TextView t1;
    String str,mll,dll;
    Float tot;
    FirebaseAuth mAuth;
    float todaytot=0,mtot=0;



    EditText dlimit,mlimit,cbalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        getSupportActionBar().setTitle("Transactions");
        mAuth=FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationChannel channel=new NotificationChannel("notifications","mysotifictions", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager=getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerContact);
        LinearLayoutManager linear=new LinearLayoutManager(getApplicationContext());
        linear.setReverseLayout(true);
        linear.setStackFromEnd(true);

        recyclerView.setLayoutManager(linear);

//        arrcontact.add(new contact_model("23 dec 2023", Float.parseFloat("240.0"), "Credited", "Niranjan"));
//        arrcontact.add(new contact_model("23 dec 2023", Float.parseFloat("240.0"), "Credited", "Niranjan"));
//        arrcontact.add(new contact_model("23 dec 2023", Float.parseFloat("240.0"), "Credited", "Niranjan S Naik"));
//        arrcontact.add(new contact_model("23 dec 2023", Float.parseFloat("240.0"), "Credited", "H Vidhyasagar"));

//           String total= String.valueOf(db.child("Users").child("SBI").child("balance"));
//
//        Toast.makeText(this, "+"+tot, Toast.LENGTH_SHORT).show();
//         tot=Float.parseFloat(total);





        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrcontact.clear();

                t1=findViewById(R.id.balance);
                str=t1.getText().toString();
                tot=Float.parseFloat(str);



              String total=snapshot.child("Users").child("balance").getValue(String.class);
                tot=Float.parseFloat(total);
//                String todaytotal=snapshot.child("Users").child("todaytot").getValue(String.class);
//                todaytot=Float.parseFloat(todaytotal);
//                String dl=snapshot.child("Users").child("dlimit").getValue(String.class);
//                String ml=snapshot.child("Users").child("mlimit").getValue(String.class);
//                Toast.makeText(MainActivity1.this, dl+ml, Toast.LENGTH_SHORT).show();

                  mll=snapshot.child("Users").child("mlimit").getValue(String.class);
                  dll=snapshot.child("Users").child("dlimit").getValue(String.class);

                  Toast.makeText(MainActivity1.this, "Monthly Limit: "+mll+"   Daily Limit: "+dll, Toast.LENGTH_SHORT).show();

//
//                  Float fdlimt=Float.parseFloat(dll);
//                  Float fmlimt=Float.parseFloat(mll);






              for (DataSnapshot child : snapshot.child("Users").child("SBI").getChildren()) {

                  float exc = 0;
                  final String date = child.child("date").getValue(String.class);
                  final Float amount = child.child("amount").getValue(Float.class);
                  final String type = child.child("type").getValue(String.class);
                  final String name = child.child("name").getValue(String.class);
                  if (type.contentEquals("DEBIT")) {
                      arrcontact.add(new contact_model(date, amount, "Debited", name));
                      tot = tot - amount;
                      t1.setText(tot.toString());
                      todaytot = todaytot + amount;
                      mtot = mtot + amount;

                  } else {
                      arrcontact.add(new contact_model(date, amount, "Credited", name));
                      tot = tot + amount;
                      t1.setText(tot.toString());
                  }


                  recyclecontactAdapter adapter = new recyclecontactAdapter(getApplicationContext(), arrcontact);
                  recyclerView.setAdapter(adapter);

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
             Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "PERMISION DENIED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                Dialog dialog2=new Dialog(MainActivity1.this);
                dialog2.setContentView(R.layout.dialog_settings);
                dialog2.show();

                dlimit= dialog2.findViewById(R.id.dailyLimit);
                mlimit=dialog2.findViewById(R.id.monthlyLimit);

                Button done =dialog2.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        db.child("Users").child("dlimit").setValue(dlimit.getText().toString());
                        db.child("Users").child("mlimit").setValue(mlimit.getText().toString());
                        dlimit.setText(dll);
                        mlimit.setText(mll);
                        dialog2.dismiss();
                    }
                });

                Button lo=dialog2.findViewById(R.id.lofOutbtn);
                lo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.signOut();
                        dialog2.dismiss();
                        Intent intent = new Intent(MainActivity1.this,signupActivity.class);
                        startActivity(intent);
                    }
                });






                break;
            case R.id.add:
                Dialog dialog1=new Dialog(MainActivity1.this);
                dialog1.setContentView(R.layout.dialog_layout);
                dialog1.show();

                EditText dialogname,dialogamount;
                RadioGroup group = dialog1.findViewById(R.id.group);
                dialogname=dialog1.findViewById(R.id.dialogName);
                dialogamount=dialog1.findViewById(R.id.dialogAmount);


                DatabaseReference authenticationdb;

                Button dialogadd = dialog1.findViewById(R.id.dialogAdd);
                dialogadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(group.getCheckedRadioButtonId()==R.id.radioCredit) {
                      Calendar calendar=Calendar.getInstance();
                      String date=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.YEAR));
                          Float amt=Float.parseFloat(dialogamount.getText().toString());

                          String type="CREDIT";
                          String name=dialogname.getText().toString();
//                            arrcontact.add(new contact_model(cal.toString(), Float.parseFloat(dialogamount.getText().toString()), "Credited", dialogname.getText().toString()));
                            contact_model ob=new contact_model(date,amt,type,name);
                            db.child("Users").child("SBI").push().setValue(ob);
                            Toast.makeText(MainActivity1.this, "added to db", Toast.LENGTH_SHORT).show();
                            tot=tot+Float.parseFloat(dialogamount.getText().toString());
                            t1.setText(tot.toString());
                        }
                        else {
//                            arrcontact.add(new contact_model(cal.toString(), Float.parseFloat(dialogamount.getText().toString()), "Debited", dialogname.getText().toString()));
                            Calendar calendar=Calendar.getInstance();
                            String date=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.YEAR));
                            Float amt=Float.parseFloat(dialogamount.getText().toString());
                            String type="DEBIT";
                            String name=dialogname.getText().toString();
                            contact_model ob=new contact_model(date,amt,type,name);
                            db.child("Users").child("SBI").push().setValue(ob);
                            tot=tot-Float.parseFloat(dialogamount.getText().toString());
                            t1.setText(tot.toString());
                        }
                        dialog1.dismiss();
                    }
                });

                break;
            case R.id.summary:
//

                Intent intent=new Intent(MainActivity1.this,MainActivity2.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}