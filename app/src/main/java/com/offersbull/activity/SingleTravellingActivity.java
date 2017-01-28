package com.offersbull.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nineoldandroids.view.ViewHelper;
import com.offersbull.R;
import com.offersbull.adapter.HotelAdapter;
import com.offersbull.adapter.TravellingAdapter;
import com.offersbull.model.Hotel;
import com.offersbull.model.HotelCard;
import com.offersbull.model.Response;
import com.offersbull.model.ResponseHotelSingle;
import com.offersbull.model.ResponseTravalSingle;
import com.offersbull.model.Travelling;
import com.offersbull.model.TravellingCard;
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

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SingleTravellingActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private Toolbar toolbar;
    private ObservableScrollView mScrollView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private LinearLayout linearContaent;
    private LinearLayout linearError;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_city;
    private TextView tv_location;
    private TextView tv_Address;
    private TextView tv_date;
    private TextView tv_price;
    private ExpandableTextView tv_discrip;
    private LinearLayout linear_discription;
    private RecyclerView recyclerView;
    private CompositeSubscription mSubscriptions;
    private View mToolbarView;
    private View mImageView;
    private int mParallaxImageHeight,id;
    private Button btnRetry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travelling);
        init();
        initListener();
        id = getIntent().getExtras().getInt("id");
        Log.d("id",""+id);
        setObservableScrollView();
        mSubscriptions = new CompositeSubscription();
        loadTravellingById();
    }
    
    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mScrollView = (ObservableScrollView)findViewById(R.id.scroll);
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearContaent = (LinearLayout) findViewById(R.id.linear_containt);
        linearError = (LinearLayout) findViewById(R.id.error);
        tv_title = (TextView) findViewById(R.id.tvPost_title);
        tv_name = (TextView) findViewById(R.id.tvPost_owner);
        tv_city = (TextView) findViewById(R.id.tvCity);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_Address = (TextView) findViewById(R.id.tv_address);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_discrip = (ExpandableTextView) findViewById(R.id.tv_discrip);
        linear_discription = (LinearLayout) findViewById(R.id.linear_discription);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnRetry = (Button) findViewById(R.id.btn_retry);
    }

    public void initListener(){
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTravellingById();
            }
        });

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


    private void loadTravellingById() {
        Log.d("Load","Load travelling");
        progressBar.setVisibility(View.VISIBLE);
        linearContaent.setVisibility(View.GONE);
        linearError.setVisibility(View.GONE);
        mSubscriptions.add(NetworkUtil.getRetrofit().getTravellingById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void setData(Travelling travelling, ArrayList<TravellingCard> travellingCardArrayList){
        Picasso.with(this)
                .load(Constants.BASE_IMAGE_URL+travelling.getPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        tv_title.setText(travelling.getTitle());
        tv_city.setText(travelling.getCity());
        tv_name.setText("By "+travelling.getName());
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(travelling.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        tv_date.setText(timeAgo);
        tv_location.setText(travelling.getArea());
        tv_Address.setText(travelling.getAddress());
        tv_price.setText(travelling.getPrice());

        if (!travelling.getDescription().isEmpty())
            tv_discrip.setText(travelling.getDescription());
        else linear_discription.setVisibility(View.GONE);


        TravellingAdapter travellingAdapter = new TravellingAdapter(this,travellingCardArrayList, Constants.SIMILAR);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(travellingAdapter);

    }

    private void handleResponse(ResponseTravalSingle response) {
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

        Snackbar.make(findViewById(R.id.main_single_travel),message,Snackbar.LENGTH_SHORT).show();

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
