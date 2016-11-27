package com.example.fares.devfest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText mEmailField ;
    Button mSignInButton;
    TextView textViewSignup;
    EditText mPasswordField;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null){

            startActivity(new Intent(getApplicationContext(), Accueil.class));
            finish();
        }
        mEmailField = (EditText) findViewById(R.id.mEmailField);
        mSignInButton =(Button) findViewById(R.id.mSignInButton);
        mPasswordField = (EditText) findViewById(R.id.mPasswordField);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);



        progressDialog = new ProgressDialog(this);
        textViewSignup.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }




    private void userLogin(){
        String email=mEmailField.getText().toString().trim();
        String password=mPasswordField.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword("user email here","user password here").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {

                    startActivity(new Intent(getApplicationContext(), Accueil.class));
                    finish();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == mSignInButton){
            userLogin();
        }
        if(view == textViewSignup){

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}