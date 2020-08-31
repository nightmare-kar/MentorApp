package com.example.mentorapp.Fragments;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlotsFragment extends Fragment {

    private FirebaseFirestore db;
    private HorizontalCalendar horizontalCalendar;
    private static List<String> slotId = new ArrayList<> (  );
    private List<String> slot = new ArrayList<> (  );
    private RecyclerView recyclerView;
    private String mentor_name = "M01";
    private String mentor_zone_id = "Antarctica/DumontDUrville";

    private String utcStartIndia[] = new String[]{"4:00","4:20","4:40","5:00","5:20","5:40","6:00","6:20","6:40","7:00","7:20","7:40","8:00","8:20","8:40","9:00","9:20","9:40","10:00","10:20","10:40","11:00","11:20","11:40","12:00","12:20","12:40","13:00","13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40"};
    private String utcEndIndia[] = new String[]{"4:20","4:40","5:00","5:20","5:40","6:00","6:20","6:40","7:00","7:20","7:40","8:00","8:20","8:40","9:00","9:20","9:40","10:00","10:20","10:40","11:00","11:20","11:40","12:00","12:20","12:40","13:00","13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40","17:00"};

    private String utcStartUsa[] = new String[]{"13:00","13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40","17:00","17:20","17:40","18:00","18:20","18:40","19:00","19:20","19:40","20:00","20:20","20:40","21:00","21:20","21:40","22:00","22:20","22:40","23:00","23:20","23:40","0:00","0:20","0:40","1:00","1:20","1:40"};
    private String utcEndUsa[] = new String[]{"13:20","13:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40","17:00","17:20","17:40","18:00","18:20","18:40","19:00","19:20","19:40","20:00","20:20","20:40","21:00","21:20","21:40","22:00","22:20","22:40","23:00","23:20","23:40","0:00","0:20","0:40","1:00","1:20","1:40","2:00"};

    private List<String> date1 = new ArrayList<> (  );
    private List<String> date2 = new ArrayList<> (  );
    private List<String> date3 = new ArrayList<> (  );
    private List<String> date4 = new ArrayList<> (  );

    private static int count = 0;
    private static boolean need = true;

    private List<String> dateDocument = new ArrayList<> (  );

    private Calendar refStartDate,startDate,secondDate,thirdDate,endDate,refEndDate;

    public SlotsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_slots , container , false );

        refStartDate = Calendar.getInstance();
        refStartDate.add(Calendar.DATE, 0);
        startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 1);
        secondDate = Calendar.getInstance();
        secondDate.add(Calendar.DATE, 2);
        thirdDate = Calendar.getInstance();
        thirdDate.add(Calendar.DATE, 3);
        endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 4);
        refEndDate = Calendar.getInstance();
        refEndDate.add(Calendar.DATE, 5);



        //dates to fetch slots
        /*if (TimeZone.getTimeZone ( mentor_zone_id ).getRawOffset ()<0){
            dateDocument.add ( String.valueOf ( refStartDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( refStartDate.get ( Calendar.MONTH ) )+1 ) +"_"+refStartDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( startDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( startDate.get ( Calendar.MONTH ) )+1 ) +"_"+startDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( secondDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( secondDate.get ( Calendar.MONTH ) )+1 ) +"_"+secondDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( thirdDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( thirdDate.get ( Calendar.MONTH ) )+1 ) +"_"+thirdDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( endDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( endDate.get ( Calendar.MONTH ) )+1 ) +"_"+endDate.get ( Calendar.YEAR ) ) );
        }
        else {
            dateDocument.add ( String.valueOf ( startDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( startDate.get ( Calendar.MONTH ) )+1 ) +"_"+startDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( secondDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( secondDate.get ( Calendar.MONTH ) )+1 ) +"_"+secondDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( thirdDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( thirdDate.get ( Calendar.MONTH ) )+1 ) +"_"+thirdDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( endDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( endDate.get ( Calendar.MONTH ) )+1 ) +"_"+endDate.get ( Calendar.YEAR ) ) );
            dateDocument.add ( String.valueOf ( refEndDate.get ( Calendar.DATE )+"_"+ String.valueOf ( Integer.valueOf ( refEndDate.get ( Calendar.MONTH ) )+1 ) +"_"+refEndDate.get ( Calendar.YEAR ) ) );
        }*/

        dateDocument.add ( "05_08_2020" );
        dateDocument.add ( "06_08_2020" );
        dateDocument.add ( "07_08_2020" );
        dateDocument.add ( "08_08_2020" );
        dateDocument.add ( "09_08_2020" );



        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById ( R.id.slot_recycler );
        count = 0;
        fetchData ( dateDocument.get ( count ),view );

        return view;
    }

    private void fetchData( String date, final View view){
        need = true;
        final CollectionReference collectionReference=db.collection("mentor").document(mentor_name).collection(date);
        collectionReference.orderBy ( "slot_id" ).get ().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                if (task.isSuccessful ()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getLong ( "slot_id" )<=39){
                            slot.add ( timeFormer ( String.valueOf ( document.getLong ( "slot_id" ) ) ) );
                        }
                    }

                    count+=1;
                    if (count<dateDocument.size ()){
                        if (need){
                            slot.add ( "///" );
                        }
                        fetchData ( dateDocument.get ( count ),view );
                    }
                    else if (count == dateDocument.size ()){
                        String totalDates = String.valueOf ( slot );
                        String s1 = totalDates.split ( "///" )[0];
                        String s2 = totalDates.split ( "///" )[1];
                        String s3 = totalDates.split ( "///" )[2];
                        String s4 = totalDates.split ( "///" )[3];

                        timeSetter ( s1,s2,s3,s4 );

                        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calender_view).range(startDate, endDate).datesNumberOnScreen(3).mode(HorizontalCalendar.Mode.DAYS).build();
                        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener () {
                            @Override
                            public void onDateSelected( Calendar date, int position) {
                                if (date.get ( Calendar.DATE ) == startDate.get ( Calendar.DATE ) && date.get ( Calendar.MONTH ) == startDate.get ( Calendar.MONTH ) && date.get ( Calendar.YEAR ) == startDate.get ( Calendar.YEAR )){
                                    recyclerView.setHasFixedSize ( true );
                                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                                    recyclerView.setAdapter ( new SlotAdapter (date1) );
                                }
                                else if (date.get ( Calendar.DATE ) == secondDate.get ( Calendar.DATE ) && date.get ( Calendar.MONTH ) == secondDate.get ( Calendar.MONTH ) && date.get ( Calendar.YEAR ) == secondDate.get ( Calendar.YEAR )){
                                    recyclerView.setHasFixedSize ( true );
                                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                                    recyclerView.setAdapter ( new SlotAdapter (date2) );
                                }
                                else if (date.get ( Calendar.DATE ) == thirdDate.get ( Calendar.DATE ) && date.get ( Calendar.MONTH ) == thirdDate.get ( Calendar.MONTH ) && date.get ( Calendar.YEAR ) == thirdDate.get ( Calendar.YEAR )){
                                    recyclerView.setHasFixedSize ( true );
                                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                                    recyclerView.setAdapter ( new SlotAdapter (date3) );
                                }
                                else if (date.get ( Calendar.DATE ) == endDate.get ( Calendar.DATE ) && date.get ( Calendar.MONTH ) == endDate.get ( Calendar.MONTH ) && date.get ( Calendar.YEAR ) == endDate.get ( Calendar.YEAR )){
                                    recyclerView.setHasFixedSize ( true );
                                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                                    recyclerView.setAdapter ( new SlotAdapter (date4) );
                                }
                            }
                        });
                    }

                }
                else {
                    Toast.makeText ( getContext (),String.valueOf ( "fail" ),Toast.LENGTH_SHORT ).show ();
                }
            }
        } );
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
    private String timeFormer( String id){

        long mentorOff = TimeZone.getTimeZone ( mentor_zone_id ).getRawOffset ();
        long slotStart = hrsToMilli (utcStartIndia[Integer.parseInt ( id )-1] )+mentorOff;
        long slotEnd = hrsToMilli ( utcEndIndia[Integer.parseInt ( id )-1] )+mentorOff;

        long hrs = TimeUnit.MILLISECONDS.toHours ( slotStart ) ;
        long min = TimeUnit.MILLISECONDS.toMinutes ( slotStart )%60;
        long hrs1 = TimeUnit.MILLISECONDS.toHours ( slotEnd ) ;
        long min1 = TimeUnit.MILLISECONDS.toMinutes ( slotEnd )%60;
        if (hrs < 0 && min <0){
            hrs = hrs+24;
            min = min+60;
        }
        else if (hrs>=0 && min >=0){
            hrs = hrs;
            min = min;
        }
        else if (hrs <0){
            hrs = hrs+24;
        }
        else if (min<0){
            min = min+60;
        }

        if (hrs1 < 0 && min1 <0){
            hrs1 = hrs1+24;
            min1 = min1+60;
        }
        else if (hrs1>=0 && min1 >=0){
            hrs1 = hrs1;
            min1 = min1;
        }
        else if (hrs <0){
            hrs1 = hrs1+24;
        }
        else if (min1<0){
            min1 = min1+60;
        }

        if (hrs >=24){
            hrs = hrs-24;
            if (need == true){
                slot.add ( "///" );
                need = false;
            }
        }

        if (hrs1 >=24){
            hrs1 = hrs1-24;
        }

        return String.valueOf ( hrs )+":"+String.valueOf ( min )+" - "+String.valueOf ( hrs1 )+":"+String.valueOf ( min1 );
    }

    private void timeSetter(String s1,String s2,String s3,String s4){
        for (int i = 0; i < s1.split ( "," ).length; i++) {
            /*if (s1.split ( "," )[0].startsWith ( "[" )  && s1.split ( "," )[0]!=""){
                date1.add ( s1.split ( "," )[0].replaceFirst ( String.valueOf ( "[" ),"" ) );
            }
            else */if (s1.split ( "," )[i]!=""){
                date1.add ( s1.split ( "," )[i] );
            }
        }
        for (int i = 0; i < s2.split ( "," ).length; i++) {
            if (s2.split ( "," )[i]!=""){
                date2.add ( s2.split ( "," )[i] );
            }
        }
        for (int i = 0; i < s3.split ( "," ).length; i++) {
            if (s3.split ( "," )[i]!=""){
                date3.add ( s3.split ( "," )[i] );
            }
        }
        for (int i = 0; i < s4.split ( "," ).length; i++) {

            if (s4.split ( "," )[i]!="" /*&& i !=(s4.split ( "," ).length-1)*/ ){
                date4.add ( s4.split ( "," )[i] );
            }
            /*else if (s4.split ( "," )[s4.split ( "," ).length-1].endsWith ( "]" )  && s4.split ( "," )[s4.split ( "," ).length-1]!=""){
                date4.add ( s4.split ( "," )[s4.split ( "," ).length-1].replaceFirst ( String.valueOf ( "]" ),"" ) );
            }*/
        }
    }
}