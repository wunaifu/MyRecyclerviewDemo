package com.wnf.recyclerviewdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wnf.recyclerviewdemo.MyRecyclerview.MyBaseAdapter;
import com.wnf.recyclerviewdemo.R;
import com.yanxuwen.swipelibrary.SwipeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：严旭文 on 2016/5/11 14:36
 * 邮箱：420255048@qq.com
 */
public class MyExpandChildAdapter extends MyBaseAdapter {
    private List<String> mDataSet;
    private Context mContext;
    boolean isStaggered=false;

    public MyExpandChildAdapter(Context context, List<String> dataSet) {
        super(context, dataSet);
        this.mDataSet = dataSet;
        this.mContext = context;
    }
    public MyExpandChildAdapter(Context context, List<String> dataSet, boolean isStaggered) {
        super(context, dataSet);
        this.mDataSet = dataSet;
        this.mContext = context;
        this.isStaggered = isStaggered;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        addSwipe(R.layout.swipe_default2, SwipeLayout.ShowMode.LayDown, SwipeLayout.DragEdge.Right, true);
        //addExpand(R.layout.expand_default);
        return new ViewHolder(setLayout(R.layout.item_expand, parent));
    }

    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final ViewHolder mViewHolder = (ViewHolder) holder;
        int adpterposition = holder.getCurPosition();
        //设置控件信息
        mViewHolder.text.setText(mDataSet.get(position));
        mViewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击"+mDataSet.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        super.onBindViewHolder(holder, position);
    }

    class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ViewHolder mViewHolder;
        //绑定控件
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.v_expand)
        View v_expand;
        @Bind(R.id.swipe_delete)
        View swipeDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mViewHolder = this;
            swipeDelete.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.swipe_delete:
                    remove(mViewHolder.getCurPosition());
                    break;

            }
        }
    }
}
