package com.example.medihelp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocRecyclerAdapter extends RecyclerView.Adapter<DocRecyclerAdapter.ViewHolder> {
    Context context;

    ArrayList<Doctor> doctorArrayList;

    DocRecyclerAdapter(Context context,ArrayList<Doctor> docArrayList) {
        this.context = context;
        this.doctorArrayList=docArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card,parent,false);
        return new ViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.docName.setText(doctorArrayList.get(position).getName());
        holder.docSpeciality.setText(doctorArrayList.get(position).getSpeciality());


//        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);

        holder.icon_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!doctorArrayList.get(position).isBookmarked()) {
                    holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.white));
                    holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_solid);
                } else {
                    holder.icon_bookmark.setColorFilter(ContextCompat.getColor(context, R.color.primary));
                    holder.icon_bookmark.setBackgroundResource(R.drawable.round_border_trans);
                }
                boolean isTrue = doctorArrayList.get(position).isBookmarked();
                doctorArrayList.get(position).setBookmarked(!isTrue);
            }
        });
    }

//                int pos = holder.getAdapterPosition();
//
//                if(pos!=RecyclerView.NO_POSITION) {
//                    Note delnote = noteArrayList.get(pos);
//                    databaseHelper.noteDao().delete(delnote);
//
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                        .setTitle("Delete Note")
//                        .setMessage("Are you sure?")
//                        .setIcon(R.drawable.baseline_delete_24)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Note delnote = noteArrayList.get(position);
//
//                                noteArrayList.remove(position);
//
//                                databaseHelper.noteDao().delete(delnote);
//                                notifyItemRemoved(position);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                builder.show();
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView docName,docSpeciality,docContact;
        AppCompatImageButton icon_bookmark;
        ConstraintLayout btnBookmarkBack;


        public ViewHolder(Context context,@NonNull View itemView) {
            super(itemView);

            docName = itemView.findViewById(R.id.tvDocName);
            docSpeciality = itemView.findViewById(R.id.tvDocSpeciality);
            icon_bookmark = itemView.findViewById(R.id.btnBookmark);
            btnBookmarkBack = itemView.findViewById(R.id.btnBookmarkBack);

        }
    }
}
