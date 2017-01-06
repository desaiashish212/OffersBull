package com.offersbull.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.offersbull.R;
import com.offersbull.appintro.IntroActivity;

public class SplashActivity extends AppCompatActivity implements ISplashView{
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashPresenter(this);
        showSplash();
    }

    public void showSplash(){
        splashPresenter.startSplash();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showNoInetErrorMsg() {
        Log.d("Error","No internet Connection");
    }

    @Override
    public void moveToMainView() {

        startActivity(new Intent(this,IntroActivity.class));
        finish();
    }
}
