package com.example.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todolist.models.SubTask;
import com.example.todolist.models.Task;

@Database(entities = {Task.class, SubTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
    public abstract SubTaskDao getSubTaskDao();
}
