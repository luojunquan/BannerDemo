---
title: 轮播条广告位
tags:
	- Android
categories: 
	- Android
---

本篇总结了轮播条广告位..

<!-- more -->	

# 轮播条
首先我们要确定一下这个banner有几个需要注意的点：

1. 无限轮播
2. 点击事件
3. 指示器
4. 指示器的滑动效果

## 无限轮播、点击事件

[循环广告位组件的实现 - 任玉刚](http://blog.csdn.net/singwhatiwanna/article/details/46541225)

## 指示器、滑动效果
其实我写的时候有好多坑，但是写这篇文章的时候感觉都不是特别难的点

关于指示器，其实现在大部分都是小圆点，这里我实现的思路是创建一个FrameLayout来添加ViewPager和指示器

但是可以看到，这里的指示器会有一个随着页面滑动而滑动的效果，这里就需要两层了，一个是包含未选中的，一个是包含了选中和未选中的（Group）

代码如下：

	private void init() {
    mPager = new BannerViewPager(mContext);
    addView(mPager);
    /**
     * 实例化两个Group
     */
    mFrameLayout = new FrameLayout(mContext);
    mDotGroup = new LinearLayout(mContext);
    /**
     * 设置小圆点Group的方向为水平
     */
    mDotGroup.setOrientation(LinearLayout.HORIZONTAL);
    /**
     * 如果不设置则小圆点在中间
     */
    mDotGroup.setGravity(CENTER | Gravity.BOTTOM);
    /**
     * 两个Group的大小都为match_parent
     */
    LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
    /**
     * 首先添加小圆点的Group
     */
    mFrameLayout.addView(mDotGroup, params);
    /**
     * 然后添加包含的Group（f**k,表达能力有限）
     */
    addView(mFrameLayout, params);

    /**
     * 添加到任务栈,当前所有任务完事之后添加已经选中的那个小圆点
     */
    post(new Runnable() {
        @Override
        public void run() {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(mContext.getResources().getDrawable(mDot[1]));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            /**
             * 设置选中小圆点的左边距
             */
            params.leftMargin = (int) mDotGroup.getChildAt(0).getX();
            params.gravity = Gravity.BOTTOM;
            mFrameLayout.addView(iv, params);
            mSelectedDot = mFrameLayout.getChildAt(1);
        }
    });
    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /**
             * 获取到当前position
             */
            position %= mAdapter.size;
            /**
             * 判断如果当前的position不是最后一个,那么就设置偏移量来实现被选中小圆点的滑动效果
             */
            if (mSelectedDot != null && position != mAdapter.size -1) {
                float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                mSelectedDot.setTranslationX((position * dx) + positionOffset * dx);
            }
        }

        @Override
        public void onPageSelected(int position) {
            position %= mAdapter.size;
            /**
             * 如果已经是最后一个,那么则直接把小圆点定位到那,不然滑动效果会出错
             */
            if (mSelectedDot != null && position == mAdapter.size - 1) {
                float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                mSelectedDot.setTranslationX(position * dx);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

	        }
	    });

	}

可以看到我们首先添加了ViewPager，然后我们添加了未选中指示器的Group，之后我们才添加的选中的指示器。在添加选中指示器的时候用了一个post，这个方法是把runnable里的代码添加到任务栈，前面的任务结束了这个任务才会执行，所以我们可以获取到第一个未选中小圆点的坐标，然后把选中指示器覆盖上去，后来我们移动的时候就直接移动选中的指示器就好了。

关于滑动指示器，其实就是用了一个setTranslationX()方法，这个方法可以虽然移动View的位置，但是没有真正的移动该View，所以我们在移动的时候根据position来计算，但是当移动到最后一个的时候就不应该用这个方法了，如果还用的话就会造成一种『出界』了的感觉，所以这里在最后一个的时候不进入方法。

有人应该会想到『在第一页的时候』，这里我们大家可以自己测试一下，当手指向右滑的时候，就已经是size-1页了，所以我们直接就用一个判断就够了
