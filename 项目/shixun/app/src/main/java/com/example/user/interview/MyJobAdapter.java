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

public class MyJobAdapter extends BaseAdapter {
    private List<JobSearch> jobSearches ;
    private int item_id;
    private Context context;

    public MyJobAdapter(List<JobSearch> paperList, int item_id, Context context) {
        this.jobSearches = paperList;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (jobSearches.size() != 0){
            return jobSearches.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (jobSearches.size() != 0) {
            return jobSearches.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_id, null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = convertView.findViewById(R.id.ll_request_job);
            viewHolder.tvJobName = convertView.findViewById(R.id.tv_request_name);
            viewHolder.tvJobCity = convertView.findViewById(R.id.tv_request_city);
            viewHolder.tvJobSalary = convertView.findViewById(R.id.tv_request_salary);
            viewHolder.tvJobTime = convertView.findViewById(R.id.tv_request_time);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,MyJobSearchActivity.class);
                    intent.putExtra("job",new Gson().toJson(jobSearches.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvJobName.setText(jobSearches.get(position).getPosition());
        viewHolder.tvJobSalary.setText(jobSearches.get(position).getSalary());
        viewHolder.tvJobTime.setText(jobSearches.get(position).getDate());
        viewHolder.tvJobCity.setText(jobSearches.get(position).getPlace());
        return convertView;
    }
    private class ViewHolder{
        public LinearLayout linearLayout;
        public TextView tvJobName;
        public TextView tvJobCity;
        public TextView tvJobSalary;
        public TextView tvJobTime;
    }
}
