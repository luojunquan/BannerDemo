package com.example.bannerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.example.bannerdemo.banner.BannerPagerAdapter;

import java.util.List;

/**
 * Created by：赖上罗小贱 on 2016/9/11
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class AdapterDemo extends BannerPagerAdapter {

        private Context mContext;
        private List<Integer> data;

        public AdapterDemo(Context context, List data) {
            super(context, data);
            mContext = context;
            this.data = data;
        }

        /**
         * 只需要重写构造和这个方法即可
         * 在这里可以设置自己的View,使用自己的图片加载库
         */
        @Override
        public View setView(int position) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.test, null);
            ImageView iv = (ImageView) v.findViewById(R.id.iv);
            iv.setImageResource(data.get(position));
            return v;
        }
    }
