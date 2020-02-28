package ru.tpu.courses.lab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Lab2Activity extends AppCompatActivity {

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, Lab2Activity.class);
    }

    private static final String STATE_VIEWS_COUNT = "views_count";

    private Lab2ViewsContainer lab2ViewsContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab2_activity);

        setTitle(getString(R.string.lab2_title, getClass().getSimpleName()));

        int orientation = this.getResources().getConfiguration().orientation;
       /* if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lab2ViewsContainer = findViewById(R.id.container1);
        }
        else
        {*/
            lab2ViewsContainer = findViewById(R.id.container);
        //}
        findViewById(R.id.btn_add_view).setOnClickListener(view -> lab2ViewsContainer.incrementViews());
        if (savedInstanceState != null) {
            lab2ViewsContainer.updateLinks();
            lab2ViewsContainer.setViewsCount(savedInstanceState.getInt(STATE_VIEWS_COUNT));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VIEWS_COUNT, lab2ViewsContainer.getViewsCount());
    }

}
