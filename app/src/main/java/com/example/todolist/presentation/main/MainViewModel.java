package com.example.todolist.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.App;
import com.example.todolist.models.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Task>> noteLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<Task>> getNoteLiveData() {
        return noteLiveData;
    }
}
