package ru.tpu.courses.lab4.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName;
    @NonNull
    @ColumnInfo(name = "second_name")
    public String secondName;
    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName;
    @NonNull
    @ColumnInfo(name = "group_number")
    public String groupNumber;
    @NonNull
    @ColumnInfo(name = "sex")
    public int sex;
    @Nullable
    @ColumnInfo(name = "photo_path")
    public String photoPath;

    public Student(
            @NonNull String firstName,
            @NonNull String secondName,
            @NonNull String lastName,
            @NonNull String groupNumber,
            @NonNull int sex,
            @Nullable String photoPath
    ) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.photoPath = photoPath;
        this.groupNumber=groupNumber;
        this.sex=sex;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        secondName = in.readString();
        lastName = in.readString();
        photoPath = in.readString();
        sex=in.readInt();
        groupNumber=in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(secondName);
        dest.writeString(lastName);
        dest.writeString(photoPath);
        dest.writeInt(sex);
        dest.writeString(groupNumber);
    }
}
