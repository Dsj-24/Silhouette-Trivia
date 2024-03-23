package com.company.silhouettetrivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up_Page extends AppCompatActivity {
    EditText SUmail;
    EditText SUPassowrd;
    Button buttonSU;
    ProgressBar progressBar;
    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        SUmail=findViewById(R.id.editTextSUEmail);
        SUPassowrd=findViewById(R.id.editTextPasswordSU);
        buttonSU=findViewById(R.id.buttonSU);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);

        buttonSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSU.setClickable(false);
                String userEmail= SUmail.getText().toString();
                String userPassword= SUPassowrd.getText().toString();
                SignUpFirebase(userEmail,userPassword);
            }
        });
    }

    public void SignUpFirebase(String userEmail,String userPassword){
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  Toast.makeText(Sign_Up_Page.this,"YOUR ACCOUNT HAS BEEN CREATED",Toast.LENGTH_SHORT).show();
                  finish();
                  progressBar.setVisibility(View.INVISIBLE);
              }
              else {
                  Toast.makeText(Sign_Up_Page.this,"THERE IS A PROBLEM ,PLEASE TRY AGAIN",Toast.LENGTH_SHORT).show();
              }


            }
        });

    }
}