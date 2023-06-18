package com.example.todolist.data;

import androidx.room.Database;

import com.example.todolist.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AddDatabase {
    public abstract NoteDao nodeDao();
}
