package com.offersbull.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.offersbull.R;
import com.offersbull.adapter.TutionsAdapter;
import com.offersbull.model.Response;
import com.offersbull.model.ResponseTution;
import com.offersbull.model.TutionCard;
import com.offersbull.network.NetworkUtil;
import com.offersbull.utils.Constants;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TutionActivity extends AppCompatActivity implements Paginate.Callbacks{

    private CompositeSubscription mSubscriptions;
    private int page;
    private Paginate paginate;
    private TutionsAdapter tutionsAdapter;
    private ArrayList<TutionCard> tutionCardArrayList;
    private boolean loading = false;
    protected int totalPages = 10;

    
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayout errorLayout;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tution);
        init();
        setupPagination();
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        errorLayout = (LinearLayout) findViewById(R.id.error);
        retry = (Button) findViewById(R.id.btn_retry);
        mSubscriptions = new CompositeSubscription();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tutionCardArrayList = new ArrayList<TutionCard>();
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadMore();
                Log.d("On retry click","Retry");
            }
        });

    }

    public void setupPagination(){
        if (paginate != null) {
            paginate.unbind();
        }

        tutionsAdapter = new TutionsAdapter(this,tutionCardArrayList, Constants.MAIN);
        loading = false;
        page = 1;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(tutionsAdapter);

        paginate = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(4)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                .build();
    }

    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // Bind custom loading row if needed
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }
    }


    @Override
    public synchronized void onLoadMore() {
        Log.d("Paginate", "onLoadMore");
        loading = true;
        // Fake asynchronous loading that will generate page of random data after some delay
        loadRealEstate();
    }

    @Override
    public synchronized boolean isLoading() {
        return loading; // Return boolean weather data is already loading or not
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages; // If all pages are loaded return true
    }

    private void loadRealEstate() {
        Log.d("Load","Load realestate");
        errorLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mSubscriptions.add(NetworkUtil.getRetrofit().getTutions(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(ResponseTution response) {
        Log.d("Respones",response.getTutionfeed().toString());
        errorLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        //mainLayout.setVisibility(View.VISIBLE);
        page++;
        tutionsAdapter.add(response.getTutionfeed());
        loading = false;

    }

    private void handleError(Throwable error) {
        Log.d("Error",error.toString());
        // mainLayout.setVisibility(View.GONE);
        if(tutionCardArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }

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

        Snackbar.make(findViewById(R.id.main_tution),message,Snackbar.LENGTH_SHORT).show();

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





}
