package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProduct extends AppCompatActivity {
    EditText did,dname,ddate,dsize,dqty,dlocation,dshipping,ddistance;
    Spinner sp1;
    Button b1;
    TestAdapter adapter;
    String sdid,sdname,sdtype,sddate,sdsize,sdqty,sdlocation,sdshipping,sddistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        did=(EditText) findViewById(R.id.txt_itemid);
        dname=(EditText) findViewById(R.id.txt_itemname);
        ddate=(EditText)findViewById(R.id.txt_dshipate);
        dsize=(EditText) findViewById(R.id.txt_itemsize);
        dqty=(EditText) findViewById(R.id.txt_itemqty);
        dlocation=(EditText) findViewById(R.id.txt_yourlocation);
        dshipping=(EditText) findViewById(R.id.txt_itemshipp);
        ddistance=(EditText) findViewById(R.id.txt_distance);
        sp1=(Spinner) findViewById(R.id.spin_itemtype);

        b1=(Button) findViewById(R.id.btn_adddelivery);


        List<String> categories1 = new ArrayList<String>();
        categories1.add("Fruits");
        categories1.add("Vegetable");
        categories1.add("Rice");
        categories1.add("Milk Product");
        categories1.add("Other");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        sp1.setAdapter(dataAdapter1);


/*ddate.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
});*/

        ddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddProduct.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                ddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },

                        year, month, day);

                datePickerDialog.show();
            }
        });


        try {
            adapter = new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();

            autoincrement();


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // sdid,sdname,sdtype,sdsize,sdqty,sdlocation,sdshipping,sddistance;
                    sdid = did.getText().toString();
                    sdname = dname.getText().toString();
                    sdtype=sp1.getSelectedItem().toString();
                    sddate=ddate.getText().toString();
                    sdsize = dsize.getText().toString();
                    sdqty = dqty.getText().toString();
                    sdlocation = dlocation.getText().toString();
                    sdshipping = dshipping.getText().toString();
                    sddistance = ddistance.getText().toString();

                    if (TextUtils.isEmpty(sdid)) {
                        Toast.makeText(getApplicationContext(), "Please Enter  Name..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(sdname)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product Name..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(sddate)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Shipping Date..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(sdsize)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product Amount in kg.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(sdqty)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product Quantity..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(sdlocation)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Aupply Amount..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(sdshipping)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Supply Area..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(sddistance)) {
                        Toast.makeText(getApplicationContext(), "Please Enter  Supply Location..", Toast.LENGTH_SHORT).show();
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
        Cursor cursor1 = adapter.selectDelivryitem();
        //cursor1.moveToFirst();
        while (cursor1.moveToNext()) {
            flag = 1;
        }
        cursor1.close();
        //setting id into edit text
        if (flag == 1) {
            try {
                Cursor cursor2 = adapter.deliveryincrementid();
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
                new ProgressDialog(AddProduct.this);
        // dialog.setIcon(R.drawable.add);
        dialog.setTitle("Processing Your Request...");
        dialog.setMessage("Please wait...");
        dialog.show();

        long i = adapter.AddDelivery(sdid,sdname,sdtype,sddate,sdsize,sdqty,sdlocation,sdshipping,sddistance);
        //Toast.makeText(getApplicationContext(), " Register Sucessfully..." + i, Toast.LENGTH_SHORT).show();

        final Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
                String mpass = null;

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.
                        Builder(AddProduct.this);

                alertDialogBuilder.setTitle("Product Add Successfully ..");

                final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent i = new Intent(AddProduct.this, FarmerHomePage.class);
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