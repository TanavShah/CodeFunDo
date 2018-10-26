package com.odetocode.onetaphelp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public final int MY_PERMISSIONS_REQUEST_SEND_SMS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!service.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            return;
        }
        Console.print("permission granted");
        Intent intent = new Intent(this, StartPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        Console.print(requestCode+"");
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS)
        {
            boolean results = true;
            for (int i = 0;i < grantResults.length;i++)
            {
                if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                {
                    results = false;
                }
            }
            if (results && grantResults.length > 0)
            {
                Console.print("permission granted");
                Intent intent = new Intent(this, StartPageActivity.class);
                startActivity(intent);
            }
            else
            {
                Console.print("permission denied");
                Intent intent = new Intent(this, DenyActivity.class);
                startActivity(intent);
            }
        }
    }

    public void sendSMS(String phoneNo, String msg)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
