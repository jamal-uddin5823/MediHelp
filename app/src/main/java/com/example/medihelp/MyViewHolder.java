package com.example.medihelp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView Doc_name, Speciality, Location, Contact_no;
    ImageView imgDoc;
    AppCompatImageButton icon_bookmark;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        Doc_name = itemView.findViewById(R.id.tvDocName);
        Speciality = itemView.findViewById(R.id.tvDocSpeciality);
        Location = itemView.findViewById(R.id.tvDocLocation);
        Contact_no = itemView.findViewById(R.id.tvContact);
        icon_bookmark = itemView.findViewById(R.id.btnBookmark);
        imgDoc = itemView.findViewById(R.id.imgDoc);

    }

}

