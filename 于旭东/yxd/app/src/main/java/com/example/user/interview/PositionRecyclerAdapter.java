package com.example.user.interview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author june
 */
public class PositionRecyclerAdapter extends RecyclerView.Adapter<PositionRecyclerAdapter.myViewHolder>{
    private Context context;
    private ArrayList <Position> positions;
    private ArrayList<User> users;
    private ArrayList<Company> companies;
    private Date date;
    //创建构造函数
    public PositionRecyclerAdapter( Context context, ArrayList<Position> positions ) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;
        this.positions = positions;
//        this.users = users;
//        this.companies = companies;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_1,parent,false);
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
        //根据点击位置绑定数据
        Position positionData = positions.get(position);
        //User userData = users.get (position);
       // Company companyData = companies.get (position);

        //加载头像
       // Glide.with (context).load (userData.getImageurl ()).into (holder.mItemUserImg);

        //职位名称
        holder.mItemJobName.setText(positionData.getName ());

        //公司名
      //  holder.mItemCompanyName.setText (companyData.getName ());

        //工作城市
        holder.mItemCity.setText (positionData.getPlace ());

        //薪水
        holder.mItemSalary.setText (String.valueOf (positionData.getSalary ()));

        //发布时间
        holder.mItemTime.setText (positionData.getDate ());

    }

    public void addItem(Position p){
        positions.add (0,p);
        notifyDataSetChanged ();
    }



    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return positions.size();
    }



    /**自定义viewhodler*/
    class myViewHolder extends RecyclerView.ViewHolder {
        private ImageView mItemUserImg;
        private TextView mItemJobName;
        private TextView mItemCompanyName;
        private TextView mItemCity;
        private TextView mItemSalary;
        private TextView mItemTime;

        public myViewHolder( View itemView) {
            super(itemView);
            mItemUserImg = itemView.findViewById (R.id.user_img);
            mItemJobName = itemView.findViewById(R.id.job_name);
            mItemCompanyName = itemView.findViewById(R.id.company_name);
            mItemCity = itemView.findViewById (R.id.work_city);
            mItemSalary = itemView.findViewById (R.id.want_salary);
            mItemTime = itemView.findViewById (R.id.publish_time);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick (v, positions.get(getLayoutPosition()-1));
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
        void onItemClick( View view , Position data );
    }

    /**需要外部访问，所以需要设置set方法，方便调用*/
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setFilter( List<Position> positionList ){

        positions = new ArrayList <> ();
        positions.addAll (positionList);
        notifyDataSetChanged ();

    }

    public void animateTo(List<Position> p) {
        applyAndAnimateRemovals(p);
        applyAndAnimateAdditions(p);
        applyAndAnimateMovedItems(p);
    }




    private void applyAndAnimateRemovals(List<Position> positions) {
        for (int i = positions.size() - 1; i >= 0; i--) {
            final Position Position = positions.get(i);
            if (!positions.contains(Position)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Position> Positions) {
        for (int i = 0, count = Positions.size(); i < count; i++) {
            final Position Position = positions.get(i);
            if (!positions.contains(Position)) {
                addItem(i, Position);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Position> Positions) {
        for (int toPosition = Positions.size() - 1; toPosition >= 0; toPosition--) {
            final Position Position = Positions.get(toPosition);
            final int fromPosition = positions.indexOf(Position);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public Position removeItem(int position) {
        final Position p = positions.remove(position);
        notifyItemRemoved(position);
        return p;
    }


    public void addItem(int position, Position p) {
        positions.add(position, p);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Position p = positions.remove(fromPosition);
        positions.add(toPosition,p);
        notifyItemMoved(fromPosition, toPosition);
    }




}
