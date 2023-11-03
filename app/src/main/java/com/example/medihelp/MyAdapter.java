package com.example.medihelp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "MyAdapter";
    Context context;
    List<Doctor> doctors;
    ArrayList<Boolean> bookmarked = new ArrayList<>();
    ArrayList<Doctor> booked;
    RoomDatabaseHelper roomDatabaseHelper;

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
        roomDatabaseHelper = RoomDatabaseHelper.getInstance(context);
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder called for position: " + position);
        Doctor doctor = doctors.get(position);
        if (doctor != null) {
            holder.Doc_name.setText(doctor.getName());
            holder.Speciality.setText(doctor.getSpeciality());
            holder.Location.setText(doctor.getLocation());
            Picasso.get()
                    .load(doctor.getPicture())
                    .placeholder(R.drawable.ic_baseline_profile_36) // Replace with your placeholder image
                    .error(R.drawable.ic_baseline_profile_36) // Replace with an error image
                    .into(holder.imgDoc);

            Log.d(TAG,"PICTUREEEEE= "+doctor.getPicture());

        } else {
            Log.e(TAG, "Doctor is null at position: " + position);
        }


        booked = (ArrayList<Doctor>) roomDatabaseHelper.DoctorDao().loadByIds(new Long[] {doctors.get(position).getID()});
//                if (!bookmarked.get(position)) {
        if(!booked.isEmpty()) {
            holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.white));
            holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_solid);
        }

        holder.icon_bookmark.setOnClickListener(view -> {
            booked = (ArrayList<Doctor>) roomDatabaseHelper.DoctorDao().loadByIds(new Long[] {doctors.get(position).getID()});
            if(booked.isEmpty()) {
                holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.white));
                holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_solid);
                roomDatabaseHelper.DoctorDao().insert(doctor);
            } else {
                holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.primary));
                holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_trans);
                roomDatabaseHelper.DoctorDao().delete(doctor);
            }
        });

        holder.Contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phno = doctors.get(position).getContact();
                dialPhoneNumber(phno);
//                Toast.makeText(view.getContext(),"Contacting "+phno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount called. Number of items: " + doctors.size());
        return doctors.size();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        // Check if there is an app that can handle the Intent
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

        if (activities.size() > 0) {
            Log.d(TAG, "Calling");
            context.startActivity(intent);
        } else {
            // Handle the case where there is no app to handle the Intent
            Log.d(TAG, "No app can handle this action."+phoneNumber);
            // You can display a message to the user or take alternative actions.
        }
    }

}
