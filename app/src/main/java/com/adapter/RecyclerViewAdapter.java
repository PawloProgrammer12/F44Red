/**
 * com.adapter contains adapter for RecyclerView.
 */
package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.f44red.R;
import com.f44red.WPPostDetails;
import com.model.Model;

import java.util.ArrayList;

/**
 * Contains methods for setting Wordpress endpoints like article title, subtitle
 * and image.
 * @author Paweł Turoń
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Model> dataSet;
    private Context mContext;
    public RecyclerViewAdapter(ArrayList<Model> mList, Context mContext){
        this.dataSet = mList;
        this.mContext = mContext;
    }

    /**
     * Static class for binding view holder.
     */
    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView title, subtitle;
        ImageView imageView;
        public ImageTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.imageView = (ImageView) itemView.findViewById(R.id.Icon);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ImageTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Model object = dataSet.get(position);

        ((ImageTypeViewHolder) holder).title.setText(object.title);
        ((ImageTypeViewHolder) holder).subtitle.setText(object.subtitle);

        ((ImageTypeViewHolder) holder).title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WPPostDetails.class);
                intent.putExtra("itemPosition", position);
                mContext.startActivity(intent);
            }
        });
        ((ImageTypeViewHolder) holder).subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WPPostDetails.class);
                intent.putExtra("itemPosition", position);
                mContext.startActivity(intent);
            }
        });
        ((ImageTypeViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WPPostDetails.class);
                intent.putExtra("itemPosition", position);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Counter of data.
     * @return size of counted items for array list.
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
