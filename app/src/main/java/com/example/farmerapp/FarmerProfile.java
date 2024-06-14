package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FarmerProfile extends AppCompatActivity {
    TestAdapter adapter;
    String mobilenum;
    EditText name,address,mobile,email,pass;
    Button edit,update;
    String smobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
        name=(EditText)findViewById(R.id.txt_aanamep);
        address=(EditText)findViewById(R.id.txtaaaddressp);
        mobile=(EditText)findViewById(R.id.txt_aamobilep);
        email=(EditText)findViewById(R.id.txt_aaemailp);
        pass=(EditText)findViewById(R.id.txt_aapasswordp);

        edit=(Button) findViewById(R.id.btn_btnedit);
      //  update=(Button)findViewById(R.id.btn_updateprofile);
        try {

            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();
            Bundle bundle = getIntent().getExtras();
            mobilenum = bundle.getString("Key");
            //   smobile.setText(mobile);

            Cursor c=adapter.getadmin(mobilenum);
            while (c.moveToNext()) {
                name.setText(c.getString(0));
                address.setText(c.getString(1));
                mobile.setText(c.getString(2));
                email.setText(c.getString(3));
                pass.setText(c.getString(4));

            }


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FarmerProfile.this, FarmerHomePage.class);

                    i.putExtra("Key", mobilenum);
                    startActivity(i);



                }
            });
        }catch (Exception e){}



    }
}