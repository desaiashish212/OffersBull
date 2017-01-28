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
import com.offersbull.activity.SingleRealEstateActivity;
import com.offersbull.activity.SingleTutionActivity;
import com.offersbull.model.RealStateCard;
import com.offersbull.model.Tution;
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

public class TutionsAdapter extends RecyclerView.Adapter<TutionsAdapter.ViewHolder> {

    private ArrayList<TutionCard> tutionArrayList;
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
                    Intent intent = new Intent(context, SingleTutionActivity.class);
                    intent.putExtra("id",tutionArrayList.get(getAdapterPosition()).getTutid());
                    context.startActivity(intent);
                }
            });

        }
    }

    public TutionsAdapter(Context context,ArrayList<TutionCard> tutionArrayList,String type) {
        this.context = context;
        this.tutionArrayList = tutionArrayList;
        this.type = type;
    }

    public void add(ArrayList<TutionCard> items) {
        int previousDataSize = this.tutionArrayList.size();
        this.tutionArrayList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != tutionArrayList ? tutionArrayList.size() : 0);
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
        TutionCard tution = tutionArrayList.get(position);
        holder.tv_title.setText(tution.getTitle());
        holder.tv_owner_post.setText(tution.getName());
        holder.tv_city.setText(tution.getArea()+","+tution.getCity());
        holder.tv_views.setText(String.valueOf(tution.getVisits()));
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(getLongDate(tution.getDate()))),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.tv_date.setText(timeAgo);

        Picasso.with(context)
                .load(Constants.BASE_IMAGE_URL+tution.getPath())
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
