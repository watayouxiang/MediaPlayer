package com.watayouxiang.mediaplayer.core;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import java.util.Locale;

/**
 * 注意事项一：
 * 为了避免Lifecycle中的生命周期方法混乱。
 * 当手机旋转时，必须禁止Activity重走生命周期方法。manifest的Activity节点配置：
 * android:configChanges="orientation|keyboard|locale|screenSize|layoutDirection"
 * <p>
 * 注意事项二：
 * 为了避免调用void setFullScreen(boolean fullScreen)时导致ActionBar样式异常。
 * Activity请使用不带ActionBar的主题。manifest的Activity节点配置：
 * android:theme="@style/Theme.AppCompat.Light.NoActionBar"
 */
class BaseView extends GestureView implements Lifecycle, BaseViewOperation {

    // ============================================================================
    // 无需释放的资源
    // ============================================================================
    private Activity mActivity;
    private NetworkState mNetworkState;//网络状态（只要NetworkHelper以创建，就有默认值，所以无需初始化值）

    // ============================================================================
    // 需要释放的资源
    // ============================================================================
    private NetworkHelper mNetworkHelper;
    private ViewSizeHelper mViewSizeHelper;
    private PhoneOrientationHelper mPhoneOrientationHelper;
    private ScreenOrientationHelper mScreenOrientationHelper;
    private BrightnessHelper mBrightnessHelper;
    private VolumeHelper mVolumeHelper;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInitEvent(Context context) {
        super.onInitEvent(context);
        mActivity = (Activity) context;
        mNetworkHelper = new NetworkHelper(context, new NetworkHelper.Listener() {
            @Override
            public void onNetworkChange(NetworkState networkState) {
                BaseView.this.onNetworkChange(networkState);
            }
        });
        mViewSizeHelper = new ViewSizeHelper(BaseView.this, new ViewSizeHelper.Listener() {
            @Override
            public void onViewSizeChange(int width, int height) {
                BaseView.this.onSizeChange(width, height);
            }
        });
        mPhoneOrientationHelper = new PhoneOrientationHelper(context, new PhoneOrientationHelper.Listener() {
            @Override
            public void onPhoneOrientationChange(Orientation orientation) {
                BaseView.this.onPhoneOrientationChange(orientation);
            }
        });
        mScreenOrientationHelper = new ScreenOrientationHelper();
        mBrightnessHelper = new BrightnessHelper(mActivity);
        mVolumeHelper = new VolumeHelper(context);
    }

    protected void onPhoneOrientationChange(Orientation orientation) {
        addLog(String.format("onPhoneOrientationChange: %s", orientation));
    }

    protected void onSizeChange(int width, int height) {
        addLog(String.format(Locale.getDefault(), "onSizeChange: W%d, H%d", width, height));
        addInfo(String.format(Locale.getDefault(), "onSizeChange: W%d, H%d", width, height));
    }

    protected void onNetworkChange(NetworkState networkState) {
        addLog(String.format("onNetworkChange: %s", networkState));
        addInfo(String.format("onNetworkChange: %s", networkState));
        mNetworkState = networkState;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNetworkHelper != null) {
            mNetworkHelper.releaseRes();
            mNetworkHelper = null;
        }
        if (mViewSizeHelper != null) {
            mViewSizeHelper.releaseRes();
            mViewSizeHelper = null;
        }
        if (mPhoneOrientationHelper != null) {
            mPhoneOrientationHelper.releaseRes();
            mPhoneOrientationHelper = null;
        }
        if (mScreenOrientationHelper != null) {
            mScreenOrientationHelper = null;
        }
        if (mBrightnessHelper != null) {
            mBrightnessHelper = null;
        }
        if (mVolumeHelper != null) {
            mVolumeHelper = null;
        }
    }

    @Override
    public NetworkState getNetworkState() {
        return mNetworkState;
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        if (mViewSizeHelper != null) {
            if (fullScreen) {
                mViewSizeHelper.setFullScreen();
            } else {
                mViewSizeHelper.setInitSize();
            }
        }
    }

    @Override
    public boolean isFullScreen() {
        if (mViewSizeHelper != null) {
            return mViewSizeHelper.isFullScreen();
        }
        return false;
    }

    @Override
    public void setScreenOrientation(Orientation orientation) {
        if (mScreenOrientationHelper != null) {
            mScreenOrientationHelper.setScreenOrientation(mActivity, orientation);
        }
    }

    @Override
    public Orientation getScreenOrientation() {
        if (mScreenOrientationHelper != null) {
            return mScreenOrientationHelper.getScreenOrientation();
        }
        return null;
    }

    @Override
    public void setBrightnessPercent(float percent) {
        if (mBrightnessHelper != null) {
            mBrightnessHelper.setBrightnessPercent(percent);
        }
    }

    @Override
    public float getBrightnessPercent() {
        if (mBrightnessHelper != null) {
            return mBrightnessHelper.getBrightnessPercent();
        }
        return 0;
    }

    @Override
    public float getVolumePercent() {
        if (mVolumeHelper != null) {
            return mVolumeHelper.getVolumePercent();
        }
        return 0;
    }

    @Override
    public void setVolumePercent(float percent) {
        if (mVolumeHelper != null) {
            mVolumeHelper.setVolumePercent(percent);
        }
    }
}
