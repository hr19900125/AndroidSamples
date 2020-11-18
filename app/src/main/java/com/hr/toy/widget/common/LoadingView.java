package com.hr.toy.widget.common;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;

import com.hr.toy.R;

/**
 * <p>关于LoadingView使用可以参考：<a href="http://redmine.meizu.com/documents/1144">LoadingVIew使用说明</a></p>
 * <dev>
 * <h6>属性说明：</h6>
 * <table>
 * <tr>
 * <td>属性名称</td>
 * <td>属性说明</td>
 * </tr>
 * <tr>
 * <td>mcLoadingRadius</td>
 * <td>圆环半径</td>
 * </tr>
 * <tr>
 * <td>mcRingWidth</td>
 * <td>圆环宽度</td>
 * </tr>
 * <tr>
 * <td>mcLBackground</td>
 * <td>圆环背景色</td>
 * </tr>
 * <tr>
 * <td>mcLForeground</td>
 * <td>圆环转动部分颜色</td>
 * </tr>
 * <tr>
 * <td>其他属性</td>
 * <td>现在已经不再使用</td>
 * </tr>
 * </table>
 * <h6>关键方法说明：</h6>
 * <table>
 * <tr>
 * <td>方法名称</td>
 * <td>方法功能</td>
 * </tr>
 * <tr>
 * <td>public LoadingView(Context context)</td>
 * <td>构造函数，不推荐使用这个</td>
 * </tr>
 * <tr>
 * <td>public LoadingView(Context context, float radius, float ringWidth)</td>
 * <td>构造函数，指定圆环半径和圆环宽度来创建LoadingView</td>
 * </tr>
 * <tr>
 * <td>public LoadingView(Context context, AttributeSet attrs)</td>
 * <td>构造函数,从XML创建使用时调用</td>
 * </tr>
 * <tr>
 * <td>public void setBarColor(int barColor)</td>
 * <td>设置进度条颜色（即设置mcLForeground）</td>
 * </tr>
 * <tr>
 * <td>public void setBarBackgroundColor(int backgroundColor)</td>
 * <td>设置进度背景颜色（即设置mcLBackground）</td>
 * </tr>
 * <tr>
 * <td>public void startAnimator()</td>
 * <td>开始加载动画，默认情况不需要调用，动画会自动开始</td>
 * </tr>
 * <tr>
 * <td>public void stopAnimator()</td>
 * <td>停止加载动画，内部调用的是Animator.cancel()</td>
 * </tr>
 * <tr>
 * <td>public void startProgressAnimation</td>
 * <td>现在已经废弃，不应该使用，内部空实现</td>
 * </tr>
 * <tr>
 * <td>getSweepAngle() setSweepAngle(float mSweepAngle) getStartAngle() setStartAngle</td>
 * <td>为内部属性动画使用，应用无需调用</td>
 * </tr>
 * </table>
 * </dev>
 * <h4>注意细节：</h4>
 * <p>LoadingView在可见时动画自动开始，在不可见时，动画会cancel掉。即LoadingView的isShown()方法返回true时会启动动画，而isShown()返回为false，即在屏幕上不可见时会停止动画。另外，LoadingView所依附的Window不可见时，动画会cancel掉。可以通过setVisibility()的方式来控制LoadingView。</p>
 * <pre>
 *         <h6>样式使用：</h6>
 *         <p>
 *             Flyme 5.0的加载动画做了一些小调整，现在加载提示文字在加载动画下方,为了方便应用调整样式，MeizuCommon里添加了 LoadingView的style。
 *          背景为白色时，LoadingView使用的style为 Widget.MeizuCommon.LoadingView.Light，LoadingView下方提示文字的style使用LoadingViewTextStyle.Light
 *          背景为深色时，LoadingView使用的style为 Widget.MeizuCommon.LoadingView.Dark，LoadingView下方提示文字的style使用LoadingViewTextStyle.Dark
 *         </p>
 *         <h6>如下是MeizuCommon中添加的style</h6>
 *         <pre>
 *              &lt;!--Style for LoadingView--&gt;
 *              &lt;style name="Widget.MeizuCommon.LoadingView"&gt;
 *              &lt;item name="mcLoadingRadius"&gt;@dimen/mcLoadingRadius&lt;/item&gt;
 *              &lt;item name="mcRingWidth"&gt;@dimen/mcRingWidth&lt;/item&gt;
 *              &lt;/style&gt;
 *              &lt;!--LoadingView样式,浅色背景时使用(默认)--&gt;
 *              &lt;style name="Widget.MeizuCommon.LoadingView.Light"&gt;
 *              &lt;item name="mcLoadingRadius"&gt;10dip&lt;/item&gt;
 *              &lt;item name="mcRingWidth"&gt;1.5dip&lt;/item&gt;
 *              &lt;item name="mcLBackground"&gt;@color/mc_loading_view_background_light&lt;/item&gt;
 *              &lt;/style&gt;
 *              &lt;!--LoadingView样式,深色背景时使用--&gt;
 *              &lt;style name="Widget.MeizuCommon.LoadingView.Dark"&gt;
 *              &lt;item name="mcLoadingRadius"&gt;10dip&lt;/item&gt;
 *              &lt;item name="mcRingWidth"&gt;1.5dip&lt;/item&gt;
 *              &lt;item name="mcLBackground"&gt;@color/mc_loading_view_background_dark&lt;/item&gt;
 *              &lt;/style&gt;
 *              &lt;style name="LoadingViewTextStyle"&gt;
 *              &lt;item name="android:textSize"&gt;14sp&lt;/item&gt;
 *              &lt;item name="android:includeFontPadding"&gt;false&lt;/item&gt;
 *              &lt;/style&gt;
 *              &lt;!--LoadingView文字样式,浅色背景时使用--&gt;
 *              &lt;style name="LoadingViewTextStyle.Light"&gt;
 *              &lt;item name="android:textColor"&gt;@color/mc_loading_view_text_light&lt;/item&gt;
 *              &lt;/style&gt;
 *              &lt;!--LoadingView文字样式,深色背景时使用--&gt;
 *              &lt;style name="LoadingViewTextStyle.Dark"&gt;
 *              &lt;item name="android:textColor"&gt;@color/mc_loading_view_text_dark&lt;/item&gt;
 *              &lt;/style&gt;
 *         </pre>
 *           <h6>使用示例：</h6>
 *     <pre>
 *         <h6>(1)在XML中使用（LoadingView可见时动画会自动开始</h6>
 *         &lt;com.meizu.common.widget.LoadingView
 *         style="@style/Widget.MeizuCommon.LoadingView.Light"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:layout_centerHorizontal="true" /&gt;
 *         &lt;TextView
 *         style="@style/LoadingViewTextStyle.Light"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:layout_centerHorizontal="true"
 *         android:layout_marginTop="32dp"
 *         android:text="正在加载" /&gt;
 *     </pre>
 *     <pre>
 *         <h6>(2)MeizuCommon里添加了mc_loading_view_layout.xml （用于白色背景）和mc_loading_view_layout_dark.xml （用于黑色背景），可以直接include来使用（于2015.08.31推送到flyme5-dev)</h6>
 *          &lt;include
 *          android:id="@+id/loadingViewLayout"
 *          layout="@layout/mc_loading_view_layout"
 *          android:layout_width="wrap_content"
 *          android:layout_height="wrap_content"
 *          android:layout_centerInParent="true"
 *          android:visibility="gone" /&gt;
 *     </pre>
 *     <pre>
 *         <h6>(3)也可以在代码中动态创建LoadingView（可见时动画会自动开始）</h6>
 *         LinearLayout containerLayout = (LinearLayout) findViewById(R.id.containerLayout);
 *         LoadingView loadingView=new LoadingView(this,30f,3f);
 *         loadingView.setBarBackgroundColor(0x19000000);
 *         containerLayout.addView(loadingView);
 *     </pre>
 *     </pre>
 *
 * @author chenminghai@meizu.com
 */

