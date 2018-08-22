package com.tnt.watchhome.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnt.watchhome.Bean.AppInfo;
import com.tnt.watchhome.R;
import com.tnt.watchhome.contorl.Controller;
import com.tnt.watchhome.widget.WearableRecyclerView;

import java.util.List;

public class WearRecyclerViewAdapter extends  WearableRecyclerView.Adapter<WearRecyclerViewAdapter.ViewHolder>{

    private List<AppInfo> mListData ;

    private View mView ;
    private Controller mController ;



    public WearRecyclerViewAdapter(List<AppInfo> data , Controller controller) {
        mListData = data ;
        mController = controller ;
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


        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (null == mListData)return ;
        AppInfo appInfo = mListData.get(position);
        if (null == appInfo) return ;

        holder.iconView.setImageDrawable(appInfo.getAdrawable());
        holder.textView.setText(appInfo.getAppName());
        holder.iconView.setTag(position);
        holder.textView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return null == mListData ? 0 : mListData.size();
    }

    public static class ViewHolder extends WearableRecyclerView.ViewHolder {
        private ImageView iconView ;
        private TextView textView ;

        public ViewHolder(View view) {
            super(view);
            iconView = view.findViewById(R.id.icon_image);
            textView = view.findViewById(R.id.app_name) ;

        }

    }






}
