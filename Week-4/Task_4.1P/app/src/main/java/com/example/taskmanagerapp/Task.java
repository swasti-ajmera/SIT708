package com.example.taskmanagerapp;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public String description;
    public String dueDate;
}
