package com.emejia.list_topics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emejia.list_topics.R;
import com.emejia.list_topics.model.ListItem;
import com.emejia.list_topics.view.PictureDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emejia on 13/03/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    private Activity activity;

    public ListAdapter(List<ListItem> listItems, Activity activity) {
        this.listItems = listItems;
        //this.context = context;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.textViewUrl.setText(listItem.getUrl());

        Picasso.with(activity).load(listItem.getImage()).into(holder.imageItem);


        holder.linearlayoutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PictureDetailActivity.class);
                intent.putExtra("Desc",listItem.getDesc());
                intent.putExtra("Head",listItem.getHead());
                intent.putExtra("Image",listItem.getImage());
                intent.putExtra("Url",listItem.getUrl());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transitionname_picture)).toBundle());

                }else {
                    activity.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewUrl;
        public ImageView imageItem;
        public LinearLayout linearlayoutItems;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);
            imageItem = (ImageView) itemView.findViewById(R.id.imageItem);
            linearlayoutItems = (LinearLayout) itemView.findViewById(R.id.linearLayoutItems);
        }
    }
}
