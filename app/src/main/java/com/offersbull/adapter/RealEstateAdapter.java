package com.offersbull.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.offersbull.R;
import com.offersbull.activity.SingleRealEstateActivity;
import com.offersbull.model.RealState;
import com.offersbull.model.RealStateCard;
import com.offersbull.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AD on 1/17/2017.
 */

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateAdapter.ViewHolder> {

    private ArrayList<RealStateCard> realStateArrayList;
    private Context context;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title,tv_name,tv_city,tv_price,tv_type,tv_date,tv_views;
        public ImageView img_post;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tvPost_title);
            tv_name = (TextView) itemView.findViewById(R.id.tvPost_owner);
            tv_city = (TextView) itemView.findViewById(R.id.tvCity);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_views = (TextView) itemView.findViewById(R.id.tv_views);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleRealEstateActivity.class);
                    intent.putExtra("id",realStateArrayList.get(getAdapterPosition()).getRealid());
                    context.startActivity(intent);

                }
            });
        }
    }

    public RealEstateAdapter(Context context,ArrayList<RealStateCard> realStateArrayList,String type) {
        this.context = context;
        this.realStateArrayList = realStateArrayList;
        this.type = type;
    }

    public void add(ArrayList<RealStateCard> items) {
        int previousDataSize = this.realStateArrayList.size();
        this.realStateArrayList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != realStateArrayList ? realStateArrayList.size() : 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (type.matches(Constants.MAIN)) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_real_estate, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_similar_real_estate, parent, false);
        }
        ViewHolder holder = new ViewHolder(itemView);
        itemView.setTag(holder);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RealStateCard realState = realStateArrayList.get(position);
        holder.tv_title.setText(realState.getTitle());
        holder.tv_name.setText(realState.getName());
        holder.tv_city.setText(realState.getCity());
        holder.tv_price.setText(realState.getPrice());
        holder.tv_views.setText(String.valueOf(realState.getVisits()));
        holder.tv_type.setText(realState.getType());

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(realState.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.tv_date.setText(timeAgo);

        Picasso.with(context)
                .load(Constants.BASE_IMAGE_URL+realState.getPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_post);
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