@SuppressLint("NewApi")
public class LoadingView extends View {
    //private final String TAG="LoadingView";
    private Paint mPaint = null;
    private Paint mPaintArc = null;//用于绘制进度条
    private Paint mPaintArcBack = null;//用于绘制进度条背景（圆环）
    private Paint mDotPaint = null;
    private Context mContext = null;

    private Animator mLoadingAnimator = null;
    private float mStartAngle;
    private float mSweepAngle;
    private final long LOADING_ANIM_DURATION = 1760;
    public static final String START_ANGLE_PROPERTY = "startAngle";
    public static final String SWEEP_ANGLE_PROPERTY = "sweepAngle";

    private RectF mCircleBounds = null;
    private int mPaintWidth = 0;

    private float mRadius;
    private float mRingWidth;
    private float mCentX;
    private float mCentY;
    private int mThemeColor;
    private int mBackgroundColor;
    private int mForegroundColor;

    private static final int LOADING_ANIMATION = 1;
    private static final int PROGRESS_ANIMATION = 2;
    private int mLoadingState = LOADING_ANIMATION;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.MeizuCommon_LoadingViewStyle);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(36);

        TypedArray b = mContext.obtainStyledAttributes(R.styleable.MZTheme);
        mThemeColor = b.getInt(R.styleable.MZTheme_mzThemeColor, Color.GREEN);
        mForegroundColor = b.getInt(R.styleable.MZTheme_mzThemeColorLevel5, getResources().getColor(R.color.Blue_5));
        mBackgroundColor = b.getInt(R.styleable.MZTheme_mzThemeColorLevel1, getResources().getColor(R.color.Blue_1));
        b.recycle();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, R.attr.MeizuCommon_LoadingStyle, 0);
        mRadius = a.getDimension(R.styleable.LoadingView_mcLoadingRadius, 24f);
        mRingWidth = a.getDimension(R.styleable.LoadingView_mcRingWidth, 10f);
        mBackgroundColor = a.getColor(R.styleable.LoadingView_mcLBackground, mBackgroundColor);
        mForegroundColor = a.getColor(R.styleable.LoadingView_mcLForeground, mForegroundColor);
        mLoadingState = a.getInt(R.styleable.LoadingView_mcLoadingState, LOADING_ANIMATION);
        a.recycle();

        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(mForegroundColor);
        mPaintArc.setStyle(Paint.Style.STROKE);
