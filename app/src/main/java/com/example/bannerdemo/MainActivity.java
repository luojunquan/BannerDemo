package com.example.bannerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bannerdemo.banner.Banner;
import com.example.bannerdemo.banner.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by：赖上罗小贱 on 2016/9/11
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.a1);
        data.add(R.drawable.a2);
        data.add(R.drawable.a3);
        data.add(R.drawable.a4);


        AdapterDemo ad = new AdapterDemo(this, data);

        Banner banner = (Banner) findViewById(R.id.banner);

        /**
         * 关于这里的设置参数问题,是需要这样使用的
         * 在设置了小圆点之后才能设置适配器
         * 因为只有在适配器里才会根据一共多少条数据来适配
         * 最后需要调用开始轮播
         * 个人建议在onPause()/onDestroy()方法中来停止 -- stopAutoPlay()
         */
        banner.setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
            setDotGravity(Banner.CENTER).
            setAdapter(ad).
            setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
                @Override
                public void onClick(int position) {
//                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Test.class);
                    startActivity(intent);
                }
            }).
            startAutoPlay();

    }
}
