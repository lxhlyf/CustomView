package com.customview.jsHtml.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.audioplayer.jshtml.AnalysisHTML;
import com.customview.R;
import com.customview.jsHtml.DetailEntity;
import com.customview.jsHtml.html.HtmlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 简言 on 2019/2/18  13:33.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.jsHtml
 * Description :
 */
public class HtmlAndJsActivity extends AppCompatActivity {

    @BindView(R.id.news_parse_web)
    LinearLayout newsParseWeb;
    @BindView(R.id.webView)
    WebView webView;

    private int position = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);

        DetailEntity detailEntity = new DetailEntity();
        if (position == 1) {

            newsParseWeb.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);

            int i = detailEntity.getLead().trim().length();
            AnalysisHTML analysisHTML = new AnalysisHTML();
            analysisHTML.loadHtml(this, detailEntity.getHtml5(), analysisHTML.HTML_URL, newsParseWeb, i);
        } else {

            newsParseWeb.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);

            //1.直接加载 原始的 html 的 url

            //2.拼接参数去掉 video
            //webView.loadUrl(HtmlUtil.getInstance(this, webView).initWebViewSetting().addParams2WezeitUrl(detailEntity.getHtml5(), true));

            //3.通过拼接 成 html 文本 然后用 webView加载
            HtmlUtil.getInstance(this, webView).initWebViewSetting().setDetail(detailEntity.getContent(), new HtmlUtil.LoadWebListener() {
                @Override
                public void onLoadFinished() {

                    //文本加载完成之后，会回调到这里
                    Toast.makeText(HtmlAndJsActivity.this, "文本加载完毕了", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
