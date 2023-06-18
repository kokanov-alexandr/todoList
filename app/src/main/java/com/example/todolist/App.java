package com.example.todolist;

import android.app.Application;

import androidx.room.Room;

import com.example.todolist.data.AddDatabase;
import com.example.todolist.data.TaskDao;

public class App extends Application {

    private AddDatabase dataBase;

    private TaskDao taskDao;
    public static App instance;


    @Override
    public void onCreate(){
        super.onCreate();

        InitAppInstance();
        InitDatabase();
    }

    private void InitAppInstance() {
        instance = this;
    }
    private void InitDatabase() {
        dataBase = Room.databaseBuilder(getApplicationContext(),AddDatabase.class, "db-name").
                allowMainThreadQueries().build();
        taskDao = dataBase.nodeDao();
    }

    public static App getInstance() {
        return instance;
    }


    public TaskDao getTaskDao() {
        return taskDao;
    }
}
