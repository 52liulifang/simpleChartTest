package com.example.cc.simplecharttest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;

/**
 * Created by cc on 2018/6/9.
 */

public class DragFloatActionButton extends FloatingActionButton {
    private int screenWidth;
    private int screenHeight;
    private int screenWidthHalf;
    private int statusHeight;
    private int virtualHeight;
    private int lastX;
    private int lastY;
    private boolean isDrag;
    public DragFloatActionButton(Context context){
        super(context);
        init();
    }
    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        screenWidth = ScreenUtils.getScreenWidth(getContext());
        screenWidthHalf = screenWidth / 2;
        screenHeight = ScreenUtils.getScreenHeight(getContext());
        statusHeight = ScreenUtils.getStatusHeight(getContext());
        virtualHeight=ScreenUtils.getVirtualBarHeigh(getContext());

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int rawX = (int) ev.getRawX();
        int rawY = (int) ev.getRawY();
        //处理多点触摸事件
        switch (ev.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                isDrag=false;
                //取得焦点，可以接受事件，当传入的参数为true时，表示子组件要自己消费这次事件，告诉父组件不要拦截（抢走）这次的事件
                //对于底层的View来说，有一种方法可以阻止父层的View截获touch事件，就是调用getParent().requestDisallowInterceptTouchEvent(true);方法。一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action。
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = true;
                //计算手指移动了多少
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些手机无法触发点击事件的问题
                int distance= (int) Math.sqrt(dx*dx+dy*dy);
                if(distance<3){//给个容错范围，不然有部分手机还是无法点击
                    isDrag=false;
                    break;
                }

                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                Log.d("move---->", "屏幕宽度screenwidth"+screenWidth+"getwidth宽度"+getWidth()+"屏幕高度screenheight"+screenHeight);
                //y = y < statusHeight ? statusHeight : (y + getHeight() >= screenHeight ? screenHeight - getHeight() : y);
                if (y<0){
                    y=0;
                }
                //getHeight()本身的高度
                if (y>screenHeight-getHeight()){
                    y=screenHeight-getHeight();
                }
                setX(x);
                setY(y);

                lastX = rawX;
                lastY = rawY;
                Log.e("move---->", "getX=" + getX() + "；screenWidthHalf=" + screenWidthHalf + " " + isDrag+"  statusHeight="+statusHeight+ " virtualHeight"+virtualHeight+ " screenHeight"+ screenHeight+"  getHeight="+getHeight()+" y"+y);
                break;
            //在UP里添加吸附功能
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    //恢复按压效果
                    setPressed(false);
                    Log.e("ACTION_UP---->", "getX=" + getX() + "；screenWidthHalf=" + screenWidthHalf);
                    if (rawX >= screenWidthHalf) {
                        //设置动画效果，设置动画为减速动画(动画播放中越来越慢)向左吸附
                        animate().setInterpolator(new BounceInterpolator())
                                .setDuration(155)//设置动画时间
                                .xBy(screenWidth - getWidth() - getX())//啥意思？不懂
                                .start();
                    } else {
                        //动画Float型平滑过渡（向右吸附）
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);//“x”是啥意思不懂
                        oa.setInterpolator(new BounceInterpolator());
                        oa.setDuration(155);//设置动画时间
                        oa.start();
                    }
                }
                Log.e("up---->",isDrag+"");
                break;
        }
        return isDrag ||super.onTouchEvent(ev);
    }
}
