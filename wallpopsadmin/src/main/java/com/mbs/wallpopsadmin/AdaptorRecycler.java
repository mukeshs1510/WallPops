package com.mbs.wallpopsadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptorRecycler extends RecyclerView.Adapter<AdaptorRecycler.MyViewHolder> {

    String[] mTitle;
    int[] mImages;
    Context context;

    public AdaptorRecycler(Context ct, String[] title, int[] images){
        context = ct;
        mTitle = title;
        mImages = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recycler,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.titlee.setText(mTitle[position]);
        holder.imagee.setImageResource(mImages[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context,AddNewWall.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context,AllTheWall.class));
                        break;
                }
            }
        });
    }

        @Override
        public int getItemCount() {
            return mTitle.length;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView titlee,descc;
            ImageView imagee;
            CardView linearLayout;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                titlee = itemView.findViewById(R.id.titleRow);
                imagee = itemView.findViewById(R.id.imaggViewRow);
                linearLayout = itemView.findViewById(R.id.mainLayout);

            }
        }

}
