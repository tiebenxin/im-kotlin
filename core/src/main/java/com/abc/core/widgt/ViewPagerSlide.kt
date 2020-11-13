package com.abc.core.widgt

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/*
* 禁止滑动viewpager
* */
class ViewPagerSlide : ViewPager {
    private var scrollable = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return scrollable
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return scrollable
    }


}
