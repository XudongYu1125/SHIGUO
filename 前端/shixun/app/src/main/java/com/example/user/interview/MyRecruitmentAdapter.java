package com.example.user.interview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class MyRecruitmentAdapter extends BaseAdapter {
    private List<Position> positions ;
    private int item_id;
    private Context context;

    public MyRecruitmentAdapter(List<Position> paperList, int item_id, Context context) {
        this.positions = paperList;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (positions.size() != 0){
            return positions.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (positions.size() != 0) {
            return positions.get(position);
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
            viewHolder.linearLayout = convertView.findViewById(R.id.ll_job);
            viewHolder.tvJobName = convertView.findViewById(R.id.tv_my_job_name);
            viewHolder.tvJobCity = convertView.findViewById(R.id.tv_my_job_city);
            viewHolder.tvJobSalary = convertView.findViewById(R.id.tv_my_job_salary);
            viewHolder.tvJobTime = convertView.findViewById(R.id.tv_my_job_time);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,MyPositionActivity.class);
                    intent.putExtra("position",new Gson().toJson(positions.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvJobName.setText(positions.get(position).getName());
        viewHolder.tvJobSalary.setText(positions.get(position).getSalary());
        viewHolder.tvJobTime.setText(positions.get(position).getDate());
        viewHolder.tvJobCity.setText(positions.get(position).getPlace());
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
