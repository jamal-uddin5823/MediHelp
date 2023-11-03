package com.example.medihelp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Doctor.class, exportSchema = false,version = 2)
public abstract class RoomDatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "bookmarkeddoc";

    private static RoomDatabaseHelper instance;

    public static synchronized RoomDatabaseHelper getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context, RoomDatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public static String getDBName() {
        return DB_NAME;
    }

    public abstract DoctorDao DoctorDao();
}