package com.example.jwtapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.jwtapp.model.Note;

@androidx.room.Database(entities = {Note.class}, version = 2)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase roomDatabase;
    private static final String DATABASE_NAME = "NotesTBL";

    public static synchronized NotesDatabase getInstance(Context context){
        if(roomDatabase==null){
            roomDatabase
                    = Room.databaseBuilder(
                            context.getApplicationContext(), NotesDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomDatabase;
    }

    public abstract NotesDAO notesDAO();

}