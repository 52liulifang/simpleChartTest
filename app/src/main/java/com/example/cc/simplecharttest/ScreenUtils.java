package com.example.cc.simplecharttest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by cc on 2018/6/9.
 */

public class ScreenUtils {
    private ScreenUtils(){
        //不让被实例化？？
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    //获取屏幕宽度
    public static int getScreenWidth(Context context){
        //获取实例
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //不包括下方虚拟按键
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }
    //获取屏幕宽度
    public static int getScreenHeight(Context context){
        //获取实例
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //不包括下方虚拟按键
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    //获取状态栏的高度
    public static int getStatusHeight(Context context){
        int statusHeight=-1;
        try{
            //获取类，反射方法
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            //实例化类
            Object object=clazz.newInstance();
            int height=Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight=context.getResources().getDimensionPixelSize(height);
        }catch (Exception e)
        {e.printStackTrace();}
        return statusHeight;
    }
    //获取虚拟功能键高度
    public static int getVirtualBarHeigh(Context context) {
        //虚拟功能键的高
        int vh = 0;
        //虚拟功能键的宽
        int vw=0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕信息
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            //反射
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            //getRealMetrics(display, dm)
            method.invoke(display, dm);
            //vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();//有替代方法，下面三行就是
            windowManager.getDefaultDisplay().getMetrics(dm);
            vh=dm.heightPixels;
            vw=dm.widthPixels;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
    //获取标题栏高度

    public static int getVirtualBarHeigh(Activity activity) {
        int titleHeight = 0;
        Rect frame = new Rect();
        //getDecorView()的意思是当前窗口的根View
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //标题高度
        int statusHeight = frame.top;
        //Window.ID_ANDROID_CONTENT表示视图的内容部分
        titleHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop() - statusHeight;
        //返回标题的高度
        return titleHeight;
    }
}
