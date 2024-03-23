package com.company.silhouettetrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results_Page extends AppCompatActivity {
    TextView scoreCorrect,scoreWrong;
    Button buttonPA,buttonExit;
    int CAC,WAC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        Intent i=getIntent();
        CAC=i.getIntExtra("correct",0);
        WAC=i.getIntExtra("wrong",0);
        buttonExit=findViewById(R.id.buttonExit);
        buttonPA=findViewById(R.id.buttonplayagain);
        scoreCorrect=findViewById(R.id.textViewRC);
        scoreWrong=findViewById(R.id.textViewWC);
        scoreCorrect.setText(""+String.valueOf(CAC));
        scoreWrong.setText(""+String.valueOf(WAC));

        buttonPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Results_Page.this,Quiz_Page.class);
                startActivity(i);
                finish();
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}