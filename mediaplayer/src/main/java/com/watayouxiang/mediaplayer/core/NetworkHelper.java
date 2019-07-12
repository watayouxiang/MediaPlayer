package com.watayouxiang.mediaplayer.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkHelper {
    private Context mContext;
    private NetBroadcastReceiver mBroadcastReceiver;

    NetworkHelper(Context context, Listener listener) {
        mContext = context;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(mBroadcastReceiver = new NetBroadcastReceiver(connectivityManager, listener), intentFilter);
        } catch (Exception e) {
        }
    }

    /**
     * 释放资源
     */
    void releaseRes() {
        try {
            if (mContext != null && mBroadcastReceiver != null) {
                mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
                mBroadcastReceiver = null;
                mContext = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 是否在用手机网络
     *
     * @return 手机网络状态
     */
    boolean isMobileNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * 是否有网
     *
     * @return 网络状态
     */
    boolean hasNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean wifiConnect = wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
        boolean mobileConnect = mobileNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
        return wifiConnect || mobileConnect;
    }

    //==============================================================================================

    private class NetBroadcastReceiver extends BroadcastReceiver {
        private final Listener mListener;
        private final ConnectivityManager mConnectivityManager;

        public NetBroadcastReceiver(ConnectivityManager connectivityManager, Listener listener) {
            mConnectivityManager = connectivityManager;
            mListener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo wifiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo.State wifiState = wifiNetworkInfo.getState();
            NetworkInfo.State mobileState = mobileNetworkInfo.getState();

            if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                mListener.onNetworkChange(NetworkState.Connected_Mobile);
            } else if (NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                mListener.onNetworkChange(NetworkState.Connected_Wifi);
            } else if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                mListener.onNetworkChange(NetworkState.Disconnected);
            }
        }
    }

    interface Listener {
        /**
         * 网络状态变化
         * 注意：由于网络切换有时也会断开网络。所以 NetworkState.Disconnected 这个状态不准确
         *
         * @param state Disconnected, Connected_Wifi, Connected_Mobile
         */
        void onNetworkChange(NetworkState state);
    }

}
