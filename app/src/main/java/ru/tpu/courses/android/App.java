package ru.tpu.courses.android;

import android.app.Application;

import ru.tpu.courses.lab3.Student;
import ru.tpu.courses.lab3.StudentsCache;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        StudentsCache studentsCache = StudentsCache.getInstance();
        studentsCache.addStudent(new Student("Андрей", "Голушков", "Николаевич","8И6А",0));
        studentsCache.addStudent(new Student("Дмитрий", "Бондаренко", "Игоревич","8И6Б",0));
        studentsCache.addStudent(new Student("Ирина", "Савельева", "Васильевна","8И6Б",1));
        studentsCache.addStudent(new Student("Дмитрий2", "Бондаренко", "Игоревич","8И6Б",0));
        studentsCache.addStudent(new Student("Ирина2", "Савельева", "Васильевна","8И6Б",1));
        studentsCache.addStudent(new Student("Дмитрий3", "Бондаренко", "Игоревич","8И6Б",0));
        studentsCache.addStudent(new Student("Ирина3", "Савельева", "Васильевна","8И6Б",1));
        studentsCache.addStudent(new Student("Дмитрий4", "Бондаренко", "Игоревич","8И6Б",0));
        studentsCache.addStudent(new Student("Ирина4", "Савельева", "Васильевна","8И6Б",1));
        studentsCache.addStudent(new Student("Дмитрий5", "Бондаренко", "Игоревич","8И6Б",0));
        studentsCache.addStudent(new Student("Ирина5", "Савельева", "Васильевна","8И6Б",1));

    }
}
