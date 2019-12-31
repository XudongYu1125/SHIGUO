package com.example.user.interview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class ExamPaperAdapter extends BaseAdapter{
    private List<Paper> paperList ;
    private int item_id;
    private Context context;

    public ExamPaperAdapter(List<Paper> paperList, int item_id, Context context) {
        this.paperList = paperList;
        this.item_id = item_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (paperList.size() != 0){
            return paperList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (paperList.size() != 0) {
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_id, null);
            viewHolder = new ViewHolder();
            viewHolder.tvPaperName = convertView.findViewById(R.id.tv_paper_title);
            viewHolder.ivPaperIcon = convertView.findViewById(R.id.iv_paper_icon);
            viewHolder.llItemPaper = convertView.findViewById(R.id.ll_item_paper);
            viewHolder.llStar = convertView.findViewById(R.id.ll_star);
            viewHolder.llItemPaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,PaperActivity.class);
                    intent.putExtra("paper",new Gson().toJson(paperList.get(position)));
                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("num",paperList.get(position).getLevel()+"");
        viewHolder.llStar.removeAllViews();
        for (int i = 0; i < paperList.get(position).getLevel(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));  //设置图片宽高
            imageView.setImageResource(R.drawable.star); //图片资源
            viewHolder.llStar.addView(imageView); //动态添加图片
        }
        viewHolder.tvPaperName.setText(paperList.get(position).getName());
        Glide.with(context).load(Constant.BASE_URL +"paperimg/"+ paperList.get(position).getImageurl()).into(viewHolder.ivPaperIcon);
        return convertView;
    }
    private class ViewHolder{
        public TextView tvPaperName;
        public ImageView ivPaperIcon;
        public LinearLayout llItemPaper;
        public LinearLayout llStar;
    }
}
