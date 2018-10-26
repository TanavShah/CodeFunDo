package com.odetocode.onetaphelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class DenyActivity extends AppCompatActivity {
    Button givepermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deny);
        givepermissions = findViewById(R.id.GIVEPERMISSIONS);
        //givepermissions.setOnClickListener((setGivepermissions());
    }
    public void setGivepermissions()
    {
        Intent intent = new Intent(this,LoadingActivity.class);
        startActivity(intent);
    }
}
