package com.company.silhouettetrivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
    EditText Email;
    Button buttonCont;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Email=findViewById(R.id.editTextEmFP);
        buttonCont=findViewById(R.id.buttonContinue);
        buttonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=Email.getText().toString();
                resetPassword(userEmail);

            }
        });

    }
    public void resetPassword(String userEmail){
        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Forgot_Password.this,"We sent an Email to  reset your Password", Toast.LENGTH_LONG).show();
                    buttonCont.setClickable(false);
                    finish();
                }
                else {
                    Toast.makeText(Forgot_Password.this,"Sorry! There is a problem, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}