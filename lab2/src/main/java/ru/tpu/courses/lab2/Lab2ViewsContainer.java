package ru.tpu.courses.lab2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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
//    private int[] viewId1 = { 0,R.id.view11, R.id.view12, R.id.view13 };
public int[] viewId1 = { 0,R.id.view21,R.id.view22,R.id.view23,R.id.view24,R.id.view25,R.id.view26};
    public int[] viewId2={0,R.id.view21,R.id.view22,R.id.view23,R.id.view24,R.id.view25,R.id.view26};
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
        int height;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            height = random.nextInt((int) Math.floor(widthScreen * 0.083)) + widthScreen/6;
        }
        else
        {
             height=height = random.nextInt((int) Math.floor(widthScreen * 0.04 )) + widthScreen/12;

        }

        int rowId = (int) Math.floor(this.viewsCount / 10);
        int columnId = viewsCount % 10;


        if (rowId == 0) {
            Lab2ViewsContainerHorizontal lab2ViewsContainer;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                lab2ViewsContainer = findViewById(viewId1[1]);
                lab2ViewsContainer.incrementViews(1, 1, height, 1);
            }
            else
            {
                lab2ViewsContainer = findViewById(viewId2[1]);
                lab2ViewsContainer.incrementViews(2, 1, height, 1);
            }

            viewsCount = 11;
        } else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (columnId != 3) {
                    if (rowId % 2 != 0) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId1[columnId + 1]);
                        lab2ViewsContainer.incrementViews(1, rowId, height, columnId + 1);
                        viewsCount++;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId1[columnId + 1]);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId - 1, columnId+1 );
                        lab2ViewsContainer.incrementViews(1, rowId, widthScreen / 3 - minusHeight, columnId + 1);
                        viewsCount++;

                    }
                } else {

                    if (rowId % 2 != 1) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId1[1]);
                        lab2ViewsContainer.incrementViews(1, rowId + 1, height, 1);
                        viewsCount = (rowId + 1) * 10 + 1;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId1[1]);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId, 1);
                        lab2ViewsContainer.incrementViews(1, rowId + 1, widthScreen / 3 - minusHeight, 1);
                        viewsCount = (rowId + 1) * 10 + 1;

                    }
                }


            } else {
                if (columnId != 6) {
                    if (rowId % 2 != 0) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId2[columnId + 1]);
                        lab2ViewsContainer.incrementViews(2, rowId, height, columnId + 1);
                        viewsCount++;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId2[columnId + 1]);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId - 1, columnId +1);
                        lab2ViewsContainer.incrementViews(2, rowId, widthScreen / 6 - minusHeight, columnId + 1);
                        viewsCount++;

                    }
                } else {

                    if (rowId % 2 != 1) {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId2[1]);
                                lab2ViewsContainer.incrementViews(2, rowId + 1, height, 1);
                        viewsCount = (rowId + 1) * 10 + 1;
                    } else {
                        Lab2ViewsContainerHorizontal lab2ViewsContainer;
                        lab2ViewsContainer = findViewById(viewId2[1]);
                        int minusHeight = lab2ViewsContainer.getHeightView(rowId, 1);
                        lab2ViewsContainer.incrementViews(2, rowId + 1, widthScreen / 6 - minusHeight, 1);
                        viewsCount = (rowId + 1) * 10 + 1;

                    }
                }
            }

        }
    }

public void updateLinks()
    {
       int[] viewId11={ 0,R.id.view21,R.id.view22,R.id.view23,R.id.view24,R.id.view25,R.id.view26};
        viewId1 = viewId11;
      viewId2=viewId11;
    }
    public void setViewsCount(int viewsCount) {
        if (this.viewsCount == viewsCount) {
            return;
        }
        viewsCount = viewsCount < minViewsCount ? minViewsCount : viewsCount;
      Lab2ViewsContainerHorizontal deletCont;
        for (int i=1;i<7;i++)
        {
            deletCont=findViewById(viewId1[i]);
            deletCont.removeAllViews();
        }
        this.viewsCount = 0;
        for (int i = 0; i < viewsCount; i++) {
            incrementViews();
        }
    }

    public int getViewsCount() {
        int rowId=(int) Math.floor(this.viewsCount/10);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            removeAllViews();
            return ((rowId-1)*6+viewsCount%10);

        }
        else
        { removeAllViews();
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