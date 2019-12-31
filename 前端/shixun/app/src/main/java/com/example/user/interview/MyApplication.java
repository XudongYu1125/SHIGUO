package com.example.user.interview;

import android.app.Application;

import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.common.QueuedWork;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //MobTach初始化
        MobSDK.init(this);
        //友盟初始化
        UMConfigure.init(this, "5ddcdf5c0cafb286a0000b26", "Shutang", UMConfigure.DEVICE_TYPE_PHONE, "");//最后一个参数为推送时需要用到的
        UMShareAPI.get(this);
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        //三方获取用户资料时是否每次都要进行授权
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        PlatformConfig.setQQZone("101835214", "f7ec3f2bf6ab20f452430160d247708a");
        //
    }
}
