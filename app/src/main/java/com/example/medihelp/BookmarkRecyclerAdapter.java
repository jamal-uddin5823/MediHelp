package com.example.medihelp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "BookmarkAdapter";
    RoomDatabaseHelper roomDatabaseHelper;


    Context context;

    ArrayList<Doctor> booked_doclist;

    BookmarkRecyclerAdapter(Context context,ArrayList<Doctor> docArrayList) {
        this.context = context;
        this.booked_doclist=docArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        roomDatabaseHelper = RoomDatabaseHelper.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor = booked_doclist.get(position);
        if (doctor != null) {
            holder.Doc_name.setText(doctor.getName());
            holder.Speciality.setText(doctor.getSpeciality());
            holder.Location.setText(doctor.getLocation());
//            holder.Contact_no.setText(doctor.getContact());
        } else {
            Log.e(TAG, "Doctor is null at position: " + position);
        }

//        Toast.makeText(context,"Doc size= "+booked_doclist.size(),Toast.LENGTH_SHORT).show();

        holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.white));
        holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_solid);

        holder.icon_bookmark.setOnClickListener(view -> {
            roomDatabaseHelper.DoctorDao().delete(doctor);
            booked_doclist.remove(position);
            notifyItemChanged(position);
        });

        holder.Contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phno = booked_doclist.get(position).getContact();
                dialPhoneNumber(phno);
//                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return booked_doclist.size();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
