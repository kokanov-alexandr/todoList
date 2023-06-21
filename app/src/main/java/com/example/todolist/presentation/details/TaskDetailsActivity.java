package com.example.todolist.presentation.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.models.SubTask;
import com.example.todolist.models.Task;
import com.example.todolist.presentation.main.MainViewModel;
import com.example.todolist.presentation.main.SubTaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskDetailsActivity extends AppCompatActivity {
    private static final String TASK_KEY = "taskListFaceActivity.TASK_KEY";
    private Task task;
    private RecyclerView recyclerView;
    private EditText editText;
    private SubTaskAdapter subTaskAdapter;

    public static void start(Activity caller, Task task){
        Intent intent = new Intent(caller, TaskDetailsActivity.class);
        if (task != null){
            intent.putExtra(TASK_KEY, task);
        }
        caller.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onHomeClick();
                break;
            case R.id.action_save:
                onSaveClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        setTitle(R.string.tasks_title);

        initSubTaskAdapter();
        initRecyclerView();
        initTask();
        initFloatingActionButton();
        initMainViewModel();
    }

    private void initTask() {
        editText = findViewById(R.id.new_task_text);
        if (isOpeningForCreatingNewTask()){
            task = new Task();
            return;
        }
        task = getIntent().getParcelableExtra(TASK_KEY);
        editText.setText(task.text);
    }

    private void initSubTaskAdapter() {
        subTaskAdapter = new SubTaskAdapter();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.subTasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(subTaskAdapter);
        recyclerView.getRecycledViewPool().clear();
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.task_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubTaskDetailsActivity.start(TaskDetailsActivity.this, null, task.id);
            }
        });
    }

    private void initMainViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTaskLiveData().observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                subTaskAdapter.setItems(App.getInstance().getTaskDao().getSubTaskById(task.id));
            }
        });
    }

    private void onHomeClick() {
        finish();
    }

    private void onSaveClick() {
        if (!wasTheTaskEntered()) {
            return;
        }
        createNewTask();
        if (isOpeningForCreatingNewTask()) {
            App.getInstance().getTaskDao().insert(task);
        }
        else {
            App.getInstance().getTaskDao().update(task);
        }
        finish();
    }

    private boolean wasTheTaskEntered() {
        return editText.getText().length() > 0;
    }
    private boolean isOpeningForCreatingNewTask() {
        return !getIntent().hasExtra(TASK_KEY);
    }
    private void createNewTask() {
        task.text = editText.getText().toString();
        task.creationTime = System.currentTimeMillis();
    }
}

