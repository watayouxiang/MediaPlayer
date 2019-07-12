package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watayouxiang.mediaplayer.R;

import java.util.ArrayList;
import java.util.List;

class LogDialog {
    private AlertDialog mAlertDialog;
    private ListAdapter mListAdapter;

    LogDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //初始化布局
        View root = LayoutInflater.from(context).inflate(R.layout.logdialog_root, null);
        RecyclerView rvList = root.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new GridLayoutManager(context, 1,
                RecyclerView.VERTICAL, false));
        rvList.setAdapter(mListAdapter = new ListAdapter());
        //初始化dialog
        builder.setTitle("播放器日志");
        builder.setView(root);
        builder.setPositiveButton("确定", null);
        mAlertDialog = builder.create();
    }

    /**
     * 显示dialog
     */
    void show() {
        mAlertDialog.show();
    }

    /**
     * 添加数据
     *
     * @param txt 字符串
     */
    void addData(String txt) {
        mListAdapter.addData(txt);
    }

    /**
     * 释放资源
     */
    void releaseRes() {
        if (mListAdapter != null) {
            mListAdapter.clearData();
            mListAdapter = null;
        }
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
        private final List<String> mData = new ArrayList<>();

        void addData(String txt) {
            if (txt != null) {
                mData.add(txt);
                notifyDataSetChanged();
            }
        }

        void clearData() {
            mData.clear();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.logdialog_listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            holder.tv_txt.setText(String.valueOf(mData.get(position)));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        private class ListHolder extends RecyclerView.ViewHolder {
            private final TextView tv_txt;

            ListHolder(View view) {
                super(view);
                tv_txt = view.findViewById(R.id.tv_txt);
            }
        }
    }
}
