package com.company.silhouettetrivia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

public class Login_Page extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button buttonSignIn;
    SignInButton buttonSignInGoogle;
    TextView SignUp;
    TextView ForgotPassword;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register();
        Email=findViewById(R.id.editTextTextEmailAddress);
        Password=findViewById(R.id.editTextTextPassword);
        buttonSignIn=findViewById(R.id.buttonSignIn);
        buttonSignInGoogle=findViewById(R.id.signInButton3);
        SignUp=findViewById(R.id.textViewsignup);
        ForgotPassword=findViewById(R.id.textViewFP);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=Email.getText().toString();
                String userPassword =Password.getText().toString();
                SignInFireBase(userEmail,userPassword);

            }
        });

        buttonSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIngoogle();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_Page.this,Sign_Up_Page.class);
                startActivity(i);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login_Page.this,Forgot_Password.class);
                startActivity(i);

            }
        });
    }
    public void signIngoogle(){
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("715909081349-id1p2osu3njps6k41kdonvjglgm9ei9v.apps.googleusercontent.com").requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        signin();

    }
    public void signin(){
        Intent signInIntent= googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }
    public void Register(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode=result.getResultCode();
                Intent data=result.getData();
                if (resultCode==RESULT_OK && data != null){
                      Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

                }
            }
        });

    }
    private void fireBaseGoogleSignIn(Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            Toast.makeText(this, "Successful!", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Login_Page.this,MainActivity.class);
            startActivity(i);
            finish();
            fireBaseGoogleAccount(account);
        } catch (ApiException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }
    private void fireBaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }
                else{

                }
            }
        });
    }
    public void SignInFireBase(String userEmail,String userPassword){
        buttonSignIn.setClickable(false);
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   Intent i= new Intent(Login_Page.this,MainActivity.class);
                   startActivity(i);
                   finish();
                    Toast.makeText(Login_Page.this,"SIGN IN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login_Page.this,"SIGN IN FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= auth.getCurrentUser();
        if (user!= null){
            Intent i= new Intent(Login_Page.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}