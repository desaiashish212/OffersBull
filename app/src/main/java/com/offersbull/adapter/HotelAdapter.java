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
import com.offersbull.activity.SingleRealEstateActivity;
import com.offersbull.model.Hotel;
import com.offersbull.model.HotelCard;
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

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private ArrayList<HotelCard> hotelCardArrayList;
    private Context context;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title,tv_owner_post,tv_city,tv_type,tv_date,tv_views;
        public ImageView img_post;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tvPost_title);
            tv_owner_post = (TextView) itemView.findViewById(R.id.tvPost_owner);
            tv_city = (TextView) itemView.findViewById(R.id.tvCity);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_views = (TextView) itemView.findViewById(R.id.tv_views);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleHotelActivity.class);
                    intent.putExtra("id",hotelCardArrayList.get(getAdapterPosition()).getHotelid());
                    context.startActivity(intent);
                }
            });

        }
    }

    public HotelAdapter(Context context,ArrayList<HotelCard> hotelCardArrayList,String type) {
        this.context = context;
        this.hotelCardArrayList = hotelCardArrayList;
        this.type = type;
    }

    public void add(ArrayList<HotelCard> items) {
        int previousDataSize = this.hotelCardArrayList.size();
        this.hotelCardArrayList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != hotelCardArrayList ? hotelCardArrayList.size() : 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (type.matches(Constants.MAIN)) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_hotel, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_similarhotel, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotelCard hotelCard = hotelCardArrayList.get(position);
        Picasso.with(context)
                .load(Constants.BASE_IMAGE_URL+hotelCard.getPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_post);
        holder.tv_title.setText(hotelCard.getTitle());
        holder.tv_owner_post.setText(hotelCard.getName());
        holder.tv_city.setText(hotelCard.getCity());
        holder.tv_type.setText(hotelCard.getType());
        holder.tv_views.setText(String.valueOf(hotelCard.getVisits()));
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(hotelCard.getDate()))),
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
