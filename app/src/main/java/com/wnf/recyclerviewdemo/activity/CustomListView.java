package com.wnf.recyclerviewdemo.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wnf.recyclerviewdemo.MyRecyclerview.MyArrowRefreshHeader;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyBaseAdapter;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyRecyclerView;
import com.wnf.recyclerviewdemo.MyRecyclerview.ProgressStyle;
import com.wnf.recyclerviewdemo.R;
import com.wnf.recyclerviewdemo.adapter.MyChildAdapter;

import java.util.ArrayList;

public class CustomListView extends AppCompatActivity {

    private MyRecyclerView mRecyclerView;
    private MyChildAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        //////////////////////////////////开始处理列表//////////////////////////////////////////////////////////
        mRecyclerView = (MyRecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setArrowRefreshHeader(new MyArrowRefreshHeader(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setItemAnimator(MyRecyclerView.ItemType.FadeInLeft);
        //只有调用setPullRefreshEnabled才会打开下来刷新
        mRecyclerView.setPullRefreshEnabled(true);
        //只有调用setLoadingMoreEnabled才会打开上啦加载
        mRecyclerView.setLoadingMoreEnabled(true);
        View head_view=   LayoutInflater.from(this).inflate(R.layout.recyclerview_header,null);
        mRecyclerView.addHeaderView(head_view);
        head_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomListView.this,"这是头部视图",Toast.LENGTH_SHORT).show();
                Log.e("xxx", "头部");
            }
        });
        mRecyclerView.setLoadingListener(new MyRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                load();
            }
        });

        listData = new  ArrayList<String>();
        mAdapter = new MyChildAdapter(this,listData);
        mRecyclerView.setAdapter(mAdapter);
        //不同于谷歌的SwipeRefreshLayout，SwipeRefreshLayout.setRefreshing(true);只是单纯的打开加载样式，但不会调用onRefresh方法
        //而这里会自动调用
        mRecyclerView.setRefreshing(true);
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyBaseAdapter.BaseViewHolder holder,View view, int position) {
                //holder.expand();
                Toast.makeText(CustomListView.this,"单击"+position,Toast.LENGTH_SHORT).show();
                Log.e("xxx", position + "");
            }
        });
        mAdapter.setOnItemLongClickListener(new MyBaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(MyBaseAdapter.BaseViewHolder holder,View view, int position) {
                Toast.makeText(CustomListView.this,"长按"+position,Toast.LENGTH_SHORT).show();
//                if(position==1){
//                    holder.expand();
//                }
                Log.e("xxx", position + "long");
            }
        });
    }

    public void refresh(){
        refreshTime ++;
        times = 1;
        new Handler().postDelayed(new Runnable(){
            public void run() {

                listData.clear();
                for(int i = 0; i < 10 ;i++){
                    listData.add("item" + i );
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

        }, 1000);            //refresh data here
    }
    public void load(){
        if(times < 2){
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    mRecyclerView.loadMoreComplete();
                    for(int i = 10; i < 20 ;i++){
                        listData.add("item" + i );
                    }
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    mRecyclerView.noMoreLoading();
                }
            }, 1000);
        }
        times ++;
    }

}
