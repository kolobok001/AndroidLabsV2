package ru.tpu.courses.lab3;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

/**
 * Кэш списка студентов в оперативной памяти. Самый быстрый тип кэша, но ограничен размерами
 * оперативной памяти и имеет время жизни равное жизни приложения. Т.е. если приложение будет
 * выгружено из оперативной памяти - то все данные из этого кэша пропадут.
 * Такой тип кэшей используется для временных данных, потеря которых не важна, в большинстве случаев
 * чтобы не делать дополнительные запросы на сервер.
 */
public class StudentsCache {

    private static StudentsCache instance;

    /**
     * Классическая реализация паттерна Singleton. Нам необходимо, чтобы в приложении был только
     * один кэш студентов.
     */
    public static StudentsCache getInstance() {
        if (instance == null) {
            synchronized (StudentsCache.class) {
                if (instance == null) {
                    instance = new StudentsCache();
                }
            }
        }
        return instance;
    }

    private Set<Student> students = new LinkedHashSet<>();

    private StudentsCache() {
    }

    @NonNull
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }
@NonNull
    public  HashMap<String, List<String>> loadData(int key,String[] sexArray) {
    HashMap<String, List<String>> expDetails = new HashMap<>();

    ArrayList<Student> allStudent = new ArrayList<>(students);
    ArrayList<String> group = new ArrayList<>();
    ArrayList<String> sexList=new ArrayList<>();

    for (Student stud : allStudent
    ) {
        if (!group.contains(stud.groupNumber)) {
            group.add(stud.groupNumber);
        }
    }
    sexList.add(sexArray[0]);
    sexList.add(sexArray[1]);


    switch (key) {
        case 1:
            for (Student stud : allStudent
            ) {
                ArrayList<String> infoStudent = new ArrayList<>();
                infoStudent.add("Фамилия: " + stud.secondName);
                infoStudent.add("Имя: " + stud.firstName);
                infoStudent.add("Отчество: " + stud.lastName);
                infoStudent.add("Группа: " + stud.groupNumber);
                infoStudent.add("Пол: " + sexArray[stud.sex]);
                expDetails.put(stud.secondName + " " + stud.firstName, infoStudent);
            }
            break;
        case 2:
            for (String gr : group
            ) {
                ArrayList<String> infoStudent = new ArrayList<>();
                for (Student stud : allStudent
                ) {
                    if (stud.groupNumber == gr) {
                        infoStudent.add(stud.secondName + " " + stud.firstName + " " + stud.lastName + " " + stud.groupNumber + " " + sexArray[stud.sex]);

                    }
                }
                expDetails.put(gr, infoStudent);

            }

            break;
        case 3:
            for (String sex : sexList
            ) {
                ArrayList<String> infoStudent = new ArrayList<>();
                for (Student stud : allStudent
                ) {
                    if (sexArray[stud.sex] == sex) {
                        infoStudent.add(stud.secondName + " " + stud.firstName + " " + stud.lastName + " " + stud.groupNumber + " " + sexArray[stud.sex]);

                    }
                }
                expDetails.put(sex, infoStudent);

            }
            break;
    }


        return expDetails;
    }


    public void addStudent(@NonNull Student student) {
        students.add(student);
    }

    public boolean contains(@NonNull Student student) {
        return students.contains(student);
    }
}
