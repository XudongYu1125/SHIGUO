package com.example.user.interview;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.example.user.interview.ChatRecordActivity.getUserId;


public class ChatRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ChatRecord> mData;
    private List<String> userurl;
    private int resource;

    public ChatRecordAdapter(Context context, List<ChatRecord> mData, List<String> userurl,
                             int resource) {
        this.context = context;
        this.mData = mData;
        this.resource = resource;
        this.userurl = userurl;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(ChatRecord chatRecord) {
        mData.add(chatRecord);
        notifyDataSetChanged();
    }

    public void Sort() {
        Collections.sort(mData, new Comparator<ChatRecord>() {
            @Override
            public int compare(ChatRecord h1, ChatRecord h2) {
                return h1.getDate().compareTo(h2.getDate());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ImageView img;
        TextView txt;


        if (getUserId() == mData.get(position).getSenderid()) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_itemi, parent, false);
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_itemu, parent, false);
        }


        if (getUserId() == mData.get(position).getSenderid()) {
            img = (ImageView) convertView.findViewById(R.id.con_imgi);
            txt = (TextView) convertView.findViewById(R.id.con_tvi);
            Log.e("图片地址", userurl.get(0));
            Glide.with(context)
                    .load(Constant.BASE_URL + "avatarimg/" + userurl.get(0))
                    .into(img);
            txt.setText(mData.get(position).getContent());
        } else {
            img = (ImageView) convertView.findViewById(R.id.con_imgu);
            txt = (TextView) convertView.findViewById(R.id.con_tvu);
            Log.e("图片地址", userurl.get(1));
            Glide.with(context)
                    .load(Constant.BASE_URL + "avatarimg/" + userurl.get(1))
                    .into(img);
            txt.setText(mData.get(position).getContent());
        }


        return convertView;
    }

    private static class ViewHolder1 {
        ImageView img_coni;
        TextView txt_coni;
    }

    private static class ViewHolder2 {
        ImageView img_conu;
        TextView txt_conu;
    }
}
