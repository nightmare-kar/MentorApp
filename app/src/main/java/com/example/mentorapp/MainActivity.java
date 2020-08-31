package com.example.mentorapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private ImageView pradeepLogo;
    private CountDownTimer countDownTimer ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_main );

        initialization();

        countDownTimer = new CountDownTimer (1000,10) {
            @Override
            public void onTick( long millisUntilFinished ) {

            }

            @Override
            public void onFinish() {
                MentorShared mentorShared = new MentorShared ( getApplicationContext () );
                Intent intent;
                if (mentorShared.getMentor ().getName ()==null){
                    intent = new Intent ( getApplicationContext (),LoginActivity.class );
                }
                else {
                    intent = new Intent ( getApplicationContext (),HomeActivity.class );
                }
                startActivity ( intent );
                finish ();
                countDownTimer.cancel ();
            }
        };

        countDownTimer.start ();

    }

    private void initialization() {
        pradeepLogo = findViewById ( R.id.logo );
        setAlphaAnimation ( pradeepLogo );
    }

    public static void setAlphaAnimation(View view) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", .3f, 1f);
        fadeIn.setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY", 1f, 1.3f);
        scaleY.setDuration(1000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX", 1f, 1.3f);
        scaleX.setDuration(1000);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(view,"scaleY", 1.3f, 1f);
        scaleY2.setDuration(1000);
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(view,"scaleX", 1.3f, 1f);
        scaleX2.setDuration(1000);

        final AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(scaleX,scaleY,fadeIn);

        final AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(scaleX2,scaleY2);

        animatorSet1.addListener(new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd( Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet2.start();
            }
        });
        animatorSet1.start();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ();
        System.exit ( 0 );
    }
    private void full(){
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        full ();
    }
}