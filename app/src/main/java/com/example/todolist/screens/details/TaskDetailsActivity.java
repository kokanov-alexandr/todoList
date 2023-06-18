package com.example.todolist.screens.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.models.Task;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final String KEY = "TaskDetailsActivity.KEY";
    private Task task;
    private EditText editText;

    public static void start(Activity caller, Task task) {
        Intent intent = new Intent(caller, TaskDetailsActivity.class);
        if (task != null) {
            intent.putExtra(KEY, task);
        }
        caller.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        setTitle(getString(R.string.task_details_title));

        InitToolbar();
        InitTask();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        showKeyboard(editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String s = editText.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(editText);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

    }
    private void showKeyboard(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.showSoftInput(editText.getRootView(), InputMethodManager.SHOW_IMPLICIT);
        editText.requestFocus();
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomeClick();
                break;
            case R.id.action_save:
                onSaveClick();
        }
        return super.onOptionsItemSelected(item);
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
    private void InitToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void InitTask() {
        editText = findViewById(R.id.editText);
        if (isOpeningForCreatingNewTask()) {
            task = new Task();
            return;
        }
        task = getIntent().getParcelableExtra(KEY);
        editText.setText(task.text);
    }
    private boolean isOpeningForCreatingNewTask() {
        return !getIntent().hasExtra(KEY);
    }
    private void createNewTask() {
        task.text = editText.getText().toString();
        task.isDone = false;
        task.creationTime = System.currentTimeMillis();
    }
}