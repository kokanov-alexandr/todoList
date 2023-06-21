package com.example.todolist.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.models.SubTask;

import java.util.List;

@Dao
public interface SubTaskDao {

    @Query("SELECT * FROM SubTask")
    LiveData<List<SubTask>> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(SubTask subTask);

    @Update
    void update(SubTask subTask);

    @Delete
    void delete(SubTask subTask);
}
