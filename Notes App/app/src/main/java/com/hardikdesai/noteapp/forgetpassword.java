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
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {

    private EditText inputMail;
    private Button btnRecover;
    private TextView backToLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        inputMail = findViewById(R.id.inputEmail);
        btnRecover = findViewById(R.id.btnRecover);
        backToLogin = findViewById(R.id.backToLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgetpassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = inputMail.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your mail First" , Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Mail is Sent You can Recover Your Password Using Mail", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgetpassword.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email is Wrong Your Account Does not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}