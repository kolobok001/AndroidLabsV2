package ru.tpu.courses.lab2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.Px;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;


public class Lab2ViewsContainer extends ConstraintLayout {

    private int minViewsCount;
    private int viewsCount;


    public Lab2ViewsContainer(Context context) {
        this(context, null);
    }


    public Lab2ViewsContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    

    public Lab2ViewsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Lab2ViewsContainer, defStyleAttr, 0);

        minViewsCount = a.getInt(R.styleable.Lab2ViewsContainer_lab2_minViewsCount, 0);
        if (minViewsCount < 0) {
            throw new IllegalArgumentException("minViewsCount can't be less than 0");
        }

        // Полученный TypedArray необходимо обязательно очистить.
        a.recycle();

        setViewsCount(minViewsCount);
    }


    public void incrementViews() {
        Random random = new Random();
        int widthScreen = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = random.nextInt((int) Math.floor(widthScreen * 0.83 / 6)) + 1 / 6 * widthScreen;

        int rowId = (int) Math.floor(this.viewsCount / 10);
        int columnId = viewsCount % 10;
        int orientation = this.getResources().getConfiguration().orientation;

        if (rowId == 0) {
            int a = 100;
            Lab2ViewsContainerHorizontal lab2ViewsContainer;
            lab2ViewsContainer = findViewById(a);
            lab2ViewsContainer.incrementViews(1, 1, height, 1);
            viewsCount = 11;
        } else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (columnId != 3) {
                    if (rowId % 2 != 0) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(columnId + 1);
                        lab2ViewsContainer.incrementViews(1, rowId, height, columnId + 1);
                        viewsCount++;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(columnId + 1);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId - 1, columnId - 1);
                        lab2ViewsContainer.incrementViews(1, rowId, widthScreen / 3 - minusHeight, columnId + 1);
                        viewsCount++;

                    }
                } else {
                    int a = 100;
                    if (rowId % 2 != 1) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(a);
                        lab2ViewsContainer.incrementViews(1, rowId + 1, height, 1);
                        viewsCount = (rowId + 1) * 10 + 1;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(a);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId, 1);
                        lab2ViewsContainer.incrementViews(1, rowId + 1, widthScreen / 3 - minusHeight, 1);
                        viewsCount = (rowId + 1) * 10 + 1;

                    }
                }


            } else {
                if (columnId != 6) {
                    if (rowId % 2 != 0) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(columnId + 1);
                        lab2ViewsContainer.incrementViews(2, rowId, height, columnId + 1);
                        viewsCount++;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(columnId + 1);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId - 1, columnId - 1);
                        lab2ViewsContainer.incrementViews(2, rowId, widthScreen / 6 - minusHeight, columnId + 1);
                        viewsCount++;

                    }
                } else {
                    int a = 100;
                    if (rowId % 2 != 1) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(a);
                                lab2ViewsContainer.incrementViews(2, rowId + 1, height, 1);
                        viewsCount = (rowId + 1) * 10 + 1;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(a);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId, 1);
                        lab2ViewsContainer.incrementViews(2, rowId + 1, widthScreen / 6 - minusHeight, 1);
                        viewsCount = (rowId + 1) * 10 + 1;

                    }
                }
            }

        }
    }


    public void setViewsCount(int viewsCount) {
        if (this.viewsCount == viewsCount) {
            return;
        }
        viewsCount = viewsCount < minViewsCount ? minViewsCount : viewsCount;

        removeAllViews();
        this.viewsCount = 0;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Lab2ViewsContainerHorizontal newContainer=new Lab2ViewsContainerHorizontal(getContext());

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftToRight=R.id.container;
            params.topToTop=R.id.container;
            newContainer.setLayoutParams(params);
            int a=100;
            newContainer.setId(a);
            addView(newContainer);
            for (int i=2;i<4;i++)
            {
Lab2ViewsContainerHorizontal newCont=new Lab2ViewsContainerHorizontal(getContext());

                params.leftToRight=findViewById((i-1)*100).getId();
                params.topToTop=findViewById((i-1)*100).getId();
                newCont.setLayoutParams(params);
                newCont.setId(i*100);
                addView(newContainer);
            }
        }
        else
        {
            Lab2ViewsContainerHorizontal newContainer=new Lab2ViewsContainerHorizontal(getContext());

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftToRight=R.id.container;
            params.topToTop=R.id.container;
            newContainer.setLayoutParams(params);
            int a=100;
            newContainer.setId(a);
            addView(newContainer);
            for (int i=2;i<7;i++)
            {

                Lab2ViewsContainerHorizontal newCont=new Lab2ViewsContainerHorizontal(getContext());
                params.leftToRight=findViewById((i-1)*100).getId();
                params.topToTop=findViewById((i-1)*100).getId();
                newCont.setLayoutParams(params);
                newCont.setId(i*100);
                addView(newContainer);
            }
        }

        for (int i = 0; i < viewsCount; i++) {
            incrementViews();
        }
    }

    public int getViewsCount() {
        int rowId=(int) Math.floor(this.viewsCount/10);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return ((rowId-1)*6+viewsCount%10);

        }
        else
        {
            return ((rowId-1)*3+viewsCount%10);

        }

    }


    @Px
    public int dpToPx(float dp) {
        if (dp == 0) {
            return 0;
        }
        float density = getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * dp);
    }


}