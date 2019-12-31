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

public class ProblemListAdapter extends BaseAdapter {
    private List<Problem> problems ;
    private int item_id;
    private Context context;

    public ProblemListAdapter(List<Problem> problems, int item_id, Context context) {
        this.problems = problems;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (problems.size() != 0){
            return problems.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (problems.size() != 0) {
            return problems.get(position);
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
            viewHolder.tvProblemName = convertView.findViewById(R.id.tv_item_name);
            viewHolder.llPromblem = convertView.findViewById(R.id.ll_all_paper);
            viewHolder.llPromblem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,ProblemActivity.class);
                    intent.putExtra("problem",new Gson().toJson(problems.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvProblemName.setText(problems.get(position).getContent());
        return convertView;
    }
    private class ViewHolder{
        public TextView tvProblemName;
        public LinearLayout llPromblem;
    }
}
