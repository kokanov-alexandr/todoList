package com.example.todolist;

import android.app.Application;

import androidx.room.Room;

import com.example.todolist.data.AppDatabase;
import com.example.todolist.data.SubTaskDao;
import com.example.todolist.data.TaskDao;


public class App extends Application {

    private AppDatabase database;
    private SubTaskDao subTaskDao;
    private TaskDao taskDao;
    private  static App instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "TaskList_DB_33")
                .allowMainThreadQueries()
                .build();
        subTaskDao = database.getSubTaskDao();
        taskDao = database.getTaskDao();
    }
    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }
    public TaskDao getTaskDao() {
        return taskDao;
    }
    public static App getInstance(){
        return instance;
    }
}
