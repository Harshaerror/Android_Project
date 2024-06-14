package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FarmerLogin extends AppCompatActivity {
    TextView tv1;
    //TextView tv1;


    Button b1,b2;
    EditText mobile,password;
    TestAdapter adapter;
    String smobile,spassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);
        tv1=(TextView) findViewById(R.id.txt_adminaccounnt);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FarmerLogin.this,FarmerSignup.class);
                startActivity(i);
            }
        });


        b1=(Button)findViewById(R.id.btn_adminsigin);
        mobile=(EditText)findViewById(R.id.txt_adminmobile);
        password=(EditText)findViewById(R.id.txt_adminpassword);



        try {

            adapter=new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    smobile = mobile.getText().toString();
                    spassword= password.getText().toString();

                    if(TextUtils.isEmpty(smobile))
                    {
                        mobile.setError("Mobile is Required.");
                        return;
                    }

                    if(TextUtils.isEmpty(spassword))
                    {
                        password.setError("Password is Required.");
                        return;
                    }

                    int i = adapter.checkAdminLogin(smobile,spassword);
                    if (i == 1) {


                        userlogin();
                        return;

                    }


                    else{
                        Toast.makeText(FarmerLogin.this, "Invalid Mobile Or Password", Toast.LENGTH_SHORT).show();
                        return;

                    }


                }
            });

        }catch (Exception e){}


    }

    private void userlogin() {
        final ProgressDialog dialog =
                new ProgressDialog(FarmerLogin.this);
        //  dialog.setIcon(R.drawable.login);
        dialog.setTitle("Login");
        dialog.setMessage("Please wait  Login is Processing...");
        dialog.show();

        final Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
                String mpass = null;

                Intent i = new Intent(FarmerLogin.this, FarmerHomePage.class);
                i.putExtra("Key",smobile);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "Login Successfull..." , Toast.LENGTH_SHORT).show();

                mobile.setText("");
                password.setText("");


            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 6000);


    }
}