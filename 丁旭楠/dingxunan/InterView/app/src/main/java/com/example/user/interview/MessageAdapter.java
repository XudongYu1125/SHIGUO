package com.example.user.interview;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Conversations> mdata;
    private int resource;
    private List<String> username;

    public MessageAdapter(Context context, List<Conversations> mdata,
                          int resource, List<String> username) {
        this.context = context;
        this.mdata = mdata;
        this.resource = resource;
        this.username = username;

    }

    @Override
    public int getCount() {
        if (mdata == null) {
            return 0;
        }
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void editItem(int uuid, String content, String date) {
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).getUUID() == uuid) {
                mdata.get(i).setContent(content);
                mdata.get(i).setDate(date);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void Sort() {
        Collections.sort(mdata, new Comparator<Conversations>() {
            @Override
            public int compare(Conversations h1, Conversations h2) {
                return h1.getDate().compareTo(h2.getDate());
            }
        });
        Collections.reverse(mdata);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(resource, null);
        }
        ImageView img_head = convertView.findViewById(R.id.ms_head);
        TextView txt_name = convertView.findViewById(R.id.ms_name);
        TextView txt_date = convertView.findViewById(R.id.ms_date);
        TextView txt_content = convertView.findViewById(R.id.ms_content);
        final Conversations mItemData = mdata.get(position);
        img_head.setImageResource(R.drawable.unlogin);
        txt_name.setText(username.get(position));
        txt_date.setText(mItemData.getDate());
        txt_content.setText(mItemData.getContent());

     /*
        if (null == convertView) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(resource, null);
        }
        ImageView img_cake = convertView.findViewById(R.id.ms_head);
        TextView txt_name = convertView.findViewById(R.id.ms_name);
        TextView txt_size = convertView.findViewById(R.id.ms_date);
        TextView txt_price = convertView.findViewById(R.id.ms_content);
        final Map<String, Object> mItemData = mdata.get(position);
        img_cake.setImageResource((int) mItemData.get("defaulthead"));
        txt_name.setText(mItemData.get("name").toString());
        txt_size.setText(mItemData.get("date").toString());
        txt_price.setText(mItemData.get("content").toString());

      */
        return convertView;

    }


}
