package com.example.bannerdemo.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by：赖上罗小贱 on 2016/9/11
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 * 重写ViewPager来达到定时翻页和触摸事件的监听
 * 当ActionDown时,停止自动翻页
 * 当手指抬起,开始计时自动翻页
 */
public class BannerViewPager extends ViewPager  {

    private static final int MSG_WHAT = -00001;
    private int SEND_TIME = 5000;
    private int position;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            position = getCurrentItem() + 1;
            setCurrentItem(position);
            sendEmptyMessageDelayed(MSG_WHAT, SEND_TIME);
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public BannerViewPager startAutoPlay() {
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, SEND_TIME);
        return this;
    }

    public void stopAutoPlay() {
        mHandler.removeMessages(MSG_WHAT);
    }

    public BannerViewPager setTime(int time) {
        SEND_TIME = time;
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopAutoPlay();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            startAutoPlay();
        }
        return super.dispatchTouchEvent(ev);
    }

}
