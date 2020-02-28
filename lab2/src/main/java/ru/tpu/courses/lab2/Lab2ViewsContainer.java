package ru.tpu.courses.lab2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.Px;

import java.util.Random;


public class Lab2ViewsContainer extends LinearLayout {

    private int minViewsCount;
    private int viewsCount;
    private int[] btnId;


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
        int height=random.nextInt((int)Math.floor(widthScreen*0.83))+1/6*widthScreen;
         Lab2ViewsContainerHorizontal lab2ViewsContainer;
         int rowId=(int) Math.floor(this.viewsCount/10);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
           if (this.viewsCount%10!=0 && this.viewsCount%10!=3)
           {
               lab2ViewsContainer = findViewById(rowId);
               if(rowId%2!=0){
                   lab2ViewsContainer.incrementViews(1,rowId,height);
               }

               else
               {
                   Lab2ViewsContainerHorizontal searchViewContainer=findViewById(rowId-1);
                   lab2ViewsContainer.incrementViews(1,rowId,widthScreen/3-searchViewContainer.getHeightView(rowId-1,viewsCount%10));
               }
               viewsCount++;
           }
           else
           {
               Lab2ViewsContainerHorizontal newHorizontalContainer=new Lab2ViewsContainerHorizontal(getContext());
               newHorizontalContainer.setId(rowId+1);
               addView((newHorizontalContainer));
               if((rowId+1)%2!=0){
                   newHorizontalContainer.incrementViews(1,rowId+1,height);
               }

               else
               {
                   Lab2ViewsContainerHorizontal searchViewContainer=findViewById(rowId+1-1);
                   newHorizontalContainer.incrementViews(1,rowId+1,widthScreen/3-searchViewContainer.getHeightView(rowId+1-1,viewsCount%10));
               }
               viewsCount=(rowId+1)*10+1;
           }
        } else {
            if (this.viewsCount%10!=0 && this.viewsCount%10!=6)
            {
                lab2ViewsContainer = findViewById(rowId);
                if(rowId%2!=0){
                    lab2ViewsContainer.incrementViews(2,rowId,height);
                }

                else
                {
                    Lab2ViewsContainerHorizontal searchViewContainer=findViewById(rowId-1);
                    lab2ViewsContainer.incrementViews(2,rowId,widthScreen/3-searchViewContainer.getHeightView(rowId-1,viewsCount%10));
                }
                viewsCount++;
            }
            else
            {
                Lab2ViewsContainerHorizontal newHorizontalContainer=new Lab2ViewsContainerHorizontal(getContext());
                newHorizontalContainer.setId(rowId+1);
                addView((newHorizontalContainer));
                if((rowId+1)%2!=0){
                    newHorizontalContainer.incrementViews(2,rowId+1,height);
                }

                else
                {
                    Lab2ViewsContainerHorizontal searchViewContainer=findViewById(rowId+1-1);
                    newHorizontalContainer.incrementViews(2,rowId+1,widthScreen/3-searchViewContainer.getHeightView(rowId+1-1,viewsCount%10));
                }
                viewsCount=(rowId+1)*10+1;
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