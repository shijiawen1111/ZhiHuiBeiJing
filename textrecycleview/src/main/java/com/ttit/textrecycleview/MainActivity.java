package com.ttit.textrecycleview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.rv)
    RecyclerView rv;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        //线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网格布局
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        //瀑布流布局
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        rv.setAdapter(new RecyclerViewAdapter());
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.setData(i);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        private Button mBtn;

        public MyViewHolder(View view) {
            super(view);
            mBtn = view.findViewById(R.id.btn);
        }

        public void setData(final int position) {
            mBtn.setText("第" + position + "条目的位置!");
            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了第" + (position + 1) + "个条目", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
