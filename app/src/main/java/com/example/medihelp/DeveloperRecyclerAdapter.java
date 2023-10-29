package com.example.medihelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeveloperRecyclerAdapter extends RecyclerView.Adapter<DeveloperRecyclerAdapter.ViewHolder> {
    private static final String TAG = "BookmarkAdapter";

    Activity activity;

    ArrayList<Developer> developers;

    public DeveloperRecyclerAdapter(Activity activity, ArrayList<Developer> developers) {
        this.activity = activity;
        this.developers = developers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.developer_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Developer developer = developers.get(position);

        if(developer!=null) {
            holder.tvDevName.setText(developer.getName());
            ImageLoadTask imageLoadTask = new ImageLoadTask(holder.imgDev,holder.imgDev.getContext());
            imageLoadTask.execute(developer.getImage());
            holder.imgDevEmail.setOnClickListener(view -> {
//                Log.d("Click", "View clicked");
//                Toast.makeText(activity.getApplicationContext(),"Touch",Toast.LENGTH_SHORT).show();
                sendEmail(developer.getEmail());
            });
        }
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    private void sendEmail(String recipientEmail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
//        intent.setData(Uri.parse("mailto:" + recipientEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject"); // Set the email subject
        intent.putExtra(Intent.EXTRA_TEXT, "Body of the email"); // Set the email body

        // Verify that there's an email client installed on the device
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDevName;
        ImageView imgDev, imgDevEmail;
//        CardView devMail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDevName = itemView.findViewById(R.id.tvDdevName);
            imgDev = itemView.findViewById(R.id.imgDev);
            imgDevEmail = itemView.findViewById(R.id.imgDevMail);
//            devMail = itemView.findViewById(R.id.mailCard);
        }
    }
}
