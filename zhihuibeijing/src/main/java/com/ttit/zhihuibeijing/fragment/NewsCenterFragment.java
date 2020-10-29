package com.ttit.zhihuibeijing.fragment;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.activity.HomeActivity;
import com.ttit.zhihuibeijing.adapter.NewsCenterGroupImageViewAdapter;
import com.ttit.zhihuibeijing.adapter.NewsCenterTabVPAdapter;
import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.base.BaseLoadNetDataOperator;
import com.ttit.zhihuibeijing.base.NewsCenterContentTabPager;
import com.ttit.zhihuibeijing.bean.NewsCenterBean;
import com.ttit.zhihuibeijing.bean.NewsCenterGroupImageViewBean;
import com.ttit.zhihuibeijing.utils.bitmap.CacheUtils;
import com.ttit.zhihuibeijing.utils.Constant;
import com.ttit.zhihuibeijing.utils.MyLogger;
import com.ttit.zhihuibeijing.utils.MyToast;
import com.ttit.zhihuibeijing.view.RecyclerViewDivider;
import com.viewpagerindicator.TabPageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by JW.S on 2020/9/26 1:17 PM.
 */
public class NewsCenterFragment extends BaseFragment implements BaseLoadNetDataOperator {
    private NewsCenterBean newsCenterBean;


    private static final String TAG = "NewsCenterTableFragment";
    private TabPageIndicator mTid;
    private ImageButton mIb;
    private ViewPager mVp;
    private List<NewsCenterContentTabPager> views;
    private Map<Integer, View> cacheViews = new HashMap<>();
    private RecyclerView mRvGroupImageView;
    private LinearLayoutManager llm;
    private GridLayoutManager glm;

    @Override
    public void initTitle() {
        setIbMenuDisplayState(true);
        setIbPicTypeDisplayState(true);
        setTitle("新闻中心");
    }

