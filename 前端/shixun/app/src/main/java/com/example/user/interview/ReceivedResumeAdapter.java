package com.example.user.interview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class ReceivedResumeAdapter extends BaseAdapter {
    private List<Resume> resumes;
    private int item_id;
    private Context context;

    public ReceivedResumeAdapter(List<Resume> resumes, int item_id, Context context) {
        this.resumes = resumes;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (resumes.size() != 0){
            return resumes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (resumes.size() != 0){
            return resumes.get(position);
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
            viewHolder.llAllResume = convertView.findViewById(R.id.ll_all_paper);
            viewHolder.llAllResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,NewResumeActivity.class);
                    intent.putExtra("myresume",new Gson().toJson(resumes.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItemName.setText(resumes.get(position).getResumename());
        return convertView;
    }
    private class ViewHolder{
        public TextView tvItemName;
        public LinearLayout llAllResume;
    }
}
