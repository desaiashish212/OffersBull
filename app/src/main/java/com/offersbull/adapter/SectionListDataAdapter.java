package com.offersbull.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.offersbull.R;
import com.offersbull.activity.SingleAutomobileActivity;
import com.offersbull.activity.SingleHotelActivity;
import com.offersbull.activity.SingleOtherActivity;
import com.offersbull.activity.SingleRealEstateActivity;
import com.offersbull.activity.SingleTravellingActivity;
import com.offersbull.activity.SingleTutionActivity;
import com.offersbull.model.RealStateCard;
import com.offersbull.model.SingleItemModel;
import com.offersbull.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AD on 1/16/2017.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    public int position;
    private String type;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList,String type) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.type = type;
    }
    public int getPosition() {
        return position;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        if (type.matches(Constants.MAIN)) {
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_single_card, viewGroup, false);
        }else {
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_real_estate, viewGroup, false);
        }
        SingleItemRowHolder mh = new SingleItemRowHolder(itemView);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        position = i;
        SingleItemModel singleItem = itemsList.get(i);


        Picasso.with(mContext)
                .load(Constants.BASE_IMAGE_URL+singleItem.getPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_post);
        holder.tvPost_Title.setText(singleItem.getTitle());
        holder.tvPost_owner.setText("By "+singleItem.getName());
        holder.tv_city.setText(singleItem.getArea()+","+singleItem.getCity());
        if (!singleItem.getPrice().isEmpty())
            holder.tv_price.setText("RS. "+singleItem.getPrice()+"/-");
        else holder.tv_price.setVisibility(View.GONE);
        if (!singleItem.getType().isEmpty())
            holder.tv_type.setText(singleItem.getType());
        else holder.tv_type.setText(" ");
        holder.tv_visits.setText(String.valueOf(singleItem.getVisits()));
        holder.tv_date.setText(singleItem.getDate());



           /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    public void add(ArrayList<SingleItemModel> items) {
        int previousDataSize = this.itemsList.size();
        this.itemsList.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvPost_Title;
        protected TextView tvPost_owner;
        protected TextView tv_city,tv_type,tv_price,tv_visits,tv_date;
        protected ImageView img_post;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvPost_Title = (TextView) view.findViewById(R.id.tvPost_title);
            this.tvPost_owner = (TextView) view.findViewById(R.id.tvPost_owner);
            this.tv_city = (TextView) view.findViewById(R.id.tvCity);
            this.tv_type = (TextView) view.findViewById(R.id.tv_type);
            this.tv_price = (TextView) view.findViewById(R.id.tv_price);
            this.tv_visits = (TextView) view.findViewById(R.id.tv_views);
            this.tv_date = (TextView) view.findViewById(R.id.tv_date);
            this.img_post = (ImageView) view.findViewById(R.id.img_post);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemsList.get(getAdapterPosition()).getCategory()==0){
                        Intent intent = new Intent(mContext, SingleRealEstateActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                    if (itemsList.get(getAdapterPosition()).getCategory()==1){
                        Intent intent = new Intent(mContext, SingleTutionActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                    if (itemsList.get(getAdapterPosition()).getCategory()==2){
                        Intent intent = new Intent(mContext, SingleHotelActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                    if (itemsList.get(getAdapterPosition()).getCategory()==3){
                        Intent intent = new Intent(mContext, SingleTravellingActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);}
                    if (itemsList.get(getAdapterPosition()).getCategory()==4){
                        Intent intent = new Intent(mContext, SingleAutomobileActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                    if (itemsList.get(getAdapterPosition()).getCategory()==5){
                        Intent intent = new Intent(mContext, SingleOtherActivity.class);
                        intent.putExtra("id",itemsList.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }


                }
            });


        }

    }

}