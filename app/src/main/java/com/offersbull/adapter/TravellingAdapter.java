package com.offersbull.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.offersbull.R;
import com.offersbull.activity.SingleHotelActivity;
import com.offersbull.activity.SingleTravellingActivity;
import com.offersbull.model.TravellingCard;
import com.offersbull.model.TutionCard;
import com.offersbull.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AD on 1/17/2017.
 */

public class TravellingAdapter extends RecyclerView.Adapter<TravellingAdapter.ViewHolder> {

    private ArrayList<TravellingCard> travellingCardArrayList;
    private Context context;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title,tv_owner_post,tv_city,tv_price,tv_views,tv_date;
        public ImageView img_post;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tvPost_title);
            tv_owner_post = (TextView) itemView.findViewById(R.id.tvPost_owner);
            tv_city = (TextView) itemView.findViewById(R.id.tvCity);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_views = (TextView) itemView.findViewById(R.id.tv_views);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleTravellingActivity.class);
                    intent.putExtra("id",travellingCardArrayList.get(getAdapterPosition()).getTravelid());
                    context.startActivity(intent);
                }
            });

        }
    }

    public TravellingAdapter(Context context,ArrayList<TravellingCard> travellingCardArrayList,String type) {
        this.context = context;
        this.travellingCardArrayList = travellingCardArrayList;
        this.type = type;
    }

    public void add(ArrayList<TravellingCard> items) {
        int previousDataSize = this.travellingCardArrayList.size();
        this.travellingCardArrayList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != travellingCardArrayList ? travellingCardArrayList.size() : 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (type.matches(Constants.MAIN)) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_travelling, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_similar_travelling, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TravellingCard travellingCard = travellingCardArrayList.get(position);
        Picasso.with(context)
                .load(Constants.BASE_IMAGE_URL+travellingCard.getPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_post);
        holder.tv_title.setText(travellingCard.getTitle());
        holder.tv_owner_post.setText(travellingCard.getName());
        holder.tv_city.setText(travellingCard.getCity());
        if (!travellingCard.getPrice().isEmpty())
            holder.tv_price.setText(travellingCard.getPrice());

        holder.tv_views.setText(String.valueOf(travellingCard.getVisits()));
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(travellingCard.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.tv_date.setText(timeAgo);

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
