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

public class ModelPaperAdapter extends BaseAdapter{
    private List<Paper> paperList;
    private int item_id;
    private Context context;

    public ModelPaperAdapter(List<Paper> paperList, int item_id, Context context) {
        this.paperList = paperList;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        return paperList.size();
    }

    @Override
    public Object getItem(int position) {
        if (paperList.size() != 0){
            return paperList.get(position);
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
            viewHolder.tvItemName = convertView.findViewById(R.id.tv_item_name);
            viewHolder.llAllPaper = convertView.findViewById(R.id.ll_all_paper);
            viewHolder.llAllPaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,PaperActivity.class);
                    intent.putExtra("paper",new Gson().toJson(paperList.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItemName.setText(paperList.get(position).getName());
        return convertView;
    }
    private class ViewHolder{
        public TextView tvItemName;
        public LinearLayout llAllPaper;
    }
}
