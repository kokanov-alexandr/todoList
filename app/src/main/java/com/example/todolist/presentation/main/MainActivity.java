package com.example.todolist.presentation.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.models.Task;
import com.example.todolist.presentation.details.TaskDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tasks);

        initTaskAdapter();
        initRecyclerView();
        initFloatingActionButton();
        initMainViewModel();
    }


    private void initTaskAdapter() {
        taskAdapter = new TaskAdapter();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.mainLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(taskAdapter);
    }


    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.task_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDetailsActivity.start(MainActivity.this, null);
            }
        });
    }

    private void initMainViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getListLiveData().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> lists) {
                taskAdapter.notifyDataSetChanged();
                taskAdapter.setItems(lists);
            }
        });
    }
}