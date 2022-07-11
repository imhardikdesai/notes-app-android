package com.hardikdesai.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView forgetPassword, gotoRegister;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    ProgressBar progresbarofmainactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.forgotPassword);
        gotoRegister = findViewById(R.id.gotoRegister);
        progresbarofmainactivity = findViewById(R.id.progressbarofmainactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getSupportActionBar().hide();

        if (firebaseUser!= null){
            finish();
            startActivity(new Intent(MainActivity.this, notesActivity.class));
        }

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, forgetpassword.class));
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , signup.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Fields are Required!",Toast.LENGTH_SHORT).show();
                }else{
                    progresbarofmainactivity.setVisibility(View.VISIBLE);
                   firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               checkEmailVErification();
                           }else{
                               Toast.makeText(getApplicationContext(), "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                               progresbarofmainactivity.setVisibility(View.INVISIBLE);
                           }
                       }
                   });
                }
            }
        });
    }
    private void checkEmailVErification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified()){
            Toast.makeText(getApplicationContext(), "Logged in..", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this , notesActivity.class));
        }
        else{
            progresbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "First Verified Your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}