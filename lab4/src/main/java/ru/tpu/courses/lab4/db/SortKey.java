package ru.tpu.courses.lab4.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SortKey {

    @PrimaryKey()
    public int id;



    public SortKey(@NonNull int id) {
        this.id = id;

    }
}