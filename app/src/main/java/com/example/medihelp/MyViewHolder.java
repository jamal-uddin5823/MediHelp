package com.example.medihelp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView Doc_name, Speciality, Location, Contact_no;
    AppCompatImageButton icon_bookmark;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        Doc_name = itemView.findViewById(R.id.tvDocName);
        Speciality = itemView.findViewById(R.id.tvDocSpeciality);
        Location = itemView.findViewById(R.id.tvDocLocation);
        Contact_no = itemView.findViewById(R.id.tvContact);
        icon_bookmark = itemView.findViewById(R.id.btnBookmark);

    }

}

