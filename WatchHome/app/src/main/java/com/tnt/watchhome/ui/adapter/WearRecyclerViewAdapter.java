package com.tnt.watchhome.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnt.watchhome.Bean.AppInfo;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.listener.ItemClickListener;
import com.tnt.watchhome.widget.WearableRecyclerView;

import java.util.List;

public class WearRecyclerViewAdapter extends  WearableRecyclerView.Adapter<WearRecyclerViewAdapter.ViewHolder>{

    private List<AppInfo> mListData ;

    private View mView ;
    private ItemClickListener mItemListener ;


    public WearRecyclerViewAdapter(List<AppInfo> data , ItemClickListener listener) {
        mListData = data ;
        mItemListener = listener ;
    }

    public void updateData(List<AppInfo> data) {
        this.mListData = data ;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item,parent,false) ;
            //mHolder = new ViewHolder(mView) ;
            //mView.setTag(mHolder);

        return new ViewHolder(mView,mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (null == mListData)return ;
        AppInfo appInfo = mListData.get(position);
        if (null == appInfo) return ;

        holder.iconView.setImageDrawable(appInfo.getDrawable());
        holder.textView.setText(appInfo.getAppName());
        holder.iconView.setTag(position);
        holder.textView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return null == mListData ? 0 : mListData.size();
    }

    public static class ViewHolder extends WearableRecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iconView ;
        private TextView textView ;
        private ItemClickListener mListener ;

        public ViewHolder(View view,ItemClickListener listener) {
            super(view);
            mListener = listener ;
            iconView = view.findViewById(R.id.icon_image);
            textView = view.findViewById(R.id.app_name) ;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());

        }
    }






}
