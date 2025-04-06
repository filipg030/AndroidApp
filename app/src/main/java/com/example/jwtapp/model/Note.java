package com.example.jwtapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NotesTBL")
public class Note {
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "rating")
    public String rating;

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String getUsername() {
        return username;
    }

    public Note(String username, String note, String rating) {
        this.username = username;
        this.note = note;
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public String getRating() {
        return rating;
    }
}
