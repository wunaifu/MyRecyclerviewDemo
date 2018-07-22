package com.wnf.recyclerviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wnf.recyclerviewdemo.MyRecyclerview.ArrowRefreshHeader;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyBaseAdapter;
import com.wnf.recyclerviewdemo.MyRecyclerview.MyRecyclerView;
import com.wnf.recyclerviewdemo.MyRecyclerview.ProgressStyle;
import com.wnf.recyclerviewdemo.R;
import com.wnf.recyclerviewdemo.adapter.MyChildAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomListView.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomListView2.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomListView3.class);
                startActivity(intent);
            }
        });
    }

}
