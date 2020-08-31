package com.example.mentorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.mentorapp.Fragments.HomeFragment;
import com.example.mentorapp.Fragments.LeaderBoardFragment;
import com.example.mentorapp.Fragments.SlotsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private Fragment SelectedFragment = null;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_home );

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.profile_page:
                        intent=new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.account_page:
                        intent=new Intent(getApplicationContext(),AccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.contact_page:
                        intent= new Intent(getApplicationContext(),ContactActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.notification_page:
                        intent= new Intent(getApplicationContext(),NotificationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.log_out_page:
                        intent=new Intent(getApplicationContext(),LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return false;
            }
        });



        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (SelectedFragment == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment () ).commit();
        }


    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.home_page:
                    drawerLayout.closeDrawer( GravityCompat.END);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment () ).commit();
                    break;
                case R.id.leader_board_page:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new LeaderBoardFragment () ).commit();
                    break;
                case R.id.slot_page:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SlotsFragment () ).commit();
                    drawerLayout.closeDrawer(GravityCompat.END);
                    break;
                case R.id.menu_page:
                    drawerLayout.openDrawer(GravityCompat.END);
                    break;
            }

            return true;
        }
    };

}