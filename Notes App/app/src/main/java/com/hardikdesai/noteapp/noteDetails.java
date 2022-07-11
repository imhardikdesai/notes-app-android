package com.hardikdesai.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class noteDetails extends AppCompatActivity {

    private TextView titleofnotedetail, contentofnotedetail;
    FloatingActionButton gotoeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        titleofnotedetail = findViewById(R.id.titleofnotedetail);
        contentofnotedetail = findViewById(R.id.cotentofnotedetail);
        gotoeditnote = findViewById(R.id.gotoeditnote);
        Toolbar toolbar = findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data=  getIntent();
        gotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), editnoteactivity.class);
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteId", data.getStringExtra("noteId"));
                view.getContext().startActivity(intent);
            }
        });
        contentofnotedetail.setText(data.getStringExtra("content"));
        titleofnotedetail.setText(data.getStringExtra("title"));
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