//        mPaintArc.setStrokeCap(Paint.Cap.SQUARE);

        mDotPaint = new Paint(mPaintArc);
        mDotPaint.setStyle(Paint.Style.FILL);

        mPaintArcBack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArcBack.setAntiAlias(true);
        mPaintArcBack.setColor(mBackgroundColor);
        mPaintArcBack.setStyle(Paint.Style.STROKE);

        mPaintArc.setStrokeWidth(mRingWidth - mPaintWidth);
        mPaintArcBack.setStrokeWidth(mRingWidth - mPaintWidth);
        init();
    }

    public LoadingView(Context context, float radius, float ringWidth) {
        this(context, null);
        mRadius = radius;
        mRingWidth = ringWidth;
        init();
    }

    private void init() {
        mCentX = getX() + getPaddingLeft() + mRadius + 2 * mPaintWidth + mRingWidth;
        mCentY = getY() + getPaddingTop() + mRadius + 2 * mPaintWidth + mRingWidth;

        mCircleBounds = new RectF();
        mCircleBounds.left = mCentX - mRadius - mPaintWidth / 2 - mRingWidth / 2;
        mCircleBounds.top = mCentY - mRadius - mPaintWidth / 2 - mRingWidth / 2;
        mCircleBounds.right = mCentX + mRadius + mPaintWidth / 2 + mRingWidth / 2;
        mCircleBounds.bottom = mCentY + mRadius + mPaintWidth / 2 + mRingWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(getWidth() / 2 - mRadius - mRingWidth, getHeight() / 2 - mRadius - mRingWidth);

        if (mForegroundColor == mBackgroundColor) {
            mPaintArcBack.setAlpha(26);
        }

        if (mLoadingState == LOADING_ANIMATION) {
            drawLoadingAnimation(canvas);
            return;
        }

        super.onDraw(canvas);
    }

    private void drawLoadingAnimation(Canvas canvas) {
        canvas.drawArc(mCircleBounds, -90, 360, false, mPaintArcBack);
        canvas.drawArc(mCircleBounds, mStartAngle, mSweepAngle, false, mPaintArc);

        float halfWidth = mCircleBounds.width() / 2;
        float halfHeight = mCircleBounds.height() / 2;
        float radius = mPaintArc.getStrokeWidth() / 2;

        float dotoneX = mCircleBounds.right - halfWidth * (float) (1 - Math.cos(Math.toRadians(mStartAngle)));
        float dotoneY = mCircleBounds.bottom - halfHeight * (float) (1 - Math.sin(Math.toRadians(mStartAngle)));

        canvas.drawCircle(dotoneX, dotoneY, radius, mDotPaint);

        dotoneX = mCircleBounds.right - halfWidth * (float) (1 - Math.cos(Math.toRadians(mSweepAngle + mStartAngle)));
        dotoneY = mCircleBounds.bottom - halfHeight * (float) (1 - Math.sin(Math.toRadians(mSweepAngle + mStartAngle)));
        canvas.drawCircle(dotoneX, dotoneY, radius, mDotPaint);
    }

    /**
     * 已被废弃，不应该被调用
     */
    @Deprecated
    public void startProgressAnimation() {
        mLoadingState = PROGRESS_ANIMATION;
    }

    public void startAnimator() {
        startLoadingAnimation();
    }

    public void stopAnimator() {
        if (null != mLoadingAnimator) {
            mLoadingAnimator.cancel();
            mLoadingAnimator = null;
        }
    }

    private void startLoadingAnimation() {
        if (null != mLoadingAnimator && mLoadingAnimator.isRunning()) {
            return;
        }
        mLoadingState = LOADING_ANIMATION;
        mLoadingAnimator = createLoadingAnimator();
        mLoadingAnimator.start();
    }

    private Animator createLoadingAnimator() {
        //控制drawArc的startAngle
        Keyframe key1 = Keyframe.ofFloat(0f, -90);/*0-90,起始startAngle*/
        Keyframe key2 = Keyframe.ofFloat(0.5f, 330);/*420-90,sweepAngel最大时的startAngle*/
        Keyframe key3 = Keyframe.ofFloat(1, 630);/*720-90,结束时的startAngle，刚好两圈*/
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofKeyframe(START_ANGLE_PROPERTY, key1, key2, key3);
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(SWEEP_ANGLE_PROPERTY, 0f, -144f, 0f);
        final ObjectAnimator loadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvhStart, pvhSweep);
        loadingAnim.setDuration(LOADING_ANIM_DURATION);
        loadingAnim.setInterpolator(new LinearInterpolator());
        loadingAnim.setRepeatCount(ValueAnimator.INFINITE);
        return loadingAnim;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            this.startLoadingAnimation();
        } else if (visibility == View.INVISIBLE || visibility == View.GONE) {
            if (null != mLoadingAnimator) {
                mLoadingAnimator.cancel();
                mLoadingAnimator = null;
            }
        }
    }

    //在LoadingView可见性发生改变时控制动画
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (LOADING_ANIMATION != mLoadingState)
            return;
        if (visibility != View.VISIBLE) {
            if (null != mLoadingAnimator) {
                mLoadingAnimator.cancel();
                mLoadingAnimator = null;
            }
        } else if (this.isShown()) {
            this.startLoadingAnimation();
        }
    }

    //在LoadingView可见性发生改变时控制动画
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (LOADING_ANIMATION != mLoadingState)
            return;
        if (visibility != View.VISIBLE) {
            if (null != mLoadingAnimator) {
                mLoadingAnimator.cancel();
                mLoadingAnimator = null;
            }
        } else if (this.isShown()) {
            this.startLoadingAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw;
        int dh;
        dw = (int) (2 * (mRadius + mRingWidth + 2));
        dh = dw;

        dw += getPaddingLeft() + getPaddingRight();
        dh += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(resolveSizeAndState(dw, widthMeasureSpec, 0),
                resolveSizeAndState(dh, heightMeasureSpec, 0));
    }

    /**
     * 设置进度条颜色
     *
     * @param barColor
     */
    public void setBarColor(int barColor) {
        if (mPaintArc != null && mPaintArc.getColor() != barColor) {
            mPaintArc.setColor(barColor);
            mDotPaint.setColor(barColor);
            mForegroundColor = barColor;
            postInvalidate();
        }
    }

    public int getBarColor() {
        return mForegroundColor;
    }

    /**
     * 设置进度条背景颜色
     *
     * @param backgroundColor
     */
    public void setBarBackgroundColor(int backgroundColor) {
        if (mPaintArcBack != null && mPaintArcBack.getColor() != backgroundColor) {
            mPaintArcBack.setColor(backgroundColor);
            mBackgroundColor = backgroundColor;
            postInvalidate();
        }
    }

    public int getBarBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * 用于内部属性动画调用，应用无需调用
     */
    public float getSweepAngle() {
        return mSweepAngle;
    }

    /**
     * 用于内部属性动画调用，应用无需调用
     */
    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
        invalidate();
    }

    /**
     * 用于内部属性动画调用，应用无需调用
     */
    public float getStartAngle() {
        return mStartAngle;

    }

    /**
     * 用于内部属性动画调用，应用无需调用
     */
    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LoadingView.class.getName());
    }
}
