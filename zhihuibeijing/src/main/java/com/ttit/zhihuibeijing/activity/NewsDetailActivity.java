package com.ttit.zhihuibeijing.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.mob.MobSDK;
import com.ttit.zhihuibeijing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by JW.S on 2020/10/10 3:36 AM.
 */
public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ib_textsize)
    ImageButton ibTextsize;
    @BindView(R.id.ib_share)
    ImageButton ibShare;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.pb)
    ProgressBar pb;

    /**
     * webView中修改网页的字体大小步骤:
     * 1.初始化字体型号数组
     * 2.创建对话框,并弹出对话框
     * 3.选择字体型号,修改网页字体大小
     */
    private String[] types = new String[]{
            "超大字体号",
            "打字体号",
            "正常字体号",
            "小字体号",
            "超小字体号"
    };
    private WebSettings.TextSize[] textSize = new WebSettings.TextSize[]{
            WebSettings.TextSize.LARGEST,
            WebSettings.TextSize.LARGER,
            WebSettings.TextSize.NORMAL,
            WebSettings.TextSize.SMALLER,
            WebSettings.TextSize.SMALLEST
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        //让webView支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //设置webView的客户端
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb.setVisibility(View.GONE);
            }
        });
        //让webView去加载网页
        webView.loadUrl(url);
    }

    @OnClick({R.id.ib_back, R.id.ib_textsize, R.id.ib_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                changeWebViewTextSize();
                break;
            case R.id.ib_share:
                showShare();
                break;
        }
    }

    private int selectPositon;

    //修改网页字体大小
    private void changeWebViewTextSize() {
        //单选的对话框   AlertDialog
        //设置字体大小 webView.getSettings().setTextSize(TextS);
        new AlertDialog.Builder(this)
                .setTitle("选择字体的大小")
                .setSingleChoiceItems(types, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectPositon = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        webView.getSettings().setTextSize(textSize[selectPositon]);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showShare() {
        MobSDK.submitPolicyGrantResult(true, null);
        OnekeyShare oks = new OnekeyShare();
        oks.setTitle("来自JW.S的分亨!");
        oks.setTitleUrl("http://sharesdk.cn");
        oks.setText("我是中国人,我骄傲!");
        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
        oks.setUrl("http://sharesdk.cn");                           // url仅在微信（包括好友和朋友圈）中使用
        oks.setComment("我是测试评论文本");                            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setSite(getString(R.string.app_name));                   // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");                        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.show(this);                                      // 启动分享GUI
    }
}
