package com.example.todolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class SubTask implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "creationTime")
    public long creationTime;

    @ColumnInfo(name = "isDone")
    public boolean isDone;

    @ColumnInfo(name = "isImportant")
    public boolean isImportant;

    @ColumnInfo(name = "listId")
    public int listId;

    public SubTask() {}
    protected SubTask(Parcel in) {
        id = in.readInt();
        text = in.readString();
        creationTime = in.readLong();
        isDone = in.readByte() != 0;
        isImportant = in.readByte() != 0;
    }

    public static final Creator<SubTask> CREATOR = new Creator<SubTask>() {
        @Override
        public SubTask createFromParcel(Parcel in) {
            return new SubTask(in);
        }

        @Override
        public SubTask[] newArray(int size) {
            return new SubTask[size];
        }
    };
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
        dest.writeByte((byte) (isImportant ? 1 : 0));
    }
}
