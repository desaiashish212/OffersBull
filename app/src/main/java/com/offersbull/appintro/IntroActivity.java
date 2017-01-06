package com.offersbull.appintro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.offersbull.R;
import com.offersbull.login.LoginActivity;
import com.offersbull.main.MainPageActivity;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(IntroSlide.newInstance(R.layout.intro));
        addSlide(IntroSlide.newInstance(R.layout.intro2));
        addSlide(IntroSlide.newInstance(R.layout.intro3));
        addSlide(IntroSlide.newInstance(R.layout.intro4));

        showStatusBar(true);
        setDepthAnimation();

    }



    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}