package com.tnt.watchhome.ui.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tnt.watchhome.Bean.AppItem;
import com.tnt.watchhome.R;

import java.util.List;


public class ApplistAdapter extends BaseAdapter {
    private static final String TAG = "watch";

    private List<AppItem> mListData ;


    public ApplistAdapter(List<AppItem> list){
        mListData = list ;

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item,parent,false);
            holder = new ViewHolder() ;
            holder.view = convertView ;
            holder.image = convertView.findViewById(R.id.icon_image) ;
            holder.textView = convertView.findViewById(R.id.app_name) ;
            convertView.setTag(holder);
            Log.i(TAG,"image = "+holder.view + " image = "+holder.image) ;


        }else {
            holder = (ViewHolder) convertView.getTag() ;
            Log.i(TAG,"holer = "+holder) ;
        }
        AppItem item  = mListData.get(position);
        holder.image.setImageResource(item.getImageId());
        holder.textView.setText(item.getAppTitleId());
        holder.textView.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.VISIBLE);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,"start app"+holder.textView.getText().toString()) ;

            }
        });

        Log.i(TAG,"postion = "+position + "item name = "+holder.textView.getText().toString()) ;

        return convertView;
    }


    static class ViewHolder {
        View view ;
        ImageView image ;
        TextView textView ;
    }



}
