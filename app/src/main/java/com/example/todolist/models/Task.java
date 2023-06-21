package com.example.todolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;
    @ColumnInfo(name = "creationTime")
    public long creationTime;

    @ColumnInfo(name = "isDone")
    public boolean isCompleted;

    @ColumnInfo(name = "isImportant")
    public boolean isImportant;

    public Task() {}

    protected Task(Parcel in) {
        id = in.readInt();
        text = in.readString();
        creationTime = in.readLong();
        isCompleted = in.readByte() != 0;
        isImportant = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }
        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task subTask = (Task) o;
        return id == subTask.id && creationTime == subTask.creationTime && isCompleted == subTask.isCompleted && isImportant == subTask.isImportant && Objects.equals(text, subTask.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationTime, isCompleted, isImportant);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(text);
        parcel.writeLong(creationTime);
        parcel.writeByte((byte) (isCompleted ? 1 : 0));
        parcel.writeByte((byte) (isImportant ? 1 : 0));
    }
}