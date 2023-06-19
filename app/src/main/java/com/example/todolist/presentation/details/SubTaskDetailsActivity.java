package com.example.todolist.presentation.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todolist.R;
import com.example.todolist.models.SubTask;

public class SubTaskDetailsActivity extends AppCompatActivity {
    private static final String KEY = "SubTaskDetailsActivity.KEY";

    private EditText editText;


    public static void start(Activity caller, SubTask subTask) {
        Intent intent = new Intent(caller, SubTaskDetailsActivity.class);
        if (subTask != null) {
            intent.putExtra(KEY, subTask);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask_detalis);
        setTitle(getString(R.string.subtask_details_title));

        editText = findViewById(R.id.subtask_editText);
        InitToolbar();
    }
    private void InitToolbar() {
        Toolbar toolbar = findViewById(R.id.subtask_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
