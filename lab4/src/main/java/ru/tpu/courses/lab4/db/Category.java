package ru.tpu.courses.lab4.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Category {

    @PrimaryKey()
    public int id;
    @NonNull
    @ColumnInfo(name = "expanded")
     public boolean expanded;


    public Category(@NonNull int id, @NonNull boolean expanded) {
        this.id = id;
        this.expanded = expanded;
    }
}