package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {
Button framer,customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        framer=(Button) findViewById(R.id.btn_adminsignin);
        customer=(Button) findViewById(R.id.btn_deliverysignin);


        framer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseActivity.this, FarmerLogin.class);

                // on below line we are
                // starting a new activity.
                startActivity(i);
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseActivity.this, CustomerLogin.class);

                // on below line we are
                // starting a new activity.
                startActivity(i);
            }
        });





    }
}