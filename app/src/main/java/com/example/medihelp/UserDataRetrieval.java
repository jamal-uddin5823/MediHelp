package com.example.medihelp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataRetrieval {

    private DatabaseReference usersDatabase;
    private FirebaseAuth firebaseAuth;

    public UserDataRetrieval() {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersDatabase = firebaseDatabase.getReference("Users");

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void retrieveUserData(final OnUserDataReceivedListener listener) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            DatabaseReference currentUserRef = usersDatabase.child(currentUserId);

            currentUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        MainActivity.currentUserData = dataSnapshot.getValue(User.class);
                        listener.onUserReceived(MainActivity.currentUserData);
                    } else {
                        listener.onUserReceived(null); // User not found
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database read error
                    listener.onUserReceived(null);
                }
            });
        } else {
            listener.onUserReceived(null); // No authenticated user
        }
    }

    public interface OnUserDataReceivedListener {
        void onUserReceived(User user);
    }
}
