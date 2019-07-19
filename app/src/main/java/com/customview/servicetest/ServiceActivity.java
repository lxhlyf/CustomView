package com.customview.servicetest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.customview.R;

public class ServiceActivity extends AppCompatActivity {

    Intent intent;
    private ServiceTest.MyBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        intent =  new Intent(this, ServiceTest.class);
    }


    /**
     *1. 在自定义的Service中继承Binder,实现自己的IBinder对象
     *2.通过onBind( )方法返回自己的IBinder对象
     *3.在绑定该Service的类中定义一个ServiceConnection对象,重写两个方法, onServiceConnected和onDisconnected！然后直接读取IBinder传递过来的参数即可!
     *4.只在绑定第一个客户端的时候，执行 onCreate()和onBind()方法,实例化Service, 之后绑定的客户端，会直接将IBinder对象传递给它
     *5.当用户调用unbindService()方法时，会调用onUnbind和onDestory方法，
     *
     * 6.当有多个客户端绑定服务的时候，只有当所有的客户端都解除了绑定的时候，才会销毁Service （除非service也被startService()方法开启）
     * 7.bindService模式下的Service是与调用者相互关联的,调用者被销毁，那么调用者调用Service执行的任务，会立即停止
     * 8.生命周期为：
     * 1)onCreat()---> onBind()-->onBind()-->onConnection()--->onDestroyed()
     * 2)
     * @param view
     */

    public void bindServiceClick(View view) {
        bindService(intent, con, Service.BIND_AUTO_CREATE);
    }

    /**
     * startService()开启Service
     * 1.onCreate( )->onStartCommand( )->onBind( )->onUnbind( )->onRebind( )
     *
     * 如果Service已经由某个客户端通过StartService()启动,
     * 接下来其他客户端调用bindService(）绑定该Service后又调用unbindService()解除绑定
     * 最后再调用bindService()绑定到Service的话,此时所触发的生命周期方法如下:
     * onCreate( )->onStartCommand( )->onBind( )->onUnbind( )->onRebind( )
     * PS:前提是:onUnbind()方法返回true!!!
     * 这里或许部分读者有疑惑了,调用了unbindService后Service不是应该调用 onDistory()方法么!
     * 其实这是因为这个Service是由我们的StartService来启动的 ,所以你调用onUnbind()方法取消绑定,Service也是不会终止的!
     * 得出的结论: 假如我们使用bindService来绑定一个启动的Service,
     * 注意是已经启动的Service!!!
     * 系统只是将Service的内部IBinder对象传递给Activity,并不会将Service的生命周期 与Activity绑定,
     * 因此调用unBindService( )方法取消绑定时,Service也不会被销毁！
     * @param view
     */
    public void startServiceClick(View view) {
        startService(intent);
    }

    ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("------Service Connected-------");
            binder = (ServiceTest.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("------Service Disconnected-------");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void unBindServiceClick(View view) {

        unbindService(con);
    }

    public void stopServiceClick(View view) {

        unbindService(con);
        stopService(intent);
    }
}
