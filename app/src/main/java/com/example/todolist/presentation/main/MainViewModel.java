package com.example.todolist.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.App;
import com.example.todolist.models.SubTask;
import com.example.todolist.models.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<SubTask>> taskLiveData = App.getInstance().getSubTaskDao().getAll();

    private LiveData<List<Task>> listLiveData = App.getInstance().getTaskDao().getAll();

    public LiveData<List<Task>> getListLiveData(){
        return listLiveData;
    }
    public LiveData<List<SubTask>> getTaskLiveData(){
        return taskLiveData;
    }
}
