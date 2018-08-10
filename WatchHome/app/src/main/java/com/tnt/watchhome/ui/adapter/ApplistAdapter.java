package com.tnt.watchhome.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tnt.watchhome.Bean.AppItem;
import com.tnt.watchhome.R;

import java.util.List;

public class ApplistAdapter extends RecyclerView.Adapter<ApplistAdapter.ViewHolder> {

    List<AppItem> mDataItems ;



    public ApplistAdapter(List<AppItem> list){
        mDataItems = list ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"start app",Toast.LENGTH_SHORT) ;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppItem appItem = mDataItems.get(position);

        holder.iconImage.setImageResource(appItem.getImageId());
        holder.appName.setText(appItem.getAppTitleId());




    }

    @Override
    public int getItemCount() {
        return mDataItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView ;
        ImageView iconImage  ;
        TextView  appName ;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView ;
            iconImage = itemView.findViewById(R.id.icon_image);
            appName = itemView.findViewById(R.id.app_name);


        }
    }
}
