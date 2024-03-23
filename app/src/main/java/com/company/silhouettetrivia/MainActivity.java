package com.company.silhouettetrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
Button buttonSO;
Button buttonSq;
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSO=findViewById(R.id.buttonSo);
        buttonSq=findViewById(R.id.buttonStartQuiz);
        buttonSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            auth.signOut();
                Intent i=new Intent(MainActivity.this,Login_Page.class);
                startActivity(i);
                finish();
            }
        });
        buttonSq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(MainActivity.this,Quiz_Page.class);
              startActivity(i);
            }
        });

    }
}