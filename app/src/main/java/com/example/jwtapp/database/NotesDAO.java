package com.example.jwtapp.database;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jwtapp.model.Note;


import java.util.List;

@Dao
public interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOne(Note note);

    @Query("SELECT * FROM notesTBL")
    List<Note> getAll();

    @Delete
    void delete(Note note);

}
