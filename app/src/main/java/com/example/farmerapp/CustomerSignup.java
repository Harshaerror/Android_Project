package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerSignup extends AppCompatActivity {
    TextView tv1;
    Button b1;
    TestAdapter adapter;
    EditText name,address,mobile,email,pass;

    String aname,aaddress,amobile,aemail,apass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        tv1=(TextView) findViewById(R.id.txt_affilatersingin);

        name=(EditText)findViewById(R.id.txt_aname);
        address=(EditText)findViewById(R.id.txtaaddress);
        mobile=(EditText)findViewById(R.id.txt_amobile);
        email=(EditText)findViewById(R.id.txt_aemail);
        pass=(EditText)findViewById(R.id.txt_apassword);

        b1=(Button)findViewById(R.id.btn_affjoin);


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerSignup.this,CustomerLogin.class);
                startActivity(i);
            }
        });
        try {
            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();

            // autoincrementid();

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    aname = name.getText().toString();
                    aaddress = address.getText().toString();
                    amobile = mobile.getText().toString();
                    aemail = email.getText().toString();
                    apass = pass.getText().toString();

                    String mpass = null;
                    Cursor cursor = adapter.selectDelivery();
                    while (cursor.moveToNext()) {
                        mpass = cursor.getString(2).toString();
                        if (amobile.equalsIgnoreCase(mpass)) {

                            Toast.makeText(getApplicationContext(), "This User is Already Registered..!", Toast.LENGTH_SHORT).show();
                            Log.w("5", "ok");
                            return;
                        }
                    }


                    if (TextUtils.isEmpty(aname)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Name..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(aaddress)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Address Details..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(aemail)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Email  Address..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(amobile)) {
                        Toast.makeText(getApplicationContext(), "Please Enter  Mobile Number..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (amobile.length() != 10) {
                        Toast.makeText(getApplicationContext(), "Please Enter 10 Digit Mobile Number.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(apass)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Password..", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        RegisterUser();

                    }
                }
            });


        } catch (Exception e) {
        }


    }


    private void RegisterUser() {

        final ProgressDialog dialog =
                new ProgressDialog(CustomerSignup.this);
        // dialog.setIcon(R.drawable.add);
        dialog.setTitle("Processing Your Request...");
        dialog.setMessage("Please wait...");
        dialog.show();

        long i = adapter.InsertDelivery(aname,aaddress,amobile,aemail,apass);
        //Toast.makeText(getApplicationContext(), " Register Sucessfully..." + i, Toast.LENGTH_SHORT).show();

        final Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
                String mpass = null;

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.
                        Builder(CustomerSignup.this);

                alertDialogBuilder.setTitle("Registration Successfully ..");

                final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent i = new Intent(CustomerSignup.this, CustomerLogin.class);
                                startActivity(i);


                            }
                        });
                android.app.AlertDialog alDialog = alertDialogBuilder.create();
                alDialog.show();
            }

        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 6000);





    }
}