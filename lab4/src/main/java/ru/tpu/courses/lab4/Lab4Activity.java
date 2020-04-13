package ru.tpu.courses.lab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import  ru.tpu.courses.lab4.ListAdapter;
import ru.tpu.courses.lab4.db.Category;
import ru.tpu.courses.lab4.db.Lab4Database;
import ru.tpu.courses.lab4.db.SortKey;
import ru.tpu.courses.lab4.db.SortingDao;
import ru.tpu.courses.lab4.db.StudentDao;
import  ru.tpu.courses.lab4.db.Student;
import ru.tpu.courses.lab4.add.AddStudentActivity;

public class Lab4Activity extends AppCompatActivity {

    private static final int REQUEST_STUDENT_ADD = 1;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, Lab4Activity.class);
    }


    private RecyclerView list;
    private FloatingActionButton fab;
    private SortKey sortKey = new SortKey(1);
    private StudentDao studentDao;
    private SortingDao sortingDao;
    private List<Category> category;
    private static final String SORT_KEY = "sort_key";


    ExpandableListView expListView;
    ListAdapter expListAdapter;
    List<String> expListTitle;
    HashMap<String, List<String>> expListDetail;

    @NonNull
    public HashMap<String, List<String>> loadData(int key, String[] sexArray) {
        HashMap<String, List<String>> expDetails = new HashMap<>();
        ArrayList<Student> allStudent = new ArrayList<>(studentDao.getAll());
        ArrayList<String> group = new ArrayList<>();
        ArrayList<String> sexList = new ArrayList<>();

        for (Student stud : allStudent)
        {
            if (!group.contains(stud.groupNumber)) {
                group.add(stud.groupNumber);
            }
        }
        sexList.add(sexArray[0]);
        sexList.add(sexArray[1]);


        switch (key) {
            case 1:
                for (Student stud : allStudent)
                {
                    ArrayList<String> infoStudent = new ArrayList<>();
                    infoStudent.add("Фамилия: " + stud.lastName);
                    infoStudent.add("Имя: " + stud.firstName);
                    infoStudent.add("Отчество: " + stud.secondName);
                    infoStudent.add("Группа: " + stud.groupNumber);
                    infoStudent.add("Пол: " + sexArray[stud.sex]);
                    expDetails.put(stud.lastName + " " + stud.firstName, infoStudent);
                }
                break;
            case 2:
                for (String gr : group)
                {
                    ArrayList<String> infoStudent = new ArrayList<>();
                    for (Student stud : allStudent)
                    {
                        if (stud.groupNumber.equals(gr)) {
                            infoStudent.add(stud.lastName + " " + stud.firstName + " " + stud.secondName + " " + stud.groupNumber + " " + sexArray[stud.sex]);
                        }
                    }
                    expDetails.put(gr, infoStudent);
                }
                break;
            case 3:
                for (String sex : sexList)
                {
                    ArrayList<String> infoStudent = new ArrayList<>();
                    for (Student stud : allStudent)
                    {
                        if (sexArray[stud.sex] == sex) {
                            infoStudent.add(stud.lastName + " " + stud.firstName + " " + stud.secondName + " " + stud.groupNumber + " " + sexArray[stud.sex]);
                        }
                    }

                    expDetails.put(sex, infoStudent);
                }
                break;
        }


        return expDetails;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] sexList = getResources().getStringArray(R.array.lab4_sex_list);
        studentDao = Lab4Database.getInstance(this).studentDao();
        sortingDao = Lab4Database.getInstance(this).sortingDao();
        setTitle(getString(R.string.lab4_title, getClass().getSimpleName()));

        setContentView(R.layout.lab4_activity);
        list = findViewById(android.R.id.list);
        fab = findViewById(R.id.fab);
        if (sortingDao.countSortKey() != 1) {
            sortingDao.deleteSortKey();
            SortKey newSortKey = new SortKey(1);
            sortingDao.insertSortKey(newSortKey);
        }
        sortKey = sortingDao.getSortKey().get(0);
        category = sortingDao.getAll();
        expListView = (ExpandableListView) findViewById(R.id.expListView);
        expListDetail = loadData(sortKey.id, sexList);
        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new ListAdapter(this, expListTitle, expListDetail);

        expListView.setAdapter(expListAdapter);
        int c = expListView.getCount();
        for (int i = 0; i < c; i++) {
            if (!category.get(i).expanded)
                expListView.collapseGroup(i);
            else
                expListView.expandGroup(i);
        }
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            //подсказка при раскрытии списка
            @Override
            public void onGroupExpand(int groupPosition) {
                boolean boolNow = category.get(groupPosition).expanded;
                category.get(groupPosition).expanded = !boolNow;
                sortingDao.update(!boolNow, category.get(groupPosition).id);
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


                boolean boolNow = category.get(groupPosition).expanded;
                category.get(groupPosition).expanded = !boolNow;
                sortingDao.update(!boolNow, category.get(groupPosition).id);
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               /* Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition)
                                + " : " + expListDetail.get(expListTitle.get(groupPosition))
                                .get(childPosition), Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });


        fab.setOnClickListener(
                v -> startActivityForResult(
                        AddStudentActivity.newIntent(this),
                        REQUEST_STUDENT_ADD
                )
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_STUDENT_ADD && resultCode == RESULT_OK) {
            Student student = AddStudentActivity.getResultStudent(data);

            studentDao.insert(student);
            Category category = new Category(
                    studentDao.countAll() - 1,
                    false
            );
            sortingDao.insert(category);
            this.category.add(category);

            expListDetail = loadData(sortKey.id, getResources().getStringArray(R.array.lab4_sex_list));
            expListTitle = new ArrayList<>(expListDetail.keySet());
            expListAdapter.setData(expListTitle, expListDetail);
            expListAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lab4_choise_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sortingDao.deleteSortKey();
        sortingDao.insertSortKey(sortKey);
    }

    @Override
    public void onStop() {
        super.onStop();
        sortingDao.deleteSortKey();
        sortingDao.insertSortKey(sortKey);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_choice) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.lab4_title_sorting)
                    .setCancelable(true)
                    .setPositiveButton(R.string.lab4_nosort, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            sortKey.id = 1;

                            expListDetail = loadData(sortKey.id, getResources().getStringArray(R.array.lab4_sex_list));
                            expListTitle = new ArrayList<>(expListDetail.keySet());
                            expListAdapter.setData(expListTitle, expListDetail);
                            expListAdapter.notifyDataSetChanged();

                        }
                    }).setNegativeButton(R.string.lab4_sort_by_group, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    sortKey.id = 2;

                    expListDetail = loadData(sortKey.id, getResources().getStringArray(R.array.lab4_sex_list));
                    expListTitle = new ArrayList<>(expListDetail.keySet());
                    expListAdapter.setData(expListTitle, expListDetail);
                    expListAdapter.notifyDataSetChanged();
                }
            }).setNeutralButton(R.string.lab4_sort_by_sex, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    sortKey.id = 3;

                    expListDetail = loadData(sortKey.id, getResources().getStringArray(R.array.lab4_sex_list));
                    expListTitle = new ArrayList<>(expListDetail.keySet());
                    expListAdapter.setData(expListTitle, expListDetail);
                    expListAdapter.notifyDataSetChanged();
                }
            }).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    } //переопределение действия для alertDiaolog
}
