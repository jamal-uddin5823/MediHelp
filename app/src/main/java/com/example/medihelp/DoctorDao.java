package com.example.medihelp;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DoctorDao {

    @Query("SELECT * FROM bookmarkeddoc order by id desc")
    List<Doctor> loadAll();

    @Query("SELECT * FROM bookmarkeddoc WHERE id IN (:Ids)")
    List<Doctor> loadByIds(Long[] Ids);

    @Insert
    void insert(Doctor doctor);

    @Delete
    void delete(Doctor doctor);
}
