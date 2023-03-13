package com.example.monease;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class myreceive extends BroadcastReceiver {
    DatabaseReference authenticationdb;
    public String type,date,name;
    public Float amt;
    DataSnapshot snapshot;
    @Override

    public void onReceive(Context context, Intent intent) {



        if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
            Bundle b=intent.getExtras();
            SmsMessage[] msgs=null;
            String msg_from;
            if(b!=null)
            {
                try{
                    Object[] pdus=(Object[]) b.get("pdus");
                    msgs=new SmsMessage[pdus.length];
                    for(int i=0;i<= msgs.length;i++)
                    {
                        msgs[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from=msgs[i].getOriginatingAddress();
                        String msgbody=msgs[i].getMessageBody();
                        if( msgbody.contains("Dear SBI User,") || msgbody.contains("Dear SBI UPI User")) {
                            authenticationdb = FirebaseDatabase.getInstance().getReference();

                            type = msgbody.substring(29, 37);
                            if (type.contentEquals("-debited")) {
                                type = "DEBIT";
                                amt = Float.parseFloat(msgbody.substring(43, msgbody.indexOf(" ", 44)));
                                date = msgbody.substring(msgbody.indexOf(" ", 44) + 4, msgbody.indexOf(" ", 55));
                                Float cdate=Float.parseFloat(date.substring(0,2));

                                date = date.substring(0, 2) + " " + date.substring(2, 5) + " 20" + date.substring(5);
                                name = msgbody.substring(msgbody.indexOf("transfer to") + 12, msgbody.indexOf(" Ref No"));

//
                            } else {
                                type = "CREDIT";
                                amt = Float.parseFloat(msgbody.substring(45, msgbody.indexOf(" ", 45)));
                                date = msgbody.substring(msgbody.indexOf(" ", 46) + 4, msgbody.indexOf(" ", 53));
                                date = date.substring(0, 2) + " " + date.substring(2, 5) + " 20" + date.substring(5);
                                name = "Me";
                            }
                            contact_model db = new contact_model(date,amt,type,name);
                            authenticationdb.child("Users").child("SBI").push().setValue(db);


                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


//           Cursor cursor=getContentResolver().query(Uri.parse("content://sms"),null,null,null,null);
//           cursor.moveToFirst();
//           while(!cursor.isAfterLast())
//           {   String stringmessage=cursor.getString(12);
//
//


//               }
//               cursor.moveToNext();
//           }
//
//           Toast.makeText(context,type+amt+date+name+"message found",Toast.LENGTH_SHORT).show();
//       }
            }
        }
//
    }


}
