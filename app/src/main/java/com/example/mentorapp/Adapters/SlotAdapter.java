package com.example.mentorapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentorapp.R;

import java.util.ArrayList;
import java.util.List;


public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.MyViewHolder> {

    List<String> slot = new ArrayList<> (  );

    public SlotAdapter( List<String> slot ) {
        this.slot = slot;
    }

    @NonNull
    @Override
    public SlotAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.slot_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull SlotAdapter.MyViewHolder holder , int position ) {
        holder.slot.setText ( slot.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return slot.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView slot;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            slot = itemView.findViewById ( R.id.slot_info );
        }
    }
}
