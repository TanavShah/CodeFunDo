package com.odetocode.onetaphelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {
    Button helpMeButton;
    Button helpOthersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        helpMeButton = findViewById(R.id.help_me_button);
        helpOthersButton = findViewById(R.id.help_others_button);

        helpMeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickHelpMe();
            }
        });

        helpOthersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickHelpOthers();
            }
        });
    }
    public void onClickHelpMe()
    {
        Intent intent = new Intent(this,LoadingActivity.class);
        startActivity(intent);
    }
    public void onClickHelpOthers()
    {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}