    @Override
    public View createContent() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.newscenter_content, (ViewGroup) getView(), false);
        mTid = view.findViewById(R.id.tabpagerindicator);
        mIb = view.findViewById(R.id.ib_next);
        mVp = view.findViewById(R.id.vp_newscenter_content);
        //点击箭头，切换到下一页
        mIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取ViewPager当前显示页面的下标
                int currentItem = mVp.getCurrentItem();

                if (currentItem != newsCenterBean.data.get(0).children.size() - 1) {
                    mVp.setCurrentItem(currentItem + 1);
                }
            }
        });
        //初始化ViewPager
        initViewPager();
        return view;
    }

    private void initViewPager() {
        //保存ViewPager显示每个条目的集合
        views = new ArrayList<>();
        //获取每个item的数据
        for (NewsCenterBean.NewsCenterNewsTabBean tabBean : newsCenterBean.data.get(0).children) {
            NewsCenterContentTabPager tabPager = new NewsCenterContentTabPager(getContext());
            views.add(tabPager);
        }
        //创建适配器
        NewsCenterTabVPAdapter adapter = new NewsCenterTabVPAdapter(views, newsCenterBean.data.get(0).children);
        //设置适配器
        mVp.setAdapter(adapter);
        //让TabPagerIndicator和ViewPager进行联合
        mTid.setViewPager(mVp);

        //让新闻中心第一个子tab的轮播图开始切换
        views.get(0).startSwitch();
        //给ViewPager设置页面切换监听
        //注意：ViewPager和TabPagerIndicator配合使用，监听只能设置给TabPagerIndicator
        mTid.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                //当前的开始切换，其他的tab停止切换
                for (int i = 0; i < views.size(); i++) {
                    NewsCenterContentTabPager tabPager = views.get(i);
                    if (position == i) {
                        //选中页
                        tabPager.startSwitch();
                    } else {
                        //未选中页
                        tabPager.stopSwitch();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void loadNetData() {
        final String newscenterUrl = Constant.NEWSCENTER_URL;
        OkHttpUtils.get()
                .url(newscenterUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyLogger.i(TAG, e.getMessage());
                        //吐司
                        MyToast.show(getActivity(), "");
                        try {
                            String json = CacheUtils.readCache(getContext(), newscenterUrl);
                            if (!TextUtils.isEmpty(json)) {
                                processData(json);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLogger.i(TAG, response);
                        //把response  == json 转换成对应的数据模型
                        processData(response);
                        //缓存数据
                        try {
                            CacheUtils.saveCache(getContext(), newscenterUrl, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //把json格式的字符串转换成对应的模型对象
    private void processData(String response) {
        hasLoadData = true;//已经加载数据
        Gson gson = new Gson();
        newsCenterBean = gson.fromJson(response, NewsCenterBean.class);
        ((HomeActivity) getActivity()).setNewsCenterMenuBeanList(newsCenterBean.data);
        //创建布局
        View view = createContent();
        //加载布局
        addView(view);
        //把布局添加在缓存的容器里
        cacheViews.put(0,view);
    }

    //切换内容
    public void switchContent(int position) {
        //标题右边的切换menu
        if (position == 2) {
            //显示
            mIbPicType.setVisibility(View.VISIBLE);
        } else {
            //隐藏
            mIbPicType.setVisibility(View.GONE);
        }
        //先从缓存的容器里面去获取
        View view = cacheViews.get(position);
        if (view == null) {
            //创建里面的内容
            mContainer.removeAllViews();
            if (position == 2) {
                //组图
                //初始化组图布局
                view = createGroupImageViewlayout();
                //将组图布局添加到新闻中心页中的FramenLayout容器中
                addView(view);
                //放入缓存中，如果缓存中有数据，直接取出
                cacheViews.put(position, view);
                //加载组图数据
                loadGroupImageViewData(position);
            }
        } else if (view != null) {
            //添加布局
            addView(view);
        }
    }

    //加载组图数据
    private void loadGroupImageViewData(int position) {
        //获取数据路径
        final String url = Constant.HOST + newsCenterBean.data.get(position).url;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "获取组图失败");
                        //读取缓存
                        try {
                            String json = CacheUtils.readCache(getContext(), url);
                            if (!TextUtils.isEmpty(json)) {
                                processGroupImageViewData(json);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLogger.i(TAG, "respose=" + response);
                        processGroupImageViewData(response);
                        //缓存数据
                        try {
                            CacheUtils.saveCache(getContext(), url, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //处理组图数据
    public void processGroupImageViewData(String json) {
        Gson gson = new Gson();
        //转化为数据模型
        NewsCenterGroupImageViewBean newsCenterGroupImageViewBean = gson.fromJson(json, NewsCenterGroupImageViewBean.class);
        //绑定适配器给rvGroupImageView
        mRvGroupImageView.setAdapter(new NewsCenterGroupImageViewAdapter(newsCenterGroupImageViewBean.data.news, getContext()));
    }

    //初始化组图布局
    private View createGroupImageViewlayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.newscenter_group_imageview, (ViewGroup) getActivity().getWindow().getDecorView(), false);
        mRvGroupImageView = view.findViewById(R.id.rv_group_imageview);
        //有的时候是列表，有的时候是网格
        //线性布局管理器
        llm = new LinearLayoutManager(getContext());
        //网格布局管理器
        glm = new GridLayoutManager(getContext(), 2);
        mRvGroupImageView.setLayoutManager(llm);
        //添加分割线
        mRvGroupImageView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, Color.BLACK));
        return view;
    }

    //组图的显示状态
    private int groupImageViewState = LIST_STATE;
    private final static int LIST_STATE = 0;
    private final static int GRID_STATE = 1;

    @OnClick(R.id.ib_pic_type)
    public void switchListGridState(View view) {
        //如果是列表，切换至网格
        if (groupImageViewState == LIST_STATE) {
            groupImageViewState = GRID_STATE;
            mIbPicType.setBackgroundResource(R.drawable.icon_pic_list_type);
            mRvGroupImageView.setLayoutManager(llm);
            //添加垂直方向的分割线
            mRvGroupImageView.addItemDecoration(new RecyclerViewDivider(getContext(), GridLayoutManager.VERTICAL, 1, Color.BLACK));
        } else {
            //如果是网络，切换至列表
            groupImageViewState = LIST_STATE;
            mIbPicType.setBackgroundResource(R.drawable.icon_pic_grid_type);
            mRvGroupImageView.setLayoutManager(glm);
            //添加水平方向的分割线
            mRvGroupImageView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, Color.BLACK));
        }
    }
}
