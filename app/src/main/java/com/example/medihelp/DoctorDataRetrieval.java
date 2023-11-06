//package com.example.medihelp;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class DoctorDataRetrieval {
//    private DatabaseReference usersDatabase;
//    private FirebaseAuth firebaseAuth;
//
//    public DoctorDataRetrieval() {
//        // Initialize Firebase Database
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        usersDatabase = firebaseDatabase.getReference("Doctors");
//
//        // Initialize Firebase Authentication
//        firebaseAuth = FirebaseAuth.getInstance();
//    }
//
//    public void retrieveDoctorData(final DoctorDataRetrieval.OnDoctorDataReceivedListener listener) {
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            String currentUserId = currentUser.getUid();
//            DatabaseReference currentUserRef = usersDatabase.child(currentUserId);
//
//            currentUserRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        MainActivityDoctor.currentDoctorData = dataSnapshot.getValue(Doctor.class);
//                        listener.onDoctorReceived(MainActivityDoctor.currentDoctorData);
//                    } else {
//                        listener.onDoctorReceived(null); // User not found
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Handle database read error
//                    listener.onDoctorReceived(null);
//                }
//            });
//        } else {
//            listener.onDoctorReceived(null); // No authenticated user
//            MainActivity.currentUserData = null;
//        }
//    }
//
//    public interface OnDoctorDataReceivedListener {
//        void onDoctorReceived(Doctor doctorData);
//    }
//}
