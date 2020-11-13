package com.abc.core.widgt.recycler

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Paint.Style
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Fancy progress indicator for Material theme.
 *
 * @hide
 */
class MaterialDrawable(context: Context, parent: NewPullRefreshLayout) :
    RefreshDrawable(context, parent), Animatable {

    private val COLORS = intArrayOf(Color.BLACK)
    /**
     * The list of animators operating on this drawable.
     */
    private val mAnimators = ArrayList<Animation>()

    /**
     * The indicator ring, used to manage animation state.
     */
    private val mRing: Ring

    /**
     * Canvas rotation in degrees.
     */
    private var rotation: Float = 0.toFloat()
        set(rotation) {
            field = rotation
            invalidateSelf()
        }

    private val mResources: Resources
    private val mParent: View
    private var mAnimation: Animation? = null
    private var mRotationCount: Float = 0.toFloat()
    private var mWidth: Double = 0.toDouble()
    private var mHeight: Double = 0.toDouble()
    private var mFinishAnimation: Animation? = null
    private var mShadowRadius: Int = 0
    private var mPadding: Int = 0
    private var mCircle: ShapeDrawable? = null
    private var mTop: Int = 0
    private val mDiameter: Int

    private val mCallback = object : Drawable.Callback {
        override fun invalidateDrawable(d: Drawable) {
            invalidateSelf()
        }

        override fun scheduleDrawable(d: Drawable, what: Runnable, `when`: Long) {
            scheduleSelf(what, `when`)
        }

        override fun unscheduleDrawable(d: Drawable, what: Runnable) {
            unscheduleSelf(what)
        }
    }

    @Retention(RetentionPolicy.CLASS)
    @IntDef(LARGE, DEFAULT)
    annotation class ProgressDrawableSize

    init {
        mParent = parent
        mResources = context.resources

        mRing = Ring(mCallback)
        mRing.setColors(COLORS)

        updateSizes(DEFAULT)
        setupAnimators()
        createCircleDrawable()
        setBackgroundColor(CIRCLE_BG_LIGHT)
        mDiameter = dp2px(40)
        mTop = -mDiameter - (getRefreshLayout()!!.finalOffset - mDiameter) / 2
    }

    private fun createCircleDrawable() {
        val radius = (CIRCLE_DIAMETER / 2).toFloat()
        val density = context!!.resources.displayMetrics.density
        val diameter = (radius * density * 2f).toInt()
        val shadowYOffset = (density * Y_OFFSET).toInt()
        val shadowXOffset = (density * X_OFFSET).toInt()

        mShadowRadius = (density * SHADOW_RADIUS).toInt()

        val oval = OvalShadow(mShadowRadius, diameter)
        mCircle = ShapeDrawable(oval)
        //        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
        mCircle!!.paint.setShadowLayer(
            mShadowRadius.toFloat(), shadowXOffset.toFloat(), shadowYOffset.toFloat(),
            KEY_SHADOW_COLOR
        )
        mPadding = mShadowRadius

        mCircle!!.paint.color = Color.WHITE
    }

    private inner class OvalShadow(
        private val mShadowRadius: Int,
        private val mCircleDiameter: Int
    ) : OvalShape() {
        private val mRadialGradient: RadialGradient
        private val mShadowPaint: Paint

        init {
            mShadowPaint = Paint()
            mRadialGradient = RadialGradient(
                (mCircleDiameter / 2).toFloat(),
                (mCircleDiameter / 2).toFloat(),
                mShadowRadius.toFloat(),
                intArrayOf(FILL_SHADOW_COLOR, Color.TRANSPARENT),
                null,
                Shader.TileMode.CLAMP
            )
            mShadowPaint.shader = mRadialGradient
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            val x = this@MaterialDrawable.bounds.centerX()
            val y = this@MaterialDrawable.bounds.centerY()
            canvas.drawCircle(
                x.toFloat(), y.toFloat(), (mCircleDiameter / 2 + mShadowRadius).toFloat(),
                mShadowPaint
            )
            canvas.drawCircle(x.toFloat(), y.toFloat(), (mCircleDiameter / 2).toFloat(), paint)
        }
    }

    private fun setSizeParameters(
        progressCircleWidth: Double, progressCircleHeight: Double,
        centerRadius: Double, strokeWidth: Double, arrowWidth: Float, arrowHeight: Float
    ) {
        val ring = mRing
        val metrics = mResources.displayMetrics
        val screenDensity = metrics.density

        mWidth = progressCircleWidth * screenDensity
        mHeight = progressCircleHeight * screenDensity
        ring.strokeWidth = strokeWidth.toFloat() * screenDensity
        ring.centerRadius = centerRadius * screenDensity
        ring.setColorIndex(0)
        ring.setArrowDimensions(arrowWidth * screenDensity, arrowHeight * screenDensity)
        ring.setInsets(mWidth.toInt(), mHeight.toInt())
    }

    /**
     * Set the overall size for the progress spinner. This updates the radius
     * and stroke width of the ring.
     */
    fun updateSizes(@ProgressDrawableSize size: Int) {
        if (size == LARGE) {
            setSizeParameters(
                CIRCLE_DIAMETER_LARGE.toDouble(),
                CIRCLE_DIAMETER_LARGE.toDouble(),
                CENTER_RADIUS_LARGE.toDouble(),
                STROKE_WIDTH_LARGE.toDouble(),
                ARROW_WIDTH_LARGE.toFloat(),
                ARROW_HEIGHT_LARGE.toFloat()
            )
        } else {
            setSizeParameters(
                CIRCLE_DIAMETER.toDouble(),
                CIRCLE_DIAMETER.toDouble(),
                CENTER_RADIUS.toDouble(),
                STROKE_WIDTH.toDouble(),
                ARROW_WIDTH.toFloat(),
                ARROW_HEIGHT.toFloat()
            )
        }
    }

    /**
     * @param show Set to true to display the arrowhead on the progress spinner.
     */
    fun showArrow(show: Boolean) {
        mRing.setShowArrow(show)
    }

    /**
     * @param scale Set the scale of the arrowhead for the spinner.
     */
    fun setArrowScale(scale: Float) {
        mRing.setArrowScale(scale)
    }

    /**
     * Set the onCreate and end trim for the progress spinner arc.
     *
     * @param startAngle onCreate angle
     * @param endAngle   end angle
     */
    fun setStartEndTrim(startAngle: Float, endAngle: Float) {
        mRing.startTrim = startAngle
        mRing.endTrim = endAngle
    }

    /**
     * Set the amount of rotation to apply to the progress spinner.
     *
     * @param rotation Rotation is from [0..1]
     */
    fun setProgressRotation(rotation: Float) {
        mRing.rotation = rotation
    }

    /**
     * Update the background color of the circle image view.
     */
    fun setBackgroundColor(color: Int) {
        mRing.setBackgroundColor(color)
    }

    override fun setPercent(percent: Float) {
        var percent = percent
        if (percent < .4f)
            return
        percent = (percent - .4f) / .6f
        alpha = (MAX_ALPHA * percent).toInt()
        showArrow(true)
        val strokeStart = percent * .8f
        setStartEndTrim(0f, MAX_PROGRESS_ANGLE.coerceAtMost(strokeStart))
        setArrowScale(1f.coerceAtMost(percent))
        val rotation: Float = if (percent < .8f) 0f else (percent - .8f) / .2f * .25f
        setProgressRotation(rotation)
    }

    /**
     * Set the colors used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.
     *
     * @param colors
     */
    override fun setColorSchemeColors(vararg colors: Int) {
        mRing.setColors(colors)
        mRing.setColorIndex(0)
    }

    override fun offsetTopAndBottom(offset: Int) {
        mTop += offset
        invalidateSelf()
    }
    //
    //    @Override
    //    public int getIntrinsicHeight() {
    //        return (int) mHeight;
    //    }
    //
    //    @Override
    //    public int getIntrinsicWidth() {
    //        return (int) mWidth;
    //    }

    override fun draw(c: Canvas) {
        val bounds = bounds
        val saveCount = c.save()
        c.translate(0f, mTop.toFloat())
        mCircle!!.draw(c)
        //        c.scale((float)mWidth / bounds.width(), (float)mHeight / bounds.height());
        c.rotate(rotation, bounds.exactCenterX(), bounds.exactCenterY())
        mRing.draw(c, bounds)
        c.restoreToCount(saveCount)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        val w = right - left
        super.setBounds(w / 2 - mDiameter / 2, top, w / 2 + mDiameter / 2, mDiameter + top)
    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context!!.resources.displayMetrics
        ).toInt()
    }

    override fun setAlpha(alpha: Int) {
        mRing.alpha = alpha
    }

    override fun getAlpha(): Int {
        return mRing.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mRing.setColorFilter(colorFilter)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun isRunning(): Boolean {
        val animators = mAnimators
        val N = animators.size
        for (i in 0 until N) {
            val animator = animators[i]
            if (animator.hasStarted() && !animator.hasEnded()) {
                return true
            }
        }
        return false
    }

    override fun start() {
        mAnimation!!.reset()
        mRing.storeOriginals()
        // Already showing some part of the ring
        if (mRing.endTrim != mRing.startTrim) {
            mParent.startAnimation(mFinishAnimation)
        } else {
            mRing.setColorIndex(0)
            mRing.resetOriginals()
            mParent.startAnimation(mAnimation)
        }
    }

    override fun stop() {
        mParent.clearAnimation()
        rotation = 0f
        mRing.setShowArrow(false)
        mRing.setColorIndex(0)
        mRing.resetOriginals()
    }

    private fun setupAnimators() {
        val ring = mRing
        val finishRingAnimation = object : Animation() {
            public override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                // shrink back down and complete a full rotation before starting other circles
                // Rotation goes between [0..1].
                val targetRotation =
                    (Math.floor((ring.startingRotation / MAX_PROGRESS_ARC).toDouble()) + 1f).toFloat()
                val startTrim =
                    ring.startingStartTrim + (ring.startingEndTrim - ring.startingStartTrim) * interpolatedTime
                ring.startTrim = startTrim
                val rotation =
                    ring.startingRotation + (targetRotation - ring.startingRotation) * interpolatedTime
                ring.rotation = rotation
                ring.setArrowScale(1 - interpolatedTime)
            }
        }
        finishRingAnimation.interpolator = EASE_INTERPOLATOR
        finishRingAnimation.duration = (ANIMATION_DURATION / 2).toLong()
        finishRingAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                ring.goToNextColor()
                ring.storeOriginals()
                ring.setShowArrow(false)
                mParent.startAnimation(mAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        val animation = object : Animation() {
            public override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                // The minProgressArc is calculated from 0 to create an angle that
                // matches the stroke width.
                val minProgressArc =
                    Math.toRadians(ring.strokeWidth / (2.0 * Math.PI * ring.centerRadius)).toFloat()
                val startingEndTrim = ring.startingEndTrim
                val startingTrim = ring.startingStartTrim
                val startingRotation = ring.startingRotation

                // Offset the minProgressArc to where the endTrim is located.
                val minArc = MAX_PROGRESS_ARC - minProgressArc
                val endTrim = startingEndTrim + minArc * START_CURVE_INTERPOLATOR.getInterpolation(
                    interpolatedTime
                )
                ring.endTrim = endTrim

                val startTrim = startingTrim + MAX_PROGRESS_ARC * END_CURVE_INTERPOLATOR
                    .getInterpolation(interpolatedTime)
                ring.startTrim = startTrim

                var rotation = startingRotation + 0.25f * interpolatedTime
                ring.rotation = rotation

                val groupRotation =
                    720.0f / NUM_POINTS * interpolatedTime + 720.0f * (mRotationCount / NUM_POINTS)
                rotation = groupRotation
            }
        }
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.RESTART
        animation.interpolator = LINEAR_INTERPOLATOR
        animation.duration = ANIMATION_DURATION.toLong()
        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {
                mRotationCount = 0f
            }

            override fun onAnimationEnd(animation: Animation) {
                // do nothing
            }

            override fun onAnimationRepeat(animation: Animation) {
                ring.storeOriginals()
                ring.goToNextColor()
                ring.startTrim = ring.endTrim
                mRotationCount = (mRotationCount + 1) % NUM_POINTS
            }
        })
        mFinishAnimation = finishRingAnimation
        mAnimation = animation
    }

    private class Ring(private val mCallback: Drawable.Callback) {
        private val mTempBounds = RectF()
        private val mPaint = Paint()
        private val mArrowPaint = Paint()

        var startTrim = 0.0f
            set(startTrim) {
                field = startTrim
                invalidateSelf()
            }
        var endTrim = 0.0f
            set(endTrim) {
                field = endTrim
                invalidateSelf()
            }
        var rotation = 0.0f
            set(rotation) {
                field = rotation
                invalidateSelf()
            }
        /**
         * @param strokeWidth Set the stroke width of the progress spinner in pixels.
         */
        var strokeWidth = 5.0f
            set(strokeWidth) {
                field = strokeWidth
                mPaint.strokeWidth = strokeWidth
                invalidateSelf()
            }
        var insets = 2.5f
            private set

        private var mColors: IntArray? = null
        // mColorIndex represents the offset into the available mColors that the
        // progress circle should currently display. As the progress circle is
        // animating, the mColorIndex moves by one to the next available color.
        private var mColorIndex: Int = 0
        var startingStartTrim: Float = 0.toFloat()
            private set
        var startingEndTrim: Float = 0.toFloat()
            private set
        /**
         * @return The amount the progress spinner is currently rotated, between [0..1].
         */
        var startingRotation: Float = 0.toFloat()
            private set
        private var mShowArrow: Boolean = false
        private var mArrow: Path? = null
        private var mArrowScale: Float = 0.toFloat()
        /**
         * @param centerRadius Inner radius in px of the circle the progress
         * spinner arc traces.
         */
        var centerRadius: Double = 0.toDouble()
        private var mArrowWidth: Int = 0
        private var mArrowHeight: Int = 0
        /**
         * @return Current alpha of the progress spinner and arrowhead.
         */
        /**
         * @param alpha Set the alpha of the progress spinner and associated arrowhead.
         */
        var alpha: Int = 0
        private val mCirclePaint = Paint()
        private var mBackgroundColor: Int = 0

        init {

            mPaint.strokeCap = Paint.Cap.SQUARE
            mPaint.isAntiAlias = true
            mPaint.style = Style.STROKE

            mArrowPaint.style = Style.FILL
            mArrowPaint.isAntiAlias = true
        }

        fun setBackgroundColor(color: Int) {
            mBackgroundColor = color
        }

        /**
         * Set the dimensions of the arrowhead.
         *
         * @param width  Width of the hypotenuse of the arrow head
         * @param height Height of the arrow point
         */
        fun setArrowDimensions(width: Float, height: Float) {
            mArrowWidth = width.toInt()
            mArrowHeight = height.toInt()
        }

        /**
         * Draw the progress spinner
         */
        fun draw(c: Canvas, bounds: Rect) {
            val arcBounds = mTempBounds
            arcBounds.set(bounds)
            arcBounds.inset(insets, insets)

            val startAngle = (startTrim + rotation) * 360
            val endAngle = (endTrim + rotation) * 360
            val sweepAngle = endAngle - startAngle

            mPaint.color = mColors!![mColorIndex]
            c.drawArc(arcBounds, startAngle, sweepAngle, false, mPaint)

            drawTriangle(c, startAngle, sweepAngle, bounds)

            if (alpha < 255) {
                mCirclePaint.color = mBackgroundColor
                mCirclePaint.alpha = 255 - alpha
                c.drawCircle(
                    bounds.exactCenterX(), bounds.exactCenterY(), (bounds.width() / 2).toFloat(),
                    mCirclePaint
                )
            }
        }

        private fun drawTriangle(c: Canvas, startAngle: Float, sweepAngle: Float, bounds: Rect) {
            if (mShowArrow) {
                if (mArrow == null) {
                    mArrow = Path()
                    mArrow!!.fillType = Path.FillType.EVEN_ODD
                } else {
                    mArrow!!.reset()
                }

                // Adjust the position of the triangle so that it is inset as
                // much as the arc, but also centered on the arc.
                val inset = insets.toInt() / 2 * mArrowScale
                val x = (centerRadius * cos(0.0) + bounds.exactCenterX()).toFloat()
                val y = (centerRadius * sin(0.0) + bounds.exactCenterY()).toFloat()

                // Update the path each time. This works around an issue in SKIA
                // where concatenating a rotation matrix to a scale matrix
                // ignored a starting negative rotation. This appears to have
                // been fixed as of API 21.
                mArrow!!.moveTo(0f, 0f)
                mArrow!!.lineTo(mArrowWidth * mArrowScale, 0f)
                mArrow!!.lineTo(mArrowWidth * mArrowScale / 2, mArrowHeight * mArrowScale)
                mArrow!!.offset(x - inset, y)
                mArrow!!.close()
                // draw a triangle
                mArrowPaint.color = mColors!![mColorIndex]
                c.rotate(
                    startAngle + sweepAngle - ARROW_OFFSET_ANGLE, bounds.exactCenterX(),
                    bounds.exactCenterY()
                )
                c.drawPath(mArrow!!, mArrowPaint)
            }
        }

        /**
         * Set the colors the progress spinner alternates between.
         *
         * @param colors Array of integers describing the colors. Must be non-`null`.
         */
        fun setColors(colors: IntArray) {
            mColors = colors
            // if colors are reset, make sure to reset the color index as well
            setColorIndex(0)
        }

        /**
         * @param index Index into the color array of the color to display in
         * the progress spinner.
         */
        fun setColorIndex(index: Int) {
            mColorIndex = index
        }

        /**
         * Proceed to the next available ring color. This will automatically
         * wrap back to the beginning of colors.
         */
        fun goToNextColor() {
            mColorIndex = (mColorIndex + 1) % mColors!!.size
        }

        fun setColorFilter(filter: ColorFilter?) {
            mPaint.colorFilter = filter
            invalidateSelf()
        }

        fun setInsets(width: Int, height: Int) {
            val minEdge = Math.min(width, height).toFloat()
            val insets: Float
            if (centerRadius <= 0 || minEdge < 0) {
                insets = Math.ceil((strokeWidth / 2.0f).toDouble()).toFloat()
            } else {
                insets = (minEdge / 2.0f - centerRadius).toFloat()
            }
            this.insets = insets
        }

        /**
         * @param show Set to true to show the arrow head on the progress spinner.
         */
        fun setShowArrow(show: Boolean) {
            if (mShowArrow != show) {
                mShowArrow = show
                invalidateSelf()
            }
        }

        /**
         * @param scale Set the scale of the arrowhead for the spinner.
         */
        fun setArrowScale(scale: Float) {
            if (scale != mArrowScale) {
                mArrowScale = scale
                invalidateSelf()
            }
        }

        /**
         * If the onCreate / end trim are offset to begin with, store them so that
         * animation starts from that offset.
         */
        fun storeOriginals() {
            startingStartTrim = startTrim
            startingEndTrim = endTrim
            startingRotation = rotation
        }

        /**
         * Reset the progress spinner to default rotation, onCreate and end angles.
         */
        fun resetOriginals() {
            startingStartTrim = 0f
            startingEndTrim = 0f
            startingRotation = 0f
            startTrim = 0f
            endTrim = 0f
            rotation = 0f
        }

        private fun invalidateSelf() {
            mCallback.invalidateDrawable(null!!)
        }
    }

    /**
     * Squishes the interpolation curve into the second half of the animation.
     */
    private class EndCurveInterpolator : AccelerateDecelerateInterpolator() {
        override fun getInterpolation(input: Float): Float {
            return super.getInterpolation(0f.coerceAtLeast((input - 0.5f) * 2.0f))
        }
    }

    /**
     * Squishes the interpolation curve into the first half of the animation.
     */
    private class StartCurveInterpolator : AccelerateDecelerateInterpolator() {
        override fun getInterpolation(input: Float): Float {
            return super.getInterpolation(1f.coerceAtMost(input * 2.0f))
        }
    }

    companion object {
        private val LINEAR_INTERPOLATOR = LinearInterpolator()
        private val END_CURVE_INTERPOLATOR = EndCurveInterpolator()
        private val START_CURVE_INTERPOLATOR = StartCurveInterpolator()
        private val EASE_INTERPOLATOR = AccelerateDecelerateInterpolator()

        // Maps to ProgressBar.Large style
        internal const val LARGE = 0
        // Maps to ProgressBar default style
        internal const val DEFAULT = 1

        // Maps to ProgressBar default style
        private val CIRCLE_DIAMETER = 40
        private val CENTER_RADIUS = 8.75f //should add up to 10 when + stroke_width
        private val STROKE_WIDTH = 2.5f

        // Maps to ProgressBar.Large style
        private val CIRCLE_DIAMETER_LARGE = 56
        private const val CENTER_RADIUS_LARGE = 12.5f
        private const val STROKE_WIDTH_LARGE = 3f

        private const val MAX_PROGRESS_ANGLE = .8f
        private const val CIRCLE_BG_LIGHT = -0x50506
        private const val MAX_ALPHA = 255

        /**
         * The duration of a single progress spin in milliseconds.
         */
        private const val ANIMATION_DURATION = 1000 * 80 / 60

        /**
         * The number of points in the progress "star".
         */
        private const val NUM_POINTS = 5f

        /**
         * Layout info for the arrowhead in dp
         */
        private const val ARROW_WIDTH = 10
        private const val ARROW_HEIGHT = 5
        private const val ARROW_OFFSET_ANGLE = 5f

        /**
         * Layout info for the arrowhead for the large spinner in dp
         */
        private const val ARROW_WIDTH_LARGE = 12
        private const val ARROW_HEIGHT_LARGE = 6
        private const val MAX_PROGRESS_ARC = .8f

        /**
         * Circle Drawable *
         */
        private const val KEY_SHADOW_COLOR = 0x1E000000
        private const val FILL_SHADOW_COLOR = 0x3D000000
        // PX
        private const val X_OFFSET = 0f
        private const val Y_OFFSET = 1.75f
        private const val SHADOW_RADIUS = 3.5f
        private const val SHADOW_ELEVATION = 4
    }
}