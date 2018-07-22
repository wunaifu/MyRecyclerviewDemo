package com.wnf.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnf.recyclerviewdemo.MyRecyclerview.ArrowRefreshHeader;
import com.wnf.recyclerviewdemo.MyRecyclerview.DensityUtil;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyBaseAdapter;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyRecyclerView;
import com.wnf.recyclerviewdemo.MyRecyclerview.ProgressStyle;
import com.wnf.recyclerviewdemo.R;
import com.wnf.recyclerviewdemo.activity.ListData;
import com.yanxuwen.swipelibrary.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：严旭文 on 2016/5/11 14:36
 * 邮箱：420255048@qq.com
 */
public class ExpandFatherAdapter extends MyBaseAdapter {
    private List<ListData> mDataSet;
    private Context mContext;
    boolean isStaggered=false;

    private MyExpandChildAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;

    public ExpandFatherAdapter(Context context, List<ListData> dataSet) {
        super(context, dataSet);
        this.mDataSet = dataSet;
        this.mContext = context;
    }
    public ExpandFatherAdapter(Context context, List<ListData> dataSet, boolean isStaggered) {
        super(context, dataSet);
        this.mDataSet = dataSet;
        this.mContext = context;
        this.isStaggered = isStaggered;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //添加滑动
        addSwipe(R.layout.swipe_default2, SwipeLayout.ShowMode.LayDown, SwipeLayout.DragEdge.Right, true);
        //添加展开
        addExpand(R.layout.expand_listview);
        return new ViewHolder(setLayout(R.layout.item, parent));
    }

    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final ViewHolder mViewHolder = (ViewHolder) holder;
        int adpterposition = holder.getCurPosition();
        mViewHolder.text.setText(mDataSet.get(position).getItem());
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        if (isStaggered && position % 2 == 0) {
//            lp.height = DensityUtil.dip2px(mContext,80);
//            lp.width = DensityUtil.dip2px(mContext,50);
//            lp.leftMargin= DensityUtil.dip2px(mContext,5);
//            lp.rightMargin=DensityUtil.dip2px(mContext,5);
//            lp.topMargin=DensityUtil.dip2px(mContext,5);
//            lp.bottomMargin=DensityUtil.dip2px(mContext,5);
//
//
//        }else{
//            lp.height = DensityUtil.dip2px(mContext,50);
//            lp.width = DensityUtil.dip2px(mContext,50);
//            lp.leftMargin= DensityUtil.dip2px(mContext,5);
//            lp.rightMargin=DensityUtil.dip2px(mContext,5);
//            lp.topMargin=DensityUtil.dip2px(mContext,5);
//            lp.bottomMargin=DensityUtil.dip2px(mContext,5);
//        }
//
//        mViewHolder.v_expand.setLayoutParams(lp);

        //////////////////////////////////开始处理展开的list列表//////////////////////////////////////////////////////////
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewHolder.mRecyclerView.setLayoutManager(layoutManager);
        mViewHolder.mRecyclerView.setArrowRefreshHeader(new ArrowRefreshHeader(mContext));

        mViewHolder.mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mViewHolder.mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mViewHolder.mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mViewHolder.mRecyclerView.setItemAnimator(MyRecyclerView.ItemType.FadeInLeft);
        //只有调用setPullRefreshEnabled才会打开下来刷新
        //mViewHolder.mRecyclerView.setPullRefreshEnabled(true);
        //只有调用setLoadingMoreEnabled才会打开上啦加载
        //mViewHolder.mRecyclerView.setLoadingMoreEnabled(true);
//        View head_view=   LayoutInflater.from(mContext).inflate(R.layout.recyclerview_header,null);
//        mViewHolder.mRecyclerView.addHeaderView(head_view);
//        head_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext,"这是头部视图",Toast.LENGTH_SHORT).show();
//                Log.e("xxx", "头部");
//            }
//        });


        mAdapter = new MyExpandChildAdapter(mContext,mDataSet.get(position).getList());
        mViewHolder.mRecyclerView.setAdapter(mAdapter);
        //不同于谷歌的SwipeRefreshLayout，SwipeRefreshLayout.setRefreshing(true);只是单纯的打开加载样式，但不会调用onRefresh方法
        //而这里会自动调用
        mViewHolder.mRecyclerView.setRefreshing(true);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder,View view, int position) {
                Toast.makeText(mContext,"单击"+mDataSet.get(position).getList().get(position),Toast.LENGTH_SHORT).show();

            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(BaseViewHolder holder,View view, int position) {
                Toast.makeText(mContext,"长按"+mDataSet.get(position).getList().get(position),Toast.LENGTH_SHORT).show();
            }
        });
//////////////////////////////////结束处理列表//////////////////////////////////////////////////////////

        mViewHolder.swipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataSet.get(position).getList().size() > 0) {
                    Toast.makeText(mContext, "存在子项" + position, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "删除父项未测试通过" + position, Toast.LENGTH_SHORT).show();
                    //remove(position);
                }
            }
        });
        super.onBindViewHolder(holder, position);

    }

    class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ViewHolder mViewHolder;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.v_expand)
        View v_expand;
        @Bind(R.id.swipe_delete)
        View swipeDelete;
        @Bind(R.id.recyclerview)
        MyRecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mViewHolder = this;
            //设置自动展开按钮，
//            setExpandView(v_expand);
            //swipeDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.swipe_delete:
//                    remove(mViewHolder.getCurPosition());
//                    break;

            }
        }
    }

//    public void add(String text, int position) {
//        mDataSet.add(position, text);
//        super.add(position);
//    }
}
