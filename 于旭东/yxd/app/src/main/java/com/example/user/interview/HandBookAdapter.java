package com.example.user.interview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class HandBookAdapter extends BaseAdapter {
    private List<HandBook> handBookList ;
    private int item_id;
    private Context context;

    public HandBookAdapter(List<HandBook> handBookList, int item_id, Context context) {
        this.handBookList = handBookList;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (handBookList.size() != 0){
        return handBookList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (handBookList.size()!=0){
            return handBookList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_id, null);
            viewHolder = new ViewHolder();
            viewHolder.tvHandBookName = convertView.findViewById(R.id.tv_item_name);
            viewHolder.llHandBook = convertView.findViewById(R.id.ll_all_paper);
            viewHolder.llHandBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,HandBookActivity.class);
                    intent.putExtra("handbook",new Gson().toJson(handBookList.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvHandBookName.setText(handBookList.get(position).getName());
        return convertView;
    }
    private class ViewHolder{
        public TextView tvHandBookName;
        public LinearLayout llHandBook;
    }
}
