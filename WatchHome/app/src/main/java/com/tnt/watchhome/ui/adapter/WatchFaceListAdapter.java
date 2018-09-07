package com.tnt.watchhome.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnt.watchhome.Bean.WatchFaceInfo;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.listener.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class WatchFaceListAdapter extends RecyclerView.Adapter<WatchFaceListAdapter.ViewHolder> {

    private static final String TAG="WatchFaceListAdapter";

    private List<WatchFaceInfo> mListData ;
    private ItemClickListener mListener ;


    public WatchFaceListAdapter(ArrayList<WatchFaceInfo> data, ItemClickListener listener) {
        mListData = data ;
        mListener = listener ;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchface_item, parent, false);

        return new ViewHolder(mView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (null == mListData ) return ;
        WatchFaceInfo watchinfo = mListData.get(position) ;
        if (null == watchinfo) return ;
        holder.watchfaceIcon.setImageDrawable(watchinfo.getWatchfaceIcon());
        holder.watchfaceName.setText(watchinfo.getWatchfaceName());
    }


    public static class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView watchfaceIcon ;
         TextView watchfaceName ;

        private ItemClickListener mListener ;

        ViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            mListener = listener ;
            watchfaceIcon = itemView.findViewById(R.id.watchface_icon) ;
            watchfaceName = itemView.findViewById(R.id.watchface_name) ;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());

            Log.i(TAG,"watch face change onclick") ;
        }
    }

    @Override
    public int getItemCount() {
        return null == mListData ? 0 : mListData.size();
    }



    public WatchFaceInfo getItem(int position) {
        if (null == mListData) return null ;
        return mListData.get(position) ;
    }
}
