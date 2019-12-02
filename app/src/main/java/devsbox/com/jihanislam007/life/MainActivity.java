package devsbox.com.jihanislam007.life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner Spinner;
    TextView DateTV;
    LinearLayout dateLL;
    EditText NameET,FaxET;
    Button SubmitButton;

    private DatePickerDialog datePicker;
    private Calendar calendar;
    private int year, month, day;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String name,fax;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner = findViewById(R.id.Spinner);
        DateTV = findViewById(R.id.DateTV);
        dateLL = findViewById(R.id.dateLL);
        NameET =(EditText) findViewById(R.id.NameET);
        FaxET = findViewById(R.id.FaxET);
        SubmitButton = findViewById(R.id.SubmitButton);


        // Spinner Drop down elements
        List<String> venue = new ArrayList<String>();
        venue.add("INTERCON");
        venue.add("BICC");
        venue.add("OTHER");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, venue);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(dataAdapter);


        //////////////date picker////////////////////
        dateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                calendar = Calendar.getInstance();

                *//*String currentdate = DateFormat.getInstance().format(calendar.getTime());
                DateTV.setText(currentdate);*//*

                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);


                datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        DateTV.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },day,month,year);
                datePicker.show();*/

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        //////////////////////////////////////////////////////////////////////////



        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSMSMessage();
               // Toast.makeText(MainActivity.this, "Submit"+name+"\n"+fax+"\n"+String.valueOf(DateTV.getText()), Toast.LENGTH_SHORT).show();
                name = NameET.getText().toString();
                fax = FaxET.getText().toString();

                String phoneNo = "01730012300;" +
                                "01711425005";
                String spinneData = String.valueOf(Spinner.getSelectedItem());
                String dateData = String.valueOf(DateTV.getText());
                String message = spinneData+"\n"+dateData+"\n"+name+"\n"+fax;


                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + Uri.encode(phoneNo)));
                smsIntent.putExtra("sms_body", message);
                //startActivity(smsIntent);
                if (smsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(smsIntent);
                }

                Toast.makeText(MainActivity.this, "Your Message is ready to send", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }
    ////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        name = NameET.getText().toString();
        fax = FaxET.getText().toString();

        String phoneNo = "01752512666;01676919830";
        String spinneData = String.valueOf(Spinner.getSelectedItem());
        String dateData = String.valueOf(DateTV.getText());
        String message = spinneData+"\n"+dateData+"\n"+name+"\n"+fax;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}
