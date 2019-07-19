package com.customview.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.customview.HomeActivity;
import com.customview.R;
import com.hp.hpl.sparta.xpath.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

//  http://www.runoob.com/w3cnote/android-tutorial-notification.html

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.notification_btn)
    Button notificationBtn;
    private boolean isOk = false;
    NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        notification();
        isOk = true;
    }

    private void notification() {

        if (isOk){
          manager.cancel(1);
        }else {
            //Step 1. 获得NotificationManager对象：
            //Step 2. 创建一个通知栏的Builder构造类：
            Notification.Builder builder = new Notification.Builder(this);
            //Step 3. 对Builder进行相关的设置，比如标题，内容，图标，动作等！
            Intent intent = new Intent(this, HomeActivity.class);
            PendingIntent pit = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setSmallIcon(R.drawable.icon_home)
                    .setContentTitle("通知")
                    .setContentText("该睡觉了亲")
                    .setContentIntent(pit);
            //Step 4.调用Builder的build()方法为notification赋值
            Notification notification = builder.build();
            //Step 5.调用NotificationManager的notify()方法发送通知！
            manager.notify(1, notification);
            //另外我们还可以调用NotificationManager的cancel()方法取消通知
        }
    }
}
