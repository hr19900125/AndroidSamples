package com.hr.toy.widget.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.toy.R;

/**
 * 加载动画的loadingView
 * 这里处理LoadingView不可见之后动画不停止导致占用CPU的问题
 */
public class LoadDataView extends FrameLayout {


    private FrameLayout mProgressContainer;
    private LoadingView mLoadingView;
    private TextView mLoadingTxt;

    private LinearLayout mEmptyView;
    private ImageView mEmptyImage;
    private TextView mEmptyTitle;

    private Handler mHandler;

    public LoadDataView(Context context) {
        super(context);
        initView(context);
    }

    public LoadDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.load_data_view, this);
        //loading view
        mProgressContainer = (FrameLayout) view.findViewById(R.id.progress_container);
        mLoadingView = (LoadingView) view.findViewById(R.id.mc_loading_view);
        mLoadingTxt = (TextView) view.findViewById(R.id.mc_loading_view_text);
        //empty view
        mEmptyView =  view.findViewById(R.id.empty_view);
        mEmptyImage  = view.findViewById(R.id.empty_image);
        mEmptyTitle = view.findViewById(R.id.empty_title);

        mHandler = new Handler(Looper.getMainLooper());
    }

    private Runnable mProgressShowRunnale = new Runnable() {
        @Override
        public void run() {
            showProgressNoDelay();
        }
    };

    public void showProgressNoDelay() {
        setVisibility(View.VISIBLE);
        mProgressContainer.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startAnimator();
    }

    /**
     * 显示加载动画,同时设置提示语
     *
     * @param progressText 提示语
     */
    public void showProgress(String progressText) {
        if (progressText != null) {
            mLoadingTxt.setText(progressText);
        }
        mHandler.removeCallbacks(mProgressShowRunnale);
        mHandler.postDelayed(mProgressShowRunnale, 500);
    }


    public void hideProgress() {
        mHandler.removeCallbacks(mProgressShowRunnale);
        mProgressContainer.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopAnimator();//隐藏LoadingView之后必须停止动画
    }

    public void hideEmptyView() {
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    public void setEmptyTitleColor(int color) {
        if(mEmptyTitle != null) {
            mEmptyTitle.setTextColor(color);
        }
    }

    /**
     *
     * @param title 要显示的文字
     * @param iconDrawable 要显示的图标，null时不显示图标
     * @param onRetryClickListener 点击事件,null时没有点击事件
     */
    public void showEmptyView(String title, Drawable iconDrawable, OnClickListener onRetryClickListener) {
        setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);
        if (title != null) {
            mEmptyTitle.setText(title);
        }
        if (iconDrawable == null) {
            mEmptyImage.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            mEmptyImage.setImageDrawable(iconDrawable);
        }
        if (onRetryClickListener == null) {
            mEmptyView.setOnClickListener(null);
        } else {
            mEmptyView.setOnClickListener(onRetryClickListener);
        }
//        mEmptyView.setAlpha(0f);
//        mEmptyView.animate().alpha(1f).setDuration(DURATION_ANIMATION).start();
//        mEmptyView.animate().start();
    }

    public TextView getTextView(){
        return mLoadingTxt;
    }

    public LoadingView getmLoadingView() {
        return mLoadingView;
    }
}
