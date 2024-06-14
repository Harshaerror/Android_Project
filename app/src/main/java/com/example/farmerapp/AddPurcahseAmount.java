package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPurcahseAmount extends AppCompatActivity {
    EditText did,dname,dtype,ddate,dsize,dqty,dlocation,dshipping,ddistance,mob,damount;
    Spinner sp1;
    Button b1;
    TestAdapter adapter;

    String sdid,sdname,sdtype,sddate,sdsize,sdqty,sdlocation,sdshipping,sddistance,smob,sdamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purcahse_amount);
        did=(EditText) findViewById(R.id.txt_itemida);
        dname=(EditText) findViewById(R.id.txt_itemnamea);
        dtype=(EditText) findViewById(R.id.txt_itemtypea);
        ddate=(EditText)findViewById(R.id.txt_dshipatea);
        dsize=(EditText) findViewById(R.id.txt_itemsizea);
        dqty=(EditText) findViewById(R.id.txt_itemqtya);
        dlocation=(EditText) findViewById(R.id.txt_yourlocationa);
        dshipping=(EditText) findViewById(R.id.txt_itemshippa);
   //     ddistance=(EditText) findViewById(R.id.txt_distancea);
        mob=(EditText)findViewById(R.id.txt_mob);
        damount=(EditText)findViewById(R.id.txt_amounta);


        b1=(Button) findViewById(R.id.btn_acceptdelivery);

        try {
            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();

            autoincrement();

            Intent intent = getIntent();
            String str = intent.getStringExtra("message_key");


            Intent intentt = getIntent();
            String strrr = intent.getStringExtra("Key");
            mob.setText(strrr);

            Cursor cursor = adapter.getAlldeliverydetails(str);

            while (cursor.moveToNext()) {
                //did,dname,dtype,ddate,dsize,dqty,dlocation,dshipping,ddistance,damount;
                //  did.setText(cursor.getString(0));
                dname.setText(cursor.getString(1));
                dtype.setText(cursor.getString(2));
                ddate.setText(cursor.getString(3));
                dsize.setText(cursor.getString(4));
                dqty.setText(cursor.getString(5));
                dlocation.setText(cursor.getString(6));
                dshipping.setText(cursor.getString(7));
             //   ddistance.setText(cursor.getString(8));



            }

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sdid = did.getText().toString();
                    sdname = dname.getText().toString();
                    sdtype=dtype.getText().toString();
                    sddate=ddate.getText().toString();
                    sdsize = dsize.getText().toString();
                    sdqty = dqty.getText().toString();
                    sdlocation = dlocation.getText().toString();
                    sdshipping = dshipping.getText().toString();
                   // sddistance = ddistance.getText().toString();
                    smob=mob.getText().toString();
                    sdamount=damount.getText().toString();

                    if (TextUtils.isEmpty(sdamount)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Delivery Charges..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else {
                        Insertdelivery();

                    }


                }
            });

        }catch (Exception e){}
    }

    private void autoincrement() {

        String number = " ";
        int no1;
        int flag = 0;
        Cursor cursor1 = adapter.selectDelivryRequest();
        //cursor1.moveToFirst();
        while (cursor1.moveToNext()) {
            flag = 1;
        }
        cursor1.close();
        //setting id into edit text
        if (flag == 1) {
            try {
                Cursor cursor2 = adapter.selectDelivryRequestincrement();
                while (cursor2.moveToNext()) {
                    if (cursor2.getString(0) != null)
                        number = cursor2.getString(0);

                }
                int n = Integer.parseInt(number);
                no1 = n + 1;
                did.setText("" + no1);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }


    private void Insertdelivery() {

        final ProgressDialog dialog =
                new ProgressDialog(AddPurcahseAmount.this);
        // dialog.setIcon(R.drawable.add);
        dialog.setTitle("Processing Your Request...");
        dialog.setMessage("Please wait...");
        dialog.show();

        long i = adapter.RequestforDelivery(sdid,sdname,sdtype,sddate,sdsize,sdqty,sdlocation,sdshipping,sddistance,smob,sdamount);
        Toast.makeText(getApplicationContext(), " Order Add Sucessfully..." + i, Toast.LENGTH_SHORT).show();

        final Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
                String mpass = null;

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.
                        Builder(AddPurcahseAmount.this);

                alertDialogBuilder.setTitle("Delivery Request Send Successfully ..");

                final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent i = new Intent(AddPurcahseAmount.this, CustomerHomePage.class);
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