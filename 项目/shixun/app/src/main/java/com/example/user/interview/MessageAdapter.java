package com.example.user.interview;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private List<Conversationoo> mdata;
    private int resource;

    public MessageAdapter(Context context, List<Conversationoo> mdata,
                          int resource) {
        this.context = context;
        this.mdata = mdata;
        this.resource = resource;
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
            if (mdata.get(i).getCon().getUUID() == uuid) {
                mdata.get(i).getCon().setContent(content);
                mdata.get(i).getCon().setDate(date);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void Sort() {
        Collections.sort(mdata, new Comparator<Conversationoo>() {
            @Override
            public int compare(Conversationoo h1, Conversationoo h2) {
                return h1.getCon().getDate().compareTo(h2.getCon().getDate());
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
        final Conversations mItemData = mdata.get(position).getCon();
        Glide.with(context)
                .load(Constant.BASE_URL + "avatarimg/" + mdata.get(position).getUrl())
                .into(img_head);
        txt_name.setText(mdata.get(position).getName());
        txt_date.setText(mItemData.getDate());
        txt_content.setText(mItemData.getContent());
        return convertView;

    }


}
