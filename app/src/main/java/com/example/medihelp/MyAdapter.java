package com.example.medihelp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "MyAdapter";
    Context context;
    List<Doctor> doctors;
    ArrayList<Boolean> bookmarked = new ArrayList<>();

    public MyAdapter(Context context, List<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;

        for(Doctor d: doctors) {
            bookmarked.add(false);
        }
    }
    public void updateData(List<Doctor> newDoctorsList) {
        this.doctors = newDoctorsList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder called");
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called for position: " + position);
        Doctor doctor = doctors.get(position);
        if (doctor != null) {
            holder.Doc_name.setText(doctor.getName());
            holder.Speciality.setText(doctor.getSpeciality());
            holder.location.setText(doctor.getLocation());
//            holder.Contact_no.setText(doctor.getContact());
        } else {
            Log.e(TAG, "Doctor is null at position: " + position);
        }

        holder.icon_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!bookmarked.get(position)) {
                    holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.white));
                    holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_solid);

//                    boolean flag = false;
//                    Doctor doctor = doctors.get(position);
//                    for (Doctor d: BookmarksFragment.arrayList) {
//                        if(d.getID()==doctor.getID()) {
//                            flag=true;
//                        }
//                    }
//                    if(!flag)
//                        BookmarksFragment.arrayList.add(doctors.get(position));
//                } else {
//                    holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.primary));
//                    holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_trans);
//                }
//                boolean isTrue = bookmarked.get(position);
//                bookmarked.set(position,!isTrue);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount called. Number of items: " + doctors.size());
        return doctors.size();
    }
}
