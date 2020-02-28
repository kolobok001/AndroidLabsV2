package ru.tpu.courses.lab2;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Px;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;


public class Lab2ViewsContainerHorizontal extends ConstraintLayout {


public int viewHorizontalCount=0;
    public Lab2ViewsContainerHorizontal(Context context) {
        this(context, null);
    }


    public Lab2ViewsContainerHorizontal(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public Lab2ViewsContainerHorizontal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Lab2ViewsContainer, defStyleAttr, 0);



        // Полученный TypedArray необходимо обязательно очистить.
        a.recycle();


    }

public int getHeightView(int rowId,int viewId)
{
    View view=findViewById(rowId*1000+viewId*100);
    return view.getLayoutParams().height;
}
    public void incrementViews(int orientation,int rowId,int height,int columnId) {
        if (orientation==1) {
            int widthScreen = Resources.getSystem().getDisplayMetrics().widthPixels;
            View view = new View(getContext());
            LayoutParams params = new LayoutParams(widthScreen / 3, height);
            view.setLayoutParams(params);
            Random random = new Random(); // Probably really put this somewhere where it gets executed only once
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            view.setBackgroundColor(Color.rgb(red, green, blue));
            view.setId(rowId*1000+columnId*100);
            addView(view);

        }
        else
        {
            int widthScreen = Resources.getSystem().getDisplayMetrics().widthPixels;
            View view = new View(getContext());
            LayoutParams params = new LayoutParams(widthScreen / 6, height);
            view.setLayoutParams(params);
            Random random = new Random(); // Probably really put this somewhere where it gets executed only once
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            view.setBackgroundColor(Color.rgb(red, green, blue));
            view.setId(rowId*1000+columnId*100);
            addView(view);

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