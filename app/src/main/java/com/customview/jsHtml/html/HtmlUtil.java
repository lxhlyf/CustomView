package com.customview.jsHtml.html;

import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.audioplayer.utils.DensityUtil;

import java.util.List;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class HtmlUtil {
    // css样式，隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

    // css style tag, 需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // js script tag, 需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    private static final String MIME_TYPE = "text/html; charset=utf-8";

    private static final String ENCODING = "utf-8";

    private String htmlHead = "<!DOCTYPE HTML html>\n" +
            "<head><meta charset=\"utf-8\"/>\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
            "</head>\n";

    private String htmlBody =  "<body>\n" +
            "<style> \n" +
            "img{width:100%!important;height:auto!important}\n" +
            " </style>";

    private String htmlEnd = "</body></html>";

    private static final String NICK = "lyf";

    private static HtmlUtil instance;

    private Context context;
    private WebView webView;

    private HtmlUtil(Context context, WebView webView) {

        this.context = context;
        this.webView = webView;
    }

    public HtmlUtil initWebViewSetting() {
        WebSettings localWebSettings = this.webView.getSettings();
        //提高优先级
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持缩放
        localWebSettings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
        return this;
    }

    public static HtmlUtil getInstance(Context context, WebView webView){
        if (instance == null){
            synchronized (HtmlUtil.class){
                if (instance == null){
                    instance = new HtmlUtil(context, webView);
                }
            }
        }
        return instance;
    }

    /**
     *   用WebView 加载html文件，并为他添加监听器和回调
     * @param htmlContent
     * @param listener
     */
    public void setDetail(String htmlContent, HtmlUtil.LoadWebListener listener) {
        mWebListener = listener;

        webView.addJavascriptInterface(new ShowPicRelation(context),NICK);//绑定JS和Java的联系类，以及使用到的昵称

        webView.loadDataWithBaseURL(null, getHtml(htmlContent), "text/html", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                addJs(view);
                if (mWebListener != null){
                    mWebListener.onLoadFinished();
                }
            }
        });
    }


    private LoadWebListener mWebListener;

    /**页面加载的回调*/
    public interface LoadWebListener{
        void onLoadFinished();
    }

    /**添加JS代码，获取所有图片的链接以及为图片设置点击事件*/
    private void addJs(WebView wv) {
        wv.loadUrl("javascript:(function  pic(){"+
                "var imgList = \"\";"+
                "var imgs = document.getElementsByTagName(\"img\");"+
                "for(var i=0;i<imgs.length;i++){"+
                "var img = imgs[i];"+
                "imgList = imgList + img.src +\";\";"+
                "img.onclick = function(){"+
                "window.lyf.openImg(this.src);"+
                "}"+
                "}"+
                "window.lyf.getImgArray(imgList);"+
                "})()");
    }

    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + DensityUtil.getDeviceId(context));
        localStringBuffer.append("&version=" + "1.3.0");
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
    }

    /**
     * 根据css链接生成Link标签
     * @param url String
     * @return String
     */
    public String createCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    /**
     * 根据多个css链接生成Link标签
     * @param urls List<String>
     * @return String
     */
    public String createCssTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据js链接生成Script标签
     *
     * @param url String
     * @return String
     */
    public String createJsTag(String url) {

        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }

    /**
     * 根据多个js链接生成Script标签
     *
     * @param urls List<String>
     * @return String
     */
    public String createJsTag(List<String> urls) {

        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据样式标签,html字符串,js标签
     * 生成完整的HTML文档
     */
    public String createHtmlData(String html, List<String> cssList, List<String> jsList) {
        final String css = createCssTag(cssList);
        final String js =  createJsTag(jsList);
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }

    public String getHtml(String content){

        return htmlHead + htmlBody + content + htmlEnd;
    }
}
