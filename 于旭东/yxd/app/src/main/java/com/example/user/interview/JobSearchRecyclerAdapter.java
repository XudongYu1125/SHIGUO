package com.example.user.interview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author june
 */
public class JobSearchRecyclerAdapter extends RecyclerView.Adapter<JobSearchRecyclerAdapter.myViewHolder>{
    private Context context;
    private ArrayList <JobSearch> jobSearches;

    //创建构造函数
    public JobSearchRecyclerAdapter( Context context, ArrayList<JobSearch> jobSearches ) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;
        this.jobSearches = jobSearches;
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_2,parent,false);
        return new myViewHolder (itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder( @NonNull myViewHolder holder , int position ) {
        JobSearch data = jobSearches.get (position);
//        holder.mItemUserName.setText(data.get ());
        holder.mItemAttention.setText (data.getPosition ());
        holder.mItemTime.setText (data.getDate ());
    }



    public void addItemJobSearch(JobSearch jb){

        jobSearches.add (0,jb);
        notifyDataSetChanged ();

    }



    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return jobSearches.size();
    }

    /**自定义viewhodler*/
    class myViewHolder extends RecyclerView.ViewHolder {
        private ImageView mItemUserImg;
        private TextView mItemUserName;
        private TextView mItemAttention;
        private TextView mItemTime;

        public myViewHolder( View itemView) {
            super(itemView);
//            mItemUserImg = itemView.findViewById(R.id.user_img);
            mItemUserName = itemView.findViewById(R.id.user_name);
            mItemAttention = itemView.findViewById (R.id.attention);
            mItemTime = itemView.findViewById (R.id.publish_date);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, jobSearches.get(getLayoutPosition()-1));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        void OnItemClick( View view , JobSearch data );
    }

    /**需要外部访问，所以需要设置set方法，方便调用*/
    private JobSearchRecyclerAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener( JobSearchRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public void setFilter( List <JobSearch> jobSearchList ){

        jobSearches = new ArrayList <> ();
        jobSearches.addAll (jobSearchList);
        notifyDataSetChanged ();

    }

    public void animateTo(List<JobSearch> jb) {
        applyAndAnimateRemovals(jb);
        applyAndAnimateAdditions(jb);
        applyAndAnimateMovedItems(jb);
    }




    private void applyAndAnimateRemovals(List<JobSearch> jobSearchList) {
        for (int i = jobSearchList.size() - 1; i >= 0; i--) {
            final JobSearch jb = jobSearchList.get(i);
            if (!jobSearchList.contains(jb)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<JobSearch> jobSearchList) {
        for (int i = 0, count = jobSearchList.size(); i < count; i++) {
            final JobSearch jb = jobSearchList.get(i);
            if (!jobSearchList.contains(jb)) {
                addItem(i, jb);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<JobSearch> jobSearchList) {
        for (int toPosition = jobSearchList.size() - 1; toPosition >= 0; toPosition--) {
            final JobSearch jb = jobSearchList.get(toPosition);
            final int fromPosition = jobSearchList.indexOf(jb);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public JobSearch removeItem(int position) {
        final JobSearch jb = jobSearches.remove(position);
        notifyItemRemoved(position);
        return jb;
    }


    public void addItem(int position, JobSearch jb) {
        jobSearches.add(position, jb);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final JobSearch jb = jobSearches.remove(fromPosition);
        jobSearches.add(toPosition,jb);
        notifyItemMoved(fromPosition, toPosition);
    }

}
