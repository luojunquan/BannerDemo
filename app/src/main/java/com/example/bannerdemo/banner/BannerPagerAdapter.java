package com.example.bannerdemo.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by：赖上罗小贱 on 2016/9/11
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public abstract class BannerPagerAdapter<T> extends PagerAdapter {

    /**
     * 把banner页的个数设置为100页,这样就达到了无限的效果
     */
    private static final int BANNER_SIZE = 100;
    /**
     * size为当前banner实际的页数
     */
    public int size = -1;
    private int position;

    public onItemClickListener l;

    public BannerPagerAdapter(Context context, List<T> data) {
        size = data.size();
    }

    @Override
    public int getCount() {
        return BANNER_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= size;   //通过这句话来确定实际的position
        this.position = position;
        /**
         * 和正常设置pagerAdapter一样的步骤
         */
        View view = setView(position);
        container.addView(view);
        final int p = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(l != null){
                    l.onClick(p);
                }
            }
        });
        return view;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        ViewPager pager = (ViewPager) container;
        /**
         * 这里获取到当前是第几页
         * 如果是第一页的话就给设置为实际大小的后面一页,比如说一共有4页,那么就设置为第5页,因为第5页是和第1页一样的
         * 如果是最后一页则设置为实际大小的最后一页
         */
        int p = pager.getCurrentItem();
        if (p == 0) {
            p = size;
            pager.setCurrentItem(p, false);
        } else if (p == BANNER_SIZE - 1) {
            p = size - 1;
            pager.setCurrentItem(p, false);
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface onItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener l){
        this. l = l;
    }

    public abstract View setView(int position);

    public int getPosition(){
        return position;
    }
}
