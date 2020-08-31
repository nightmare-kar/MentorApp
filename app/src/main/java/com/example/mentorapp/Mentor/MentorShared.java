package com.example.mentorapp.Mentor;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MentorShared {
    private Context context;
    public MentorShared( Context context ) {
        this.context = context;
    }

    public Mentor getMentor() {
        Mentor mentor = new Mentor ();
        SharedPreferences sharedPreferences = context.getSharedPreferences ( "mentor",Context.MODE_PRIVATE );
        mentor.setAccommodation ( sharedPreferences.getString ( "accommodation",null ) );
        mentor.setAccountNumber ( sharedPreferences.getString ( "acc_no",null ) );
        mentor.setAmountPaid ( sharedPreferences.getString ( "amt_paid",null ) );
        mentor.setAmountPending ( sharedPreferences.getString ( "amt_pending",null ) );
        mentor.setCurrentKidCount ( sharedPreferences.getString ( "current_kid_count",null ) );
        mentor.setLastPaid ( sharedPreferences.getString ( "last_paid",null ) );
        mentor.setMentorType ( sharedPreferences.getString ( "mentor_type",null ) );
        mentor.setName ( sharedPreferences.getString ( "name",null ) );
        mentor.setScore ( sharedPreferences.getString ( "score",null ) );
        mentor.setTimeZoneId ( sharedPreferences.getString ( "zone_id",null ) );
        mentor.setTotalOneOnOne ( sharedPreferences.getString ( "total_one_on_one",null ) );
        mentor.setTotalReviews ( sharedPreferences.getString ( "total_reviews",null ) );
        return mentor;
    }

    public void setMentor( Mentor mentor ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( "mentor",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ("accommodation" ,mentor.getAccommodation () );
        editor.putString ("acc_no" ,mentor.getAccountNumber () );
        editor.putString ("amt_paid" ,mentor.getAmountPaid () );
        editor.putString ("amt_pending" ,mentor.getAmountPending () );
        editor.putString ("current_kid_count",mentor.getCurrentKidCount () );
        editor.putString ("last_paid",mentor.getLastPaid () );
        editor.putString ("mentor_type" ,mentor.getMentorType () );
        editor.putString ("name" ,mentor.getName () );
        editor.putString ("score" ,mentor.getScore () );
        editor.putString ("zone_id" ,mentor.getTimeZoneId () );
        editor.putString ("total_one_on_one" ,mentor.getTotalOneOnOne () );
        editor.putString ("total_reviews",mentor.getTotalReviews () );
        editor.apply();
        Toast.makeText ( context,"dome",Toast.LENGTH_SHORT ).show ();
    }
}
