package com.example.mentorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentorapp.Adapters.SlotAdapter;
import com.example.mentorapp.Mentor.MentorShared;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class AccountActivity extends AppCompatActivity {

    private Button back;
    private TextView user_name,mentor_type,amount_pending,account_number,total_amount,last_paid;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_account );
        back = findViewById ( R.id.back_button );

        MentorShared mentorShared = new MentorShared ( getApplicationContext () );

        user_name = findViewById ( R.id.user_name );
        user_name.setText ( mentorShared.getMentor ().getName () );
        mentor_type = findViewById ( R.id.mentor_type );
        mentor_type.setText ( mentorShared.getMentor ().getMentorType () );
        amount_pending = findViewById ( R.id.amount_pending );
        amount_pending.setText ( mentorShared.getMentor ().getAmountPending () );
        account_number = findViewById ( R.id.account_number );
        account_number.setText ( mentorShared.getMentor ().getAccountNumber () );
        total_amount = findViewById ( R.id.total_amount );
        total_amount.setText ( mentorShared.getMentor ().getAmountPaid () );
        last_paid = findViewById ( R.id.last_paid );
        last_paid.setText ( mentorShared.getMentor ().getLastPaid () );

        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        } );
    }
}