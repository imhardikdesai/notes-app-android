package com.hardikdesai.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView goToLogin;
    private Button btnSignup;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        goToLogin = findViewById(R.id.gotoLogin);
        btnSignup = findViewById(R.id.btnSignup);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, MainActivity.class );
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Fileds are Required!",Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 7){
                    Toast.makeText(getApplicationContext(), "Password Length Must be Greater Than 7",Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful",Toast.LENGTH_SHORT ).show();
                                sendEmailVerifaction();
                            }
                        }
                    });
                }
            }
        });
    }
    private void sendEmailVerifaction(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
         if (firebaseUser != null){
             firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     Toast.makeText(getApplicationContext(), "Verification Email is Sent, Verify it and Log in Again",Toast.LENGTH_LONG ).show();
                     firebaseAuth.signOut();
                     finish();
                     startActivity(new Intent(signup.this, MainActivity.class));
                 }
             });
         }else{
             Toast.makeText(getApplicationContext(), "Failed to Send Verification Mail",Toast.LENGTH_SHORT ).show();
         }
    }
}