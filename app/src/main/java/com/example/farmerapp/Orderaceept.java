package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Orderaceept extends AppCompatActivity {
    TextView oid,name,mobile;
    TestAdapter adapter;

    String smobile;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    String contact;
    Button b1;
    static int PERMISSION_CODE= 100;
    Context context=this;
    String dmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderaceept);
        oid = (TextView) findViewById(R.id.orderid);
        name = (TextView) findViewById(R.id.dename);
        mobile = (TextView) findViewById(R.id.mobilenumber);

        b1 = (Button) findViewById(R.id.btn_call);

        if (ContextCompat.checkSelfPermission(Orderaceept.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Orderaceept.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);

        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(),"Hii Nikhil",Toast.LENGTH_SHORT).show();;
                //  call();
                String phoneno = mobile.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);
            }
        });



        try {
            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();
            Intent intent = getIntent();
            String str = intent.getStringExtra("message_key");
            oid.setText("                   " + str);

            Cursor c = adapter.getAlldeliveryaccepted(str);
            while (c.moveToNext()) {
                name.setText("             " + c.getString(1));
                mobile.setText("       " + c.getString(9));


            }
           /* smobile=mobile.getText().toString();
            Cursor cc=adapter.getDboyname(smobile);
            while (cc.moveToNext()){
                name.setText("       " + cc.getString(0));
            }*/

        } catch (Exception e) {}
    }


    private void addnameondeliveryboy() {
        smobile=mobile.getText().toString();
        Cursor c=adapter.getDeliveryboydetaisl(smobile);
        while (c.moveToNext()){
            name.setText("     "+c.getString(0));
        }

    }

}