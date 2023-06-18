package com.example.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todolist.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AddDatabase extends RoomDatabase {
    public abstract TaskDao nodeDao();
}
