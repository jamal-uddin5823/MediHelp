package com.example.medihelp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private static final String TAG = "SearchAdapter";
    Context context;
    private List<Doctor> doctorList;

    public SearchAdapter(Context context, List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public void updateData(List<Doctor> newDoctorsList) {
        this.doctorList = newDoctorsList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder called");
        View itemView = LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false);
        return new MyViewHolder(itemView);
    }

//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder called");
//        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false));
//    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        if(doctor!=null){
            holder.Doc_name.setText(doctor.getName());
            holder.Speciality.setText(doctor.getSpeciality());
            holder.Location.setText(doctor.getLocation());
        }
        else {
            Log.e(TAG, "Doctor is null at position: " + position);
        }

        holder.Contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phno = doctorList.get(position).getContact();
                dialPhoneNumber(phno);
//                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

//    public class DoctorViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvDoctorName, tvSpecialty, tvLocation, tvContact;
//
//        public DoctorViewHolder(View view) {
//            super(view);
//            tvDoctorName = view.findViewById(R.id.tvDoctorName);
//            tvSpecialty = view.findViewById(R.id.tvSpecialty);
//            tvLocation = view.findViewById(R.id.tvLocation);
//            tvContact = view.findViewById(R.id.tvContact);
//        }
//    }
}

