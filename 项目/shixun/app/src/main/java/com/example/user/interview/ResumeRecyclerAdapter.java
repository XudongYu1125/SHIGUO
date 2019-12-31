package com.example.user.interview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ResumeRecyclerAdapter extends RecyclerView.Adapter<ResumeRecyclerAdapter.myViewHolder>{

    private ArrayList <Resume> resumes;
    //创建构造函数
    public ResumeRecyclerAdapter(ArrayList<Resume> resumes ) {
        //将传递过来的数据，赋值给本地变量
        this.resumes = resumes;
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = LayoutInflater.from(parent.getContext ()).inflate(R.layout.item_3,parent,false);
        return new myViewHolder (itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder( myViewHolder holder, int position) {

        holder.tv.setText (resumes.get (position).getResumename ());


    }

    public void addItem( Resume resume ){
        resumes.add (resume);
        notifyDataSetChanged ();
    }



    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (resumes == null){
            return 0;
        }else {
            return resumes.size();
        }

    }



    /**自定义viewhodler*/
    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        public myViewHolder( View itemView) {
            super(itemView);
            tv = itemView.findViewById (R.id.item3_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick (v, resumes.get(getLayoutPosition()));
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
        void onItemClick( View view , Resume data );
    }

    /**需要外部访问，所以需要设置set方法，方便调用*/
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }




}

