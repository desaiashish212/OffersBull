package com.offersbull.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nineoldandroids.view.ViewHelper;
import com.offersbull.R;
import com.offersbull.adapter.RealEstateAdapter;
import com.offersbull.model.RealState;
import com.offersbull.model.RealStateCard;
import com.offersbull.model.ResponsReal;
import com.offersbull.model.Response;
import com.offersbull.model.ResponseRealSingle;
import com.offersbull.network.NetworkUtil;
import com.offersbull.utils.Constants;
import com.offersbull.view.ExpandableTextView;
import com.offersbull.view.ObservableScrollView;
import com.offersbull.view.ObservableScrollViewCallbacks;
import com.offersbull.view.ScrollState;
import com.offersbull.view.ScrollUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SingleRealEstateActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    @Nullable
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Nullable
    @BindView(R.id.scroll) ObservableScrollView mScrollView;
    @Nullable
    @BindView(R.id.image) ImageView imageView;
    @Nullable
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @Nullable
    @BindView(R.id.linear_containt) LinearLayout linearContaent;
    @Nullable
    @BindView(R.id.error) LinearLayout linearError;
    @Nullable
    @BindView(R.id.tvPost_title) TextView tv_title;
    @Nullable
    @BindView(R.id.tvPost_owner) TextView tv_name;
    @Nullable
    @BindView(R.id.tvCity) TextView tv_city;
    @Nullable
    @BindView(R.id.tv_price) TextView tv_price;
    @Nullable
    @BindView(R.id.tv_buildUp) TextView tv_buildUp;
    @Nullable
    @BindView(R.id.tv_date) TextView tv_date;
    @Nullable
    @BindView(R.id.tv_facility)ExpandableTextView tv_facility;
    @Nullable
    @BindView(R.id.tv_type) TextView tv_type;
    @Nullable
    @BindView(R.id.tv_discrip) ExpandableTextView tv_discrip;
    @Nullable
    @BindView(R.id.linear_facility) LinearLayout linear_facility;
    @Nullable
    @BindView(R.id.linear_discription) LinearLayout linear_discription;
    @Nullable
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private CompositeSubscription mSubscriptions;
    private View mToolbarView;
    private View mImageView;
    private int mParallaxImageHeight,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_real_estate);
        ButterKnife.bind(this);
        id = getIntent().getExtras().getInt("id");
        Log.d("id",""+id);
        setObservableScrollView();
        mSubscriptions = new CompositeSubscription();
        loadRealEstateById();
    }

    public void setObservableScrollView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarView = toolbar;
        mImageView = imageView;
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }


    private void loadRealEstateById() {
        Log.d("Load","Load realestate");
        progressBar.setVisibility(View.VISIBLE);
        linearContaent.setVisibility(View.GONE);
        linearError.setVisibility(View.GONE);
        mSubscriptions.add(NetworkUtil.getRetrofit().getRealEstateById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void setData(RealState realState, ArrayList<RealStateCard> realStateCardArrayList){
        Picasso.with(this)
                .load("http://offersbull.com/uploads/real/186/2017-01-18_17-26-20_0.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        tv_title.setText(realState.getTitle());
        tv_city.setText(realState.getArea()+","+realState.getCity());
        tv_name.setText("By "+realState.getName());
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(realState.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        tv_date.setText(timeAgo);
        tv_price.setText("RS. "+realState.getPrice()+"/-");
        tv_buildUp.setText(realState.getBuiltup()+" Sq.ft");
        tv_type.setText(realState.getType());
        if (!realState.getAmenities().isEmpty())
            tv_facility.setText(realState.getAmenities());
        else linear_facility.setVisibility(View.GONE);

        if (!realState.getDescription().isEmpty())
            tv_discrip.setText(realState.getDescription());
        else linear_discription.setVisibility(View.GONE);


        RealEstateAdapter realEstateAdapter = new RealEstateAdapter(this,realStateCardArrayList, Constants.SIMILAR);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(realEstateAdapter);

    }

    private void handleResponse(ResponseRealSingle response) {
        Log.d("Respones",response.getData().toString());
        progressBar.setVisibility(View.GONE);
        linearError.setVisibility(View.GONE);
        linearContaent.setVisibility(View.VISIBLE);
        //mainLayout.setVisibility(View.VISIBLE);
        setData(response.getData().get(0),response.getSimiller());
    }

    private void handleError(Throwable error) {
        Log.d("Error",error.toString());
        progressBar.setVisibility(View.GONE);
        linearContaent.setVisibility(View.GONE);
        linearError.setVisibility(View.VISIBLE);

        //errorLayout.setVisibility(View.VISIBLE);

        if (error instanceof HttpException) {
            System.out.print("if:");
            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Erorr",error.toString());
            showSnackBarMessage("Network Error !");
        }
    }


    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.main_single_real),message,Snackbar.LENGTH_SHORT).show();

    }

    @Optional
    @OnClick(R.id.btn_retry)
    public  void retry(){
        loadRealEstateById();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public static long getLongDate(String dateString){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdf.parse(dateString);

            return  date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
