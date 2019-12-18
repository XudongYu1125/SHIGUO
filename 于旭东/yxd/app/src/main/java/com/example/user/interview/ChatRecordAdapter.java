package com.example.user.interview;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.example.user.interview.ChatRecordActivity.getUserId;


public class ChatRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ChatRecord> mData;
    private int resource;

    public ChatRecordAdapter(Context context, List<ChatRecord> mData,
                             int resource) {
        this.context = context;
        this.mData = mData;
        this.resource = resource;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder1 holder1 = new ViewHolder1();
        //ViewHolder2 holder2 = new ViewHolder2();

        ImageView img;
        TextView txt;
       /* if (convertView == null) {
            if (getUserId() == mData.get(position).getSenderid()) {
                convertView = LayoutInflater.from(context).inflate(R.layout.message_itemi, parent, false);
                //holder1.img_coni = (ImageView) convertView.findViewById(R.id.con_imgi);
                //holder1.txt_coni = (TextView) convertView.findViewById(R.id.con_tvi);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.message_itemu, parent, false);

                //holder2.img_conu = (ImageView) convertView.findViewById(R.id.con_imgu);
                //holder2.txt_conu = (TextView) convertView.findViewById(R.id.con_tvu);
                //convertView.setTag(R.id.Tag_Recevier, holder2);
            }
        } else {

            if (getUserId() == mData.get(position).getSenderid()) {

                //holder1 = (ViewHolder1) convertView.getTag(R.id.Tag_Sender);
            } else {

                //holder2 = (ViewHolder2) convertView.getTag(R.id.Tag_Recevier);
            }


        }*/

        if (getUserId() == mData.get(position).getSenderid()) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_itemi, parent, false);
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_itemu, parent, false);
        }


        if (getUserId() == mData.get(position).getSenderid()) {
            img = (ImageView) convertView.findViewById(R.id.con_imgi);
            txt = (TextView) convertView.findViewById(R.id.con_tvi);
            img.setImageResource(R.drawable.unlogin);
            txt.setText(mData.get(position).getContent());
        } else {
            img = (ImageView) convertView.findViewById(R.id.con_imgu);
            txt = (TextView) convertView.findViewById(R.id.con_tvu);
            img.setImageResource(R.drawable.unlogin);
            txt.setText(mData.get(position).getContent());
        }

        /*if (getUserId() == mData.get(position).getSenderid()) {
            //holder1.img_coni.setImageResource(send.getImageurl());

            holder1.txt_coni.setText(mData.get(position).getContent());
            //holder1.txt_coni.setText(str);

        } else {
            //holder2.img_conu.setImageResource(receive.getImageurl());
            holder2.img_conu.setImageResource(R.mipmap.defaulthead);
            holder2.txt_conu.setText(mData.get(position).getContent());
            //holder2.txt_conu.setText(str);

        }

         */
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
