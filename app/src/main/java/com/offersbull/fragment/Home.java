package com.offersbull.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.offersbull.R;
import com.offersbull.activity.AutomobileActivity;
import com.offersbull.activity.HotelRestroActivity;
import com.offersbull.activity.OfferActivity;
import com.offersbull.activity.OtherActivity;
import com.offersbull.activity.RealEstateActivity;
import com.offersbull.activity.TravellingActivity;
import com.offersbull.activity.TutionActivity;
import com.offersbull.adapter.SectionListDataAdapter;
import com.offersbull.model.Response;
import com.offersbull.network.NetworkUtil;
import com.offersbull.utils.Constants;
import com.offersbull.utils.NetworkStateReceiver;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by AD on 1/8/2017.
 */

public class Home extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener{
    View view;
    private SliderLayout mDemoSlider;
    private CompositeSubscription mSubscriptions;
    private ProgressBar progressBar;
    private RecyclerView latest_view_list;
    private RecyclerView more_view_list;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private LinearLayout realEstate;
    private LinearLayout tution;
    private LinearLayout hotel;
    private LinearLayout travelling;
    private LinearLayout automobile;
    private LinearLayout other;
    private Button btn_retry;
    private Button btn_lSeeAll;
    private Button btn_pSeeAll;
    private Button btn_mSeeAll;
    private CardView card_latest;
    private CardView card_popular;
    private CardView card_more;
    private NetworkStateReceiver networkStateReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view =  inflater.inflate(R.layout.fragment_home,null);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initListener();

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        loadHome();
        //mDemoSlider.addOnPageChangeListener(this);

    }

    private void init(){
        mSubscriptions = new CompositeSubscription();
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        latest_view_list = (RecyclerView) view.findViewById(R.id.latest_listview);
        more_view_list = (RecyclerView) view.findViewById(R.id.more_listview);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        errorLayout = (LinearLayout) view.findViewById(R.id.error);
        btn_retry = (Button) view.findViewById(R.id.btn_retry);
        realEstate = (LinearLayout) view.findViewById(R.id.real_estate);
        tution = (LinearLayout) view.findViewById(R.id.tution);
        hotel = (LinearLayout) view.findViewById(R.id.hotel);
        travelling = (LinearLayout) view.findViewById(R.id.travelling);
        automobile = (LinearLayout) view.findViewById(R.id.automobile);
        other = (LinearLayout) view.findViewById(R.id.other);
        btn_lSeeAll = (Button) view.findViewById(R.id.btn_lSeeAll);
        btn_pSeeAll = (Button) view.findViewById(R.id.btn_pSeeAll);
        btn_mSeeAll = (Button) view.findViewById(R.id.btn_mSeeAll);
        card_latest = (CardView) view.findViewById(R.id.card_latest);
        card_popular = (CardView) view.findViewById(R.id.card_popular);
        card_more = (CardView) view.findViewById(R.id.card_more);
      // networkStateReceiver = new NetworkStateReceiver(getActivity());
      //  networkStateReceiver.addListener(this);

    }

    private void initListener(){

        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                loadHome();
            }
        });

        realEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RealEstateActivity.class));
            }
        });

        tution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TutionActivity.class));
            }
        });


        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HotelRestroActivity.class));
            }
        });


        travelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TravellingActivity.class));
            }
        });

        automobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AutomobileActivity.class));
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OtherActivity.class));
            }
        });

        btn_lSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OfferActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });

        btn_pSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OfferActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });

        btn_mSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OfferActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNetworkAvailable() {
        showSnackBarMessage("Internet Connection Available");
    }

    @Override
    public void onNetworkUnavailable() {
        showSnackBarMessage("No Internet Connection");
    }

    @Override
    public void onPause() {
        super.onPause();
       // getActivity().unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
       // getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void loadHome() {
        System.out.print("Load:");
        progressBar.setVisibility(View.VISIBLE);
        mSubscriptions.add(NetworkUtil.getRetrofit().getHome()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        System.out.print("response:"+response.toString());

        if (!response.getLatest().isEmpty()){
            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(getActivity(), response.getLatest(), Constants.MAIN);
            latest_view_list.setHasFixedSize(true);
            latest_view_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
            latest_view_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            latest_view_list.setAdapter(itemListDataAdapter);
        }else {
            card_latest.setVisibility(View.GONE);
        }

        if (!response.getPolular().isEmpty()){

        }else {
            card_popular.setVisibility(View.GONE);
        }

        if (!response.getMore().isEmpty()){
            SectionListDataAdapter moreListAdapter = new SectionListDataAdapter(getActivity(), response.getMore(),Constants.MAIN);
            more_view_list.setHasFixedSize(true);
            more_view_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
            more_view_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            more_view_list.setAdapter(moreListAdapter);
        }else {
            card_more.setVisibility(View.GONE);
        }






    }

    private void handleError(Throwable error) {
        System.out.print("Error:");
        mainLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);

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

            showSnackBarMessage("Network Error !");
        }
    }


    private void showSnackBarMessage(String message) {

        Snackbar.make(view.findViewById(R.id.home),message,Snackbar.LENGTH_SHORT).show();

    }
}
