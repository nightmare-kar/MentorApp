package com.example.mentorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mentorapp.Adapters.SlotAdapter;
import com.example.mentorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsaFragment extends Fragment {

    private FirebaseFirestore db;
    private List<String> slot = new ArrayList<> (  );
    private RecyclerView recyclerView;
    private String mentor_name = "M01";
    private String mentor_zone_id = "America/Aruba";
    private String utcStartUsa[] = new String[]{"13:00","13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40","17:00","17:20","17:40","18:00","18:20","18:40","19:00","19:20","19:40","20:00","20:20","20:40","21:00","21:20","21:40","22:00","22:20","22:40","23:00","23:20","23:40","0:00","0:20","0:40","1:00","1:20","1:40"};
    private String utcEndUsa[] = new String[]{"13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40","17:00","17:20","17:40","18:00","18:20","18:40","19:00","19:20","19:40","20:00","20:20","20:40","21:00","21:20","21:40","22:00","22:20","22:40","23:00","23:20","23:40","0:00","0:20","0:40","1:00","1:20","1:40","2:00"};
    private List<String> dates = new ArrayList<> (  );

    public UsaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_usa , container , false );

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById ( R.id.slot_recycler );

        fetchData ( "06_08_2020" );

        return view;
    }

    private void fetchData(String date){
        final CollectionReference collectionReference=db.collection("mentor").document(mentor_name).collection(date);
        collectionReference.orderBy ( "slot_id" ).get ().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                if (task.isSuccessful ()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getLong ( "slot_id" )<=33){
                            slot.add ( slotIdToTime ( String.valueOf ( document.getLong ( "slot_id" ) ) ) );
                        }
                    }

                    recyclerView.setHasFixedSize ( true );
                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                    recyclerView.setAdapter ( new SlotAdapter (slot) );

                }
                else {
                    Toast.makeText ( getContext (),String.valueOf ( "fail" ),Toast.LENGTH_SHORT ).show ();
                }
            }
        } );
    }

    private String slotIdToTime(String id){
        long mentorOff = TimeZone.getTimeZone ( mentor_zone_id ).getRawOffset ();
        String slotStart = "";
        String slotEnd = "";
        slotStart = milliToHrs ( hrsToMilli (utcStartUsa[Integer.parseInt ( id )-1] )+mentorOff );
        slotEnd = milliToHrs ( hrsToMilli ( utcEndUsa[Integer.parseInt ( id )-1] )+mentorOff );
        return slotStart+" - "+slotEnd;
    }

    private float IdToOffset(String id){
        return TimeZone.getTimeZone ( id ).getRawOffset ()/(1000*60*60);
    }

    public String addTime(int hour, int minute,int hrsToAdd) {
        Calendar calendar = new GregorianCalendar (1990, 1, 1, hour,minute);
        calendar.add ( Calendar.HOUR,hrsToAdd );
        SimpleDateFormat sdf = new SimpleDateFormat ("HH:mm");
        String date = sdf.format(calendar.getTime());
        return date;
    }
    private long hrsToMilli(String time){
        int hrs = Integer.parseInt ( time.split ( ":" )[0] );
        int min = Integer.parseInt ( time.split ( ":" )[1] );
        return (hrs*60*60*1000)+(min*60*1000);
    }
    private String milliToHrs(long milli){
        long hrs = TimeUnit.MILLISECONDS.toHours ( milli ) ;
        long min = TimeUnit.MILLISECONDS.toMinutes ( milli )%60;
        if (hrs < 0 && min <0){
            hrs = hrs+24;
            min = min+60;
        }
        else if (hrs>=0 && milli >=0){
            hrs = hrs;
            min = min;
        }
        else if (hrs <0){
            hrs = hrs+24;
        }
        else if (min<0){
            min = min+60;
        }

        return String.valueOf ( hrs )+":"+String.valueOf ( min );
    }
}