package com.example.todolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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
    public boolean isDone;

    public Task() {

    }

    protected Task(Parcel in) {
        id = in.readInt();
        text = in.readString();
        creationTime = in.readLong();
        isDone = in.readByte() != 0;
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
        Task note = (Task) o;
        return id == note.id && creationTime == note.creationTime && isDone == note.isDone && Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationTime, isDone);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeLong(creationTime);
        dest.writeByte((byte) (isDone ? 1 : 0));
    }
}
