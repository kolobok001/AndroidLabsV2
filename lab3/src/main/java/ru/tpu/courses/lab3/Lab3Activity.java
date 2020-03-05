package ru.tpu.courses.lab3;

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
import  ru.tpu.courses.lab3.ListAdapter;

public class Lab3Activity extends AppCompatActivity {

    private static final int REQUEST_STUDENT_ADD = 1;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, Lab3Activity.class);
    }

    private final StudentsCache studentsCache = StudentsCache.getInstance();

    private RecyclerView list;
    private FloatingActionButton fab;
    private int sortKey;
    private static final String SORT_KEY = "sort_key";

AlertDialog dialog;
    ExpandableListView expListView;
    ListAdapter expListAdapter;
    List<String> expListTitle;
    HashMap<String, List<String>> expListDetail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] sexList=getResources().getStringArray(R.array.lab3_sex_list);

        setTitle(getString(R.string.lab3_title, getClass().getSimpleName()));

        setContentView(R.layout.lab3_activity);
        list = findViewById(android.R.id.list);
        fab = findViewById(R.id.fab);
        if (savedInstanceState != null)
            sortKey=savedInstanceState.getInt(SORT_KEY);
        else
            sortKey=1;



        expListView = (ExpandableListView) findViewById(R.id.expListView);

        expListDetail=studentsCache.loadData(sortKey,sexList);
        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new ListAdapter(this, expListTitle, expListDetail);

        expListView.setAdapter(expListAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//подсказка при раскрытии списка
            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список раскрыт.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список скрыт.",
                        Toast.LENGTH_SHORT).show();*/

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



        /*
        При нажатии на кнопку мы переходим на Activity для добавления студента. Обратите внимание,
        что здесь используется метод startActivityForResult. Этот метод позволяет организовывать
        передачу данных обратно от запущенной Activity. В нашем случае, после закрытия AddStudentActivity,
        у нашей Activity будет вызван метод onActivityResult, в котором будут данные, которые мы
        указали перед закрытием AddStudentActivity.
         */
        fab.setOnClickListener(
                v -> startActivityForResult(
                        AddStudentActivity.newIntent(this),
                        REQUEST_STUDENT_ADD
                )
        );
    }

    /**
     * Этот метод вызывается после того, как мы ушли с запущенной с помощью метода
     * {@link #startActivityForResult(Intent, int)} Activity.
     *
     * @param requestCode переданный в метод startActivityForResult requestCode, для случаев,
     *                    когда с нашей активитизапускается несколько различных активити. По этому
     *                    идентификатору мы их различаем.
     * @param resultCode  идентификатор, описывающий, с каким результатом запущенная активити была
     *                    завершена. Если пользователь просто закрыл Activity, то по умолчанию будет
     *                    {@link #RESULT_CANCELED}.
     * @param data        даные переданные нам от запущенной Activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_STUDENT_ADD && resultCode == RESULT_OK) {
            Student student = AddStudentActivity.getResultStudent(data);

            studentsCache.addStudent(student);
            expListDetail=studentsCache.loadData(sortKey,getResources().getStringArray(R.array.lab3_sex_list));
            expListTitle = new ArrayList<>(expListDetail.keySet());
            expListAdapter.setData(expListTitle,expListDetail);
            expListAdapter.notifyDataSetChanged();


//            studentsAdapter.setStudents(studentsCache.getStudents());
//            studentsAdapter.notifyItemRangeInserted(studentsAdapter.getItemCount() - 2, 2);
//
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lab3_choise_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() ==R.id.action_choice) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.lab3_title_sorting)
                    .setCancelable(false)
                    .setPositiveButton(R.string.lab3_nosort, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sortKey=1;
                            expListDetail=studentsCache.loadData(sortKey,getResources().getStringArray(R.array.lab3_sex_list));
                            expListTitle = new ArrayList<>(expListDetail.keySet());
                            expListAdapter.setData(expListTitle,expListDetail);
                            expListAdapter.notifyDataSetChanged();

                        }
                    }).setNegativeButton(R.string.lab3_sort_by_group, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sortKey=2;
                    expListDetail=studentsCache.loadData(sortKey,getResources().getStringArray(R.array.lab3_sex_list));
                    expListTitle = new ArrayList<>(expListDetail.keySet());
                    expListAdapter.setData(expListTitle,expListDetail);
                    expListAdapter.notifyDataSetChanged();
                }
            }).setNeutralButton(R.string.lab3_sort_by_sex, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sortKey=3;
                    expListDetail=studentsCache.loadData(sortKey,getResources().getStringArray(R.array.lab3_sex_list));
                    expListTitle = new ArrayList<>(expListDetail.keySet());
                    expListAdapter.setData(expListTitle,expListDetail);
                    expListAdapter.notifyDataSetChanged();
                }
            }).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_KEY, sortKey);
    }



}
