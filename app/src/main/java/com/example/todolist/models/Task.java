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

    public Task() {}

    protected Task(Parcel in) {
        id = in.readInt();
        text = in.readString();
        creationTime = in.readLong();
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
        if (!(o instanceof Task)) return false;
        Task listTask = (Task) o;
        return id == listTask.id && creationTime == listTask.creationTime && Objects.equals(text, listTask.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationTime);
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
    }
}
