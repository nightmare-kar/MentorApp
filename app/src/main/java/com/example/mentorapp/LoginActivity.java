package com.example.mentorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentorapp.Adapters.SlotAdapter;
import com.example.mentorapp.Mentor.Mentor;
import com.example.mentorapp.Mentor.MentorShared;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class LoginActivity extends AppCompatActivity {

    private String TAG="loginActivity";
    private FirebaseFirestore db;
    private EditText user_name,user_password;
    private Button log_in_button;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        db = FirebaseFirestore.getInstance ();

        user_name = findViewById ( R.id.user_name );
        user_password = findViewById ( R.id.user_password );
        log_in_button = findViewById ( R.id.log_in_but );

        log_in_button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                Log.i(TAG,user_name.getText().toString());
                db.collection("mentor_profile").document(user_name.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.i(TAG,task.getResult().toString());
                        if (task.isSuccessful() && task.getResult().exists()) {

                            fetchData(user_name.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid_userName", Toast.LENGTH_SHORT).show();
                        }

                    }
                });}});

            }

    private void fetchData( String mentor_name ){

        final DocumentReference documentReference =db.collection("mentor_profile").document(mentor_name);
        documentReference.addSnapshotListener ( new EventListener<DocumentSnapshot> () {
            @Override
            public void onEvent( @Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException e ) {
                MentorShared mentorShared = new MentorShared ( getApplicationContext () );
                Mentor mentor = new Mentor ();
                mentor.setAccommodation ( documentSnapshot.getString ( "accommodation" ) );
                mentor.setAccountNumber ( documentSnapshot.getString ( "account_number" ) );
                mentor.setAmountPaid ( documentSnapshot.getString ( "amount_paid" ) );
                mentor.setAmountPending ( documentSnapshot.getString ( "amount_pending" ) );
                mentor.setCurrentKidCount ( documentSnapshot.getString ( "current_kid_count" ) );
                mentor.setLastPaid ( documentSnapshot.getString ( "last_paid" ) );
                mentor.setMentorType ( documentSnapshot.getString ( "mentor_type" ) );
                mentor.setName ( documentSnapshot.getString ( "name" ) );
                mentor.setScore ( documentSnapshot.getString ( "score" ) );
                mentor.setTimeZoneId ( documentSnapshot.getString ( "time_zone_id" ) );
                mentor.setTotalOneOnOne ( documentSnapshot.getString ( "total_one_on_one" ) );
                mentor.setTotalReviews ( documentSnapshot.getString ( "total_reviews" ) );
                mentorShared.setMentor ( mentor );
                startActivity ( new Intent ( getApplicationContext (),HomeActivity.class ) );
            }
        } );
    }
}