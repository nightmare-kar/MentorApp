package com.example.mentorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentorapp.Mentor.MentorShared;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class ContactActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText message;
    private Button post;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_contact );

        db = FirebaseFirestore.getInstance ();
        post = findViewById ( R.id.post_button );
        message = findViewById ( R.id.message );
        post.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                if (message.getText ().toString ()==""){
                    Toast.makeText ( getApplicationContext (),"enter something",Toast.LENGTH_SHORT );
                }
                else {
                    MentorShared mentorShared = new MentorShared ( getApplicationContext () );
                    CollectionReference collectionReference = db.collection ( "mentor_support" );
                    HashMap<String,Object> hashMap = new HashMap<> (  );
                    hashMap.put ( "mentor_name",mentorShared.getMentor ().getName () );
                    hashMap.put ( "message",message.getText ().toString () );
                    hashMap.put ( "date", String.valueOf ( Calendar.DATE )+","+String.valueOf ( Calendar.HOUR_OF_DAY )+"/"+String.valueOf ( Calendar.MINUTE )+"/"+String.valueOf ( Calendar.SECOND ) );
                    collectionReference.add ( hashMap );
                }
            }
        } );
    }
}