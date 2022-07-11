package com.hardikdesai.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editnoteactivity extends AppCompatActivity {

    Intent data;
    EditText edittitleofnote, editcontentofnote;
    FloatingActionButton saveeditnote;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnoteactivity);
        edittitleofnote = findViewById(R.id.edittitleofnote);
        editcontentofnote = findViewById(R.id.editcontentofnote);
        saveeditnote = findViewById(R.id.saveeditnote);
        data = getIntent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Button Clicked",Toast.LENGTH_SHORT).show();
               String newtitle = edittitleofnote.getText().toString();
               String newcontent = editcontentofnote.getText().toString();

               if (newtitle.isEmpty() || newcontent.isEmpty()){
                   Toast.makeText(getApplicationContext(),"Something is Empty", Toast.LENGTH_SHORT).show();
                   return;
               }
               else{
                   DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                   Map<String, Object> note = new HashMap<>();
                   note.put("title", newtitle);
                   note.put("content", newcontent);
                   documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void avoid) {
                           Toast.makeText(getApplicationContext(),"Note Updated...", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(editnoteactivity.this, notesActivity.class));
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(),"Failed to Update", Toast.LENGTH_SHORT).show();
                       }
                   });

               }
            }
        });

        String notetitle= data.getStringExtra("title");
        String notecontent = data.getStringExtra("content");
        editcontentofnote.setText(notecontent);
        edittitleofnote.setText(notetitle);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
//        if (item.getItemId() == android.R.id.home){
//            onBackPressed();
//            Toast.makeText(getApplicationContext(), "ERROR!!",Toast.LENGTH_SHORT).show();
//        }
    }
}