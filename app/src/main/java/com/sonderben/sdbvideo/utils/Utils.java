package com.sonderben.sdbvideo.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static void setEnableViewGroup(View searchView, boolean b) {
        searchView.setEnabled(b);
        if (searchView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) searchView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);

                setEnableViewGroup(child, b);
            }
        }
    }

    public static void setVisibleChildGroup(View searchView, int visibility) {
        //searchView.setEnabled(b);
        if (searchView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) searchView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);

                child.setVisibility(visibility);
            }
        }
    }

    //textTimeToMillis
    public static String timeMillisToTextTime(Long timeInMillis) {
        StringBuilder stringBuilder = new StringBuilder();
        Long sec;
        Long min = 0L;
        Long hour = 0L;

        sec = timeInMillis / 1000;

        while (min > 60 || sec > 60) {

            if (sec > 60) {
                min = sec / 60;
                sec = sec % 60;
            }
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }

        }

        if (hour > 0) {
            stringBuilder.append(hour);
        }
        if (min > 0) {
            if (min > 9 && hour > 0)
                stringBuilder.append(":" + min);
            else if (min > 9 && hour == 0)
                stringBuilder.append(min);
            else if (min < 10 && hour > 0) {
                stringBuilder.append(":0" + min);
            } else if (min < 10 && hour == 0) {
                stringBuilder.append("0" + min);
            }
        }
        if (sec > 0) {
            if (sec > 9 && min > 0)
                stringBuilder.append(":" + sec);
            else if (sec > 9 && min == 0)
                stringBuilder.append(sec);
            else if (sec < 10 && min > 0) {
                stringBuilder.append(":0" + sec);
            } else if (sec < 10 && min == 0) {
                stringBuilder.append("0" + sec);
            }
        }


       /* return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillis))
        );*/


        return stringBuilder.toString();
    }

    public static void setFullScreen(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
