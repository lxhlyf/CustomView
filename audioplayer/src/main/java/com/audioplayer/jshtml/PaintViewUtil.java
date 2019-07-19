package com.audioplayer.jshtml;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.audioplayer.R;
import com.audioplayer.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/10
 * owspace
 */
public class PaintViewUtil {
    private final String LINE_H3 = "LINE_H3";
    private final String LINE_H4 = "LINE_H4";
    private final String LINE_HR = "LINE_HR";
    private SelectTextView blockTv;
    private int imgH;
    private int imgW;
    private LinearLayout.LayoutParams lParam;
    private View lineView;
    private SelectTextView ntv;
    private SelectTextView poetryTv;
    private Typeface typeFace;

    public PaintViewUtil(Context context) {
        typeFace =Typeface.createFromAsset(context.getAssets(),"fonts/PMingLiU.ttf");
    }

    private void addBlock(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2)
    {
        paintBlockLine(paramContext, paramViewGroup, true);
        paintBlockTextView(paramContext, paramViewGroup, paramSpannableStringBuilder, paramInt1, paramInt2);
        paintBlockLine(paramContext, paramViewGroup, false);
    }

    private void addH3TextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        addLines(paramContext, paramViewGroup, "LINE_H3");
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setTextIsSelectable(true);
        setFont(ntv);
        paramViewGroup.addView(this.ntv);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
    }

    private void addH4TextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        addLines(paramContext, paramViewGroup, "LINE_H4");
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.black));
        setFont(ntv);
        paramViewGroup.addView(this.ntv);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
    }

    private void addH5TextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.orange));
        ntv.setTextSize(10);
        setFont(ntv);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
        paramViewGroup.addView(this.ntv);
    }
    private void addH6TextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.black));
        ntv.setTextSize(8);
        ntv.setLineSpacing(1.5f,1.8f);
        setFont(ntv);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
        paramViewGroup.addView(this.ntv);
    }

    public static final String IMG_URLS = "mImageUrls";
    public static final String POSITION = "position";

    private List<String> stringList = new ArrayList<>();

    private void addImageView(final Activity paramActivity, SpannableStringBuilder paramSpannableStringBuilder, ViewGroup paramViewGroup, String imgWidth, String imgHeight, final String imgUrl)
    {
        stringList.add(imgUrl);
        ImageView localImageView = (ImageView)View.inflate(paramActivity, R.layout.item_image_view, null);
        this.lParam = getLinearParams();
        if ((imgWidth != null) && (imgHeight != null) && (!imgWidth.isEmpty()) && (!imgHeight.isEmpty()))
        {
            String str3 = imgWidth.replace("px", "");
            String str4 = imgHeight.replace("px", "");
            this.imgW = DensityUtil.dip2px(paramActivity, (float)Double.parseDouble(str3));
            this.imgH = DensityUtil.dip2px(paramActivity, (float)Double.parseDouble(str4));
            this.lParam.height = (DensityUtil.getWindowWidth(paramActivity) * this.imgH / this.imgW);
        }
        localImageView.setLayoutParams(this.lParam);
        paramViewGroup.addView(localImageView);
        Glide.with(paramActivity).load(imgUrl).into(localImageView);
        localImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.customview", "com.customview.jsHtml.ImageViewPagerActivity");
                intent.putExtra(POSITION, stringList.indexOf(imgUrl));
                intent.putStringArrayListExtra(IMG_URLS, (ArrayList<String>) stringList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                paramActivity.startActivity(intent);
            }
        });
    }

    private void addLines(Context paramContext, ViewGroup paramViewGroup, String paramString)
    {
        this.lineView = new View(paramContext);
        this.lParam = getLinearParams();
        paramViewGroup.addView(this.lineView);
    }

    private void addPoetry(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2)
    {
        this.poetryTv = new SelectTextView(paramContext);
        this.poetryTv.setSingleLine(false);
        this.poetryTv.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        poetryTv.setTextColor(paramContext.getResources().getColor(R.color.black));
        setFont(poetryTv);
        paramViewGroup.addView(this.poetryTv);
    }

    private void addTextSpanView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setLineSpacing(1.5f,1.8f);
        ntv.setTextSize(15);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.black));
        setFont(ntv);
        this.lParam = getLinearParams();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,14,0,0);
        ntv.setLayoutParams(layoutParams);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
        paramViewGroup.addView(this.ntv);
    }

    private void addTextSpanTitleView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setLineSpacing(2.2f,2.5f);
        ntv.setTextSize(20);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.black));
        setFont(ntv);
        this.lParam = getLinearParams();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,14,0,0);
        ntv.setLayoutParams(layoutParams);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
        paramViewGroup.addView(this.ntv);
    }

    private void addStrongTextSpanView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        this.ntv = new SelectTextView(paramContext);
        this.ntv.setSingleLine(false);
        ntv.setLineSpacing(1.5f,1.8f);
        ntv.setTextSize(15);
        ntv.setTextIsSelectable(true);
        ntv.setTextColor(paramContext.getResources().getColor(R.color.black));
        setFont(ntv);
        this.lParam = getLinearParams();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,14,0,0);
        ntv.setGravity(Gravity.LEFT);
        ntv.setLayoutParams(layoutParams);
        TextPaint tp = ntv .getPaint();
        tp.setFakeBoldText(true);
        putTextSpanViewSettings(this.ntv, paramSpannableStringBuilder, paramInt1, paramInt2, paramInt3);
        paramViewGroup.addView(this.ntv);
    }
    private void setFont(SelectTextView selectTextView){
        selectTextView.setTypeface(typeFace);
    }
    private int getColorById(Context paramContext, int paramInt)
    {
        return paramContext.getResources().getColor(paramInt);
    }

    private LinearLayout.LayoutParams getLinearParams()
    {
        this.lParam = new LinearLayout.LayoutParams(-1, -2);
        return this.lParam;
    }

    private void paintBlockLine(Context paramContext, ViewGroup paramViewGroup, boolean paramBoolean)
    {
        this.lineView = new View(paramContext);
        this.lParam = getLinearParams();
        this.lineView.setLayoutParams(this.lParam);
        paramViewGroup.addView(this.lineView);
    }

    private void paintBlockTextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2)
    {
        this.blockTv = new SelectTextView(paramContext);
        this.lParam = getLinearParams();
        this.blockTv.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        int i = 0;
        int j = 0;
        this.lParam.setMargins(i, j, i, j);
        this.blockTv.setLayoutParams(this.lParam);
        paramViewGroup.addView(this.blockTv);
    }

    private void putTextSpanViewSettings(TextView paramTextView, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3)
    {
        paramTextView.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        paramTextView.setGravity(Gravity.LEFT);
    }

    public void addTypeView(Activity paramActivity, ViewGroup paramViewGroup, int type, SpannableStringBuilder paramSpannableStringBuilder, String imgWidth, String imgHeight, String imgUrl, int wordsLength, int spaces)
    {
//        Logger.i("paramInt1="+type);
        if ((paramSpannableStringBuilder != null) && (paramSpannableStringBuilder.length() > 0))
        {
            switch (type){
                case 0:  //<p></p>  中间没有 <h></h>
                    addTextSpanView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
                case 1:
                case 2:
                   addTextSpanTitleView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                   break;
                case 3:
                    addH3TextView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
                case 4:
                    addH4TextView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
                case 5:
                    addH5TextView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
                case 6:
                    addH6TextView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
                case 7:
                    addBlock(paramActivity, paramViewGroup, paramSpannableStringBuilder, wordsLength, spaces);
                    break;
                case 8:
                    addPoetry(paramActivity, paramViewGroup, paramSpannableStringBuilder, wordsLength, spaces);
                    break;
                case 9:
                    addImageView(paramActivity, paramSpannableStringBuilder, paramViewGroup, imgWidth,imgHeight, imgUrl);
                    break;
                case 10:
                    addLines(paramActivity, paramViewGroup, "LINE_HR");
                    break;
                case 11:
                    addStrongTextSpanView(paramActivity, paramViewGroup, paramSpannableStringBuilder, type, wordsLength, spaces);
                    break;
            }

        }
    }
}
