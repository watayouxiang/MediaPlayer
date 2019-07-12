package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

class GestureView extends CoreView {
    private ScrollMode mScrollMode;
    private float mEndScrollPercent;

    public GestureView(Context context) {
        super(context);
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInitEvent(Context context) {
        super.onInitEvent(context);
        //设置手势监听
        final GestureDetector gestureDetector = new GestureDetector(context, new MyGestureListener());
        gestureDetector.setIsLongpressEnabled(false);//取消长按，不然会影响滑动
        //设置触摸监听
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mScrollMode == ScrollMode.VERTICAL_LEFT) {
                        onEndScroll(mScrollMode, mEndScrollPercent);
                        v.performClick();
                    } else if (mScrollMode == ScrollMode.VERTICAL_RIGHT) {
                        onEndScroll(mScrollMode, mEndScrollPercent);
                        v.performClick();
                    } else if (mScrollMode == ScrollMode.HORIZONTAL) {
                        onEndScroll(mScrollMode, mEndScrollPercent);
                        v.performClick();
                    }
                }
                //监听触摸事件
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    //==============================================================================================

    protected void onStartScroll(ScrollMode mode) {
        addLog(String.format("onStartScroll: mode=%s", mode));
    }

    protected void onScrolling(ScrollMode mode, float percent) {
        addLog(String.format(Locale.getDefault(), "onScrolling: mode=%s, percent=%f", mode, percent));
    }

    protected void onEndScroll(ScrollMode mode, float percent) {
        addLog(String.format(Locale.getDefault(), "onEndScroll: mode=%s, percent=%f", mode, percent));
    }

    protected void onSingleTap() {
        addLog("onSingleTap");
    }

    protected void onDoubleTap() {
        addLog("onDoubleTap");
    }

    //==============================================================================================

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private int _offsetX = 1;//横向偏移检测，让快进快退不那么敏感
        private boolean _startScroll;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //判断滚动类型
            if (mScrollMode == null) {
                _startScroll = true;
                if (Math.abs(distanceX) - Math.abs(distanceY) > _offsetX) {
                    mScrollMode = ScrollMode.HORIZONTAL;
                } else {
                    if (e1.getX() < getWidth() / 2) {
                        mScrollMode = ScrollMode.VERTICAL_LEFT;
                    } else {
                        mScrollMode = ScrollMode.VERTICAL_RIGHT;
                    }
                }
            }
            //区分滚动逻辑
            else if (mScrollMode == ScrollMode.VERTICAL_LEFT) {
                if (_startScroll) {
                    onStartScroll(mScrollMode);
                    _startScroll = false;
                } else {
                    float deltaY = e1.getY() - e2.getY();
                    float percentY = deltaY / ((float) getHeight());
                    onScrolling(mScrollMode, percentY);
                    mEndScrollPercent = percentY;
                }
            } else if (mScrollMode == ScrollMode.VERTICAL_RIGHT) {
                if (_startScroll) {
                    onStartScroll(mScrollMode);
                    _startScroll = false;
                } else {
                    float deltaY = e1.getY() - e2.getY();
                    float percentY = deltaY / ((float) getHeight());
                    onScrolling(mScrollMode, percentY);
                    mEndScrollPercent = percentY;
                }
            } else if (mScrollMode == ScrollMode.HORIZONTAL) {
                if (_startScroll) {
                    onStartScroll(mScrollMode);
                    _startScroll = false;
                } else {
                    float deltaX = e2.getX() - e1.getX();
                    float percentX = deltaX / ((float) getWidth());
                    onScrolling(mScrollMode, percentX);
                    mEndScrollPercent = percentX;
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            _startScroll = false;
            mScrollMode = null;
            mEndScrollPercent = 0f;
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            GestureView.this.onDoubleTap();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onSingleTap();
            return super.onSingleTapConfirmed(e);
        }
    }
}
