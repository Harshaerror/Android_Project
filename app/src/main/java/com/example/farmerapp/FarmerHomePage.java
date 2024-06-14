package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FarmerHomePage extends AppCompatActivity {
Button addprod,profile,aboutus,logout,order;
TestAdapter adapter;
TextView smobile;
String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home_page);

        addprod=(Button) findViewById(R.id.addproduct);
        smobile=(TextView) findViewById(R.id.txt_id);
        profile=(Button)findViewById(R.id.profile);
        aboutus=(Button)findViewById(R.id.aboutus);
        logout=(Button)findViewById(R.id.logout);
        order=(Button)findViewById(R.id.vieworder);


        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerHomePage.this, AddProduct.class);
                startActivity(i);
            }
        });

        try {

            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();
            Bundle bundle = getIntent().getExtras();
            mobile = bundle.getString("Key");
            smobile.setText(mobile);



            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FarmerHomePage.this, FarmerProfile.class);
                    i.putExtra("Key",mobile);
                    startActivity(i);
                }
            });

            aboutus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FarmerHomePage.this, Aboutus.class);
                    i.putExtra("Key",mobile);
                    startActivity(i);
                }
            });

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FarmerHomePage.this, CustomerOrder.class);
                    i.putExtra("Key",mobile);
                    startActivity(i);
                }
            });


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerHomePage.this);


                    builder.setMessage("Do you want to Logout ?");

                    builder.setTitle("Alert !");

                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                        Intent i=new Intent(FarmerHomePage.this,FarmerLogin.class);
                        startActivity(i);

                    });


                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
            });




        }catch (Exception e){}



    }
}