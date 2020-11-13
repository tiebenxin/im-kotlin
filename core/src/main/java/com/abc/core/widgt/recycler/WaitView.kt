package com.abc.core.widgt.recycler


import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import com.abc.core.R
import com.abc.core.utils.DensityUtil


class WaitView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()
    private var midx: Float = 0.toFloat()
    private var midy: Float = 0.toFloat()
    private var osize = 10
    private var opadding = 40f
    private var isAutoPlay = false
    private val colors = intArrayOf(-0x66ffff01, -0x66ff0100, -0x66010000)
    private val animtime: Long = 500

    private var animValue = 0f
    private var animator: ValueAnimator? = null

    fun setAutoPlay(autoPlay: Boolean) {
        isAutoPlay = autoPlay
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = measuredWidth.toFloat()
        height = measuredHeight.toFloat()
        midx = (width + osize) / 2
        midy = (height + osize) / 2
    }

    init {
        osize = DensityUtil.dip2px(context, 4f)
        opadding = DensityUtil.dip2px(context, 16f).toFloat()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaitView)
        isAutoPlay = typedArray.getBoolean(R.styleable.WaitView_waitview_state, false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = colors[0]
        canvas.drawCircle(midx - opadding + animValue, midy, osize.toFloat(), paint)
        paint.color = colors[1]
        canvas.drawCircle(midx, midy, osize.toFloat(), paint)
        paint.color = colors[2]
        canvas.drawCircle(midx + opadding - animValue, midy, osize.toFloat(), paint)


    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (!isAutoPlay) {
            return
        }

        if (visibility == View.VISIBLE) {
            //判断是否显示
            startAnim()
        } else {
            stopAnim()
        }

    }

    fun startAnim() {
        if (animator != null) {
            return
        }

        animator = ValueAnimator.ofFloat(0f, opadding)
        animator!!.duration = animtime
        animator!!.repeatCount = ValueAnimator.INFINITE
        animator!!.repeatMode = ValueAnimator.REVERSE
        animator!!.addUpdateListener { animation ->
            animValue = animation.animatedValue as Float


            invalidate()
        }
        animator!!.addListener(object : Animator.AnimatorListener {
            internal var i = 0

            override fun onAnimationStart(animator: Animator) {
                i = 0
            }

            override fun onAnimationEnd(animator: Animator) {

            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {
                i++
                if (i % 2 == 0) {
                    return
                }
                val temp = colors[2]
                colors[2] = colors[1]
                colors[1] = colors[0]
                colors[0] = temp

            }
        })
        animator!!.start()
    }


    fun stopAnim() {
        if (animator == null)
            return
        animator!!.end()
        animator!!.cancel()
        animator = null
    }
}
