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
import com.offersbull.activity.SingleOtherActivity;
import com.offersbull.activity.SingleTutionActivity;
import com.offersbull.model.OtherCard;
import com.offersbull.model.TravellingCard;
import com.offersbull.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AD on 1/17/2017.
 */

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {

    private ArrayList<OtherCard> otherCardArrayList;
    private Context context;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title,tv_owner_post,tv_city,tv_views,tv_date;
        public ImageView img_post;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tvPost_title);
            tv_owner_post = (TextView) itemView.findViewById(R.id.tvPost_owner);
            tv_city = (TextView) itemView.findViewById(R.id.tvCity);
            tv_views = (TextView) itemView.findViewById(R.id.tv_views);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleOtherActivity.class);
                    intent.putExtra("id",otherCardArrayList.get(getAdapterPosition()).getOtherid());
                    context.startActivity(intent);
                }
            });

        }
    }

    public OtherAdapter(Context context,ArrayList<OtherCard> otherCardArrayList,String type) {
        this.context = context;
        this.otherCardArrayList = otherCardArrayList;this.context = context;
        this.type = type;
    }

    public void add(ArrayList<OtherCard> items) {
        int previousDataSize = this.otherCardArrayList.size();
        this.otherCardArrayList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != otherCardArrayList ? otherCardArrayList.size() : 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (type.matches(Constants.MAIN)) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_tution, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_similar_tution, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OtherCard otherCard = otherCardArrayList.get(position);
        holder.tv_title.setText(otherCard.getTitle());
        holder.tv_owner_post.setText(otherCard.getName());
        holder.tv_city.setText(otherCard.getCity());
        holder.tv_views.setText(String.valueOf(otherCard.getVisits()));
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(otherCard.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.tv_date.setText(timeAgo);

        Picasso.with(context)
                .load(Constants.BASE_IMAGE_URL+otherCard.getPath())
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
