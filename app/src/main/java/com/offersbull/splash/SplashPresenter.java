package com.offersbull.splash;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.offersbull.utils.NetworkUtils;

/**
 * Created by AD on 1/5/2017.
 */

public class SplashPresenter implements IConnectionStatus {

    ISplashView splashView;
    Handler handler;
    NetworkUtils networkUtils;

    public SplashPresenter (Context context){

        this.splashView = (ISplashView)context;
        handler = new Handler(Looper.getMainLooper());
        networkUtils = new NetworkUtils(context);

    }

    @Override
    public boolean isOnline() {

        return networkUtils.isConnectingToInternet();
    }

    public void startSplash(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOnline()){
                    splashView.showNoInetErrorMsg();
                }else splashView.moveToMainView();
            }
        },3000);
    }
}
