package com.example.todolist.presentation.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.models.SubTask;

public class SubTaskDetailsActivity extends AppCompatActivity {

    private static final String SUB_TASK_KEY = "SubTaskDetailsActivity.SUB_TASK_KEY";
    private static final String LIST_ID_KEY = "SubTaskDetailsActivity.LIST_ID_KEY";

    private SubTask subTask;

    private EditText editText;

    public static void start(Activity caller, SubTask subTask, int listId){
        Intent intent = new Intent(caller, SubTaskDetailsActivity.class);
        intent.putExtra(LIST_ID_KEY, listId);
        if (subTask != null){
            intent.putExtra(SUB_TASK_KEY, subTask);
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
        setContentView(R.layout.activity_subtask_detalis);
        setTitle(R.string.subtasks_title);

        initEditText();
        initSubTask();
    }

    private void onHomeClick() {
        finish();
    }

    private void onSaveClick() {
        if (!wasTheSubTaskEntered()) {
            return;
        }
        createNewSubTask();
        if (isOpeningForCreatingNewTask()) {
            App.getInstance().getSubTaskDao().insert(subTask);
        }
        else {
            App.getInstance().getSubTaskDao().update(subTask);
        }
        finish();
    }

    private boolean wasTheSubTaskEntered() {
        return editText.getText().length() > 0;
    }
    private boolean isOpeningForCreatingNewTask() {
        return !getIntent().hasExtra(SUB_TASK_KEY);
    }
    private void createNewSubTask() {
        subTask.text = editText.getText().toString();
        subTask.creationTime = System.currentTimeMillis();
        subTask.listId = getIntent().getIntExtra(LIST_ID_KEY, 0);
    }

    private void initEditText() {
        editText = findViewById(R.id.textForTask);
    }

    private void initSubTask() {
        if (isOpeningForCreatingNewSubTask()){
            subTask = new SubTask();
            subTask.listId = getIntent().getExtras().getInt(LIST_ID_KEY);
            return;
        }
        subTask = getIntent().getParcelableExtra(SUB_TASK_KEY);
        editText.setText(subTask.text);
    }

    private boolean isOpeningForCreatingNewSubTask() {
        return !getIntent().hasExtra(SUB_TASK_KEY);
    }
}