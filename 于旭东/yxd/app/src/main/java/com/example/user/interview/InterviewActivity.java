package com.example.user.interview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.interview.Fragment1.addItemPosition;
import static com.example.user.interview.Fragment2.addItemJobSearch;


/**
 * @author june
 */
public class InterviewActivity extends Fragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private View mView;
    private ViewPager mViewPaper;
    private ViewPager viewPager;
    private List <ImageView> images;
    private List <View> dots;
    private int currentItem;
    private int oldPosition = 0;
    private FloatingActionMenu actionMenu;
    private FloatingActionButton actionButton;
    private SubActionButton btnJob;
    private SubActionButton btnRecruit;
    private static List <Position> positionList;
    private static List <JobSearch> jobSearchList;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;

    /**
     * 存放banner图片的ID集合
     */
    private int[] imageIds = new int[]{
            R.drawable.banner1 ,
            R.drawable.banner2 ,
            R.drawable.banner3 ,
            R.drawable.banner4 ,
            R.drawable.banner5
    };

    /**
     * 存放选项卡标题
     */
    private String[] tabTitles = new String[]{
            "职位介绍" ,
            "求职广场"
    };


    /**
     * 存放banner标题
     */
    private String[] imageTitles = new String[]{
            "真题模拟" ,
            "应试教程" ,
            "提分测试" ,
            "面试技巧" ,
            "真题详解"
    };
    private TextView title;
    private ScheduledExecutorService scheduledExecutorService;


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        mView = inflater.inflate (R.layout.fragment_interview,null);
        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        //开启轮播图
        setView ( );
        //初始化tab
        initTab ( );
        //悬浮菜单
        floatingMenu ( );
        //圆形头像
        setRoundImg ( );
        //搜索框点击事件
        onClickSearchImg ( );
        return mView;

    }


    /*
      156---308均为轮播图设计代码，运用线程进行操作
     */

    /**
     * 自定义轮播图setView方法
     */

    private void setView() {
        mViewPaper = mView.findViewById (R.id.vp);

     /*
        显示的图片
     */
        images = new ArrayList <> ( );
        for (int imageId : imageIds) {
            ImageView imageView = new ImageView (getActivity ( ));
            imageView.setBackgroundResource (imageId);
            images.add (imageView);
        }

     /*
        显示的小点
     */
        dots = new ArrayList <> ( );
        dots.add (mView.findViewById (R.id.dot_0));
        dots.add (mView.findViewById (R.id.dot_1));
        dots.add (mView.findViewById (R.id.dot_2));
        dots.add (mView.findViewById (R.id.dot_3));
        dots.add (mView.findViewById (R.id.dot_4));

        title = mView.findViewById (R.id.title);
        title.setText (imageTitles[0]);

        ViewPagerAdapter adapter = new ViewPagerAdapter ( );
        mViewPaper.setAdapter (adapter);
        mViewPaper.setOnPageChangeListener (new ViewPager.OnPageChangeListener ( ) {
            @Override
            public void onPageScrolled( int i , float v , int i1 ) {

            }

            @Override
            public void onPageSelected( int position ) {
                title.setText (imageTitles[position]);
                dots.get (position).setBackgroundResource (R.drawable.dot_yes);
                dots.get (oldPosition).setBackgroundResource (R.drawable.dot_no);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged( int i ) {

            }
        });
    }

    @Override
    public void onPageScrolled( int i , float v , int i1 ) {

    }

    @Override
    public void onPageSelected( int i ) {
        if (i == 1) {
            actionButton.setVisibility (View.VISIBLE);
            btnJob.setVisibility (View.VISIBLE);
            btnRecruit.setVisibility (View.VISIBLE);
        } else {
            actionButton.setVisibility (View.GONE);
            btnJob.setVisibility (View.GONE);
            btnRecruit.setVisibility (View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged( int i ) {

    }


    /**
     * 自定义Adapter适配器类
     */


    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size ( );
        }

        @Override
        public boolean isViewFromObject( @NonNull View arg0 , @NonNull Object arg1 ) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem( ViewGroup view , int position , @NonNull Object object ) {
            // TODO Auto-generated method stub
            view.removeView (images.get (position));
        }

        @NonNull
        @Override
        public Object instantiateItem( ViewGroup view , int position ) {
            // TODO Auto-generated method stub
            view.addView (images.get (position));
            return images.get (position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart ( );
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor ( );
        scheduledExecutorService.scheduleWithFixedDelay (
                new ViewPageTask ( ) ,
                2 ,
                2 ,
                TimeUnit.SECONDS);
    }

    /**
     * 接收子线程传递过来的数据
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler ( ) {
        @Override
        public void handleMessage( android.os.Message msg ) {
            mViewPaper.setCurrentItem (currentItem);
        }
    };

    /**
     * 图片轮播任务
     */
    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage (0);
        }
    }


    @Override
    public void onStop() {
        super.onStop ( );
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown ( );
            scheduledExecutorService = null;
        }
    }


    /**
     * 272----324为悬浮菜单函数
     */
    public void floatingMenu() {
//      菜单悬浮
        ImageView icon = new ImageView (getActivity ( ));
        icon.setImageDrawable (getResources ( ).getDrawable (R.drawable.add));
        actionButton = new FloatingActionButton
                .Builder (Objects.requireNonNull (this.getActivity ( )))
                .setContentView (icon)
                .setPosition (FloatingActionButton.POSITION_RIGHT_CENTER)
                .setTheme (FloatingActionButton.THEME_LIGHT)
                .build ( );

//      设计主菜单
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder (getActivity ( ));

//      添加第一个子菜单
        ImageView itemIconApplyJob = new ImageView (getActivity ( ));
        itemIconApplyJob.setImageDrawable (getResources ( ).getDrawable (R.drawable.qiu));
        btnJob = itemBuilder.setContentView (itemIconApplyJob).build ( );


//      添加第二个子菜单
        ImageView itemIconRecruit = new ImageView (getActivity ( ));
        itemIconRecruit.setImageDrawable (getResources ( ).getDrawable (R.drawable.pin));
        btnRecruit = itemBuilder.setContentView (itemIconRecruit).build ( );
//      将子菜单与主菜单拼接
        actionMenu = new FloatingActionMenu.Builder (this.getActivity ( ))
                .addSubActionView (btnJob)
                .addSubActionView (btnRecruit)
                .attachTo (actionButton)
                .build ( );
//      打开的动画
        actionMenu.setStateChangeListener (new FloatingActionMenu.MenuStateChangeListener ( ) {
            @Override
            public void onMenuOpened( FloatingActionMenu floatingActionMenu ) {
                actionButton.setRotation (0);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat (View.ROTATION , 45);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder (actionButton , pvhr);
                animator.start ( );
            }

            //      关闭的动画
            @Override
            public void onMenuClosed( FloatingActionMenu floatingActionMenu ) {
                actionButton.setRotation (45);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat (View.ROTATION , 0);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder (actionButton , pvhr);
                animator.start ( );
            }
        });

//      招聘按钮点击事件
        btnRecruit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (getActivity ( ) , AddNewPosition.class);
                startActivityForResult (intent , 0);
            }
        });
//      求职按钮点击事件
        btnJob.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (getActivity ( ) , AddNewApply.class);
                startActivityForResult (intent , 1);
            }
        });

    }

    @Override
    public void onActivityResult( int requestCode , int resultCode , Intent data ) {
        switch (resultCode) {
            case 0:
                Position p = data.getParcelableExtra ("发布新的职位");
                boolean status = data.getBooleanExtra ("状态" , false);
                if (status) {
                    addItemPosition (p);
                   // writeBack (p);
                }
                break;
            case 1:
                JobSearch jobSearch = data.getParcelableExtra ("发布新的求职");
                boolean stutas = data.getBooleanExtra ("状态" , false);
                if (stutas) {
                    addItemJobSearch (jobSearch);
                }
                break;
            default:
                break;
        }
        super.onActivityResult (requestCode , resultCode , data);
    }

    @Override
    public void onPause() {
        super.onPause ( );
        actionButton.setVisibility (View.GONE);
        btnJob.setVisibility (View.GONE);
        btnRecruit.setVisibility (View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ( );
        actionButton.setVisibility (View.GONE);
        btnJob.setVisibility (View.GONE);
        btnRecruit.setVisibility (View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume ( );
        actionButton.setVisibility (View.VISIBLE);
        btnJob.setVisibility (View.VISIBLE);
        btnRecruit.setVisibility (View.VISIBLE);

    }

    /**
     * 选项卡页面实现
     */
    public void initTab() {
        List <Fragment> fragments = new ArrayList <> ( );
        TabLayout tabLayout = mView.findViewById (R.id.tabLayout);
        viewPager = mView.findViewById (R.id.viewpaper);

        for (String tab : tabTitles) {
            tabLayout.addTab (tabLayout.newTab ( ).setText (tab));
        }

        tabLayout.setOnTabSelectedListener (this);
        fragments.add (new Fragment1 ( ));
        fragments.add (new Fragment2 ( ));
        FragmentAdapter fragmentAdapter = new FragmentAdapter (getChildFragmentManager ( ) , fragments , tabTitles);
        viewPager.setAdapter (fragmentAdapter);
        tabLayout.setupWithViewPager (viewPager);
    }

    @Override
    public void onTabSelected( TabLayout.Tab tab ) {
        viewPager.setCurrentItem (tab.getPosition ( ));
    }

    @Override
    public void onTabUnselected( TabLayout.Tab tab ) {

    }

    @Override
    public void onTabReselected( TabLayout.Tab tab ) {

    }


    /**
     * 头像
     */
    private void setRoundImg() {
//        String uri = user.getImageurl ();
        String uri = "http://img4.imgtn.bdimg.com/it/u=1796720027,3489106365&fm=26&gp=0.jpg";
        ImageView imageView = mView.findViewById (R.id.roundImg);
        Glide.with (Objects.requireNonNull (getContext ( ))).load (uri).placeholder (R.drawable.unlogin).error (R.drawable.error).into (imageView);

//
//        ImageView imageView = mView.findViewById (R.id.roundImg);
//        imageView.setImageResource (R.drawable.add);
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                //点击头像进入个人中心
                Intent intent = new Intent (getActivity ( ) , MainActivity.class);
                startActivity (intent);
            }
        });
    }


    //搜索框点击事件
    public void onClickSearchImg() {
        ImageView imageView = mView.findViewById (R.id.search_img);
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Intent intent = new Intent (getActivity ( ) , SearchActivity.class);
                startActivity (intent);
            }
        });
    }


    public static void setPositionList( List <Position> list ) {
        positionList = list;
    }


    public static void setJobSearchList( List <JobSearch> list ) {
        jobSearchList = list;
    }

    public static List <Position> getPositionList() {
        return positionList;
    }

    public static List <JobSearch> getJobSearchList() {
        return jobSearchList;
    }




    private void writeBack( final Position p){

        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                Gson gson = new Gson ( );
                String ip = "";
                JSONObject object = new JSONObject();
                try {
                    object.put ("addNewPosition",p);
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
                String urlAddStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Position/SearchAll";

                try {
                    URL url = new URL (urlAddStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();



                    conn.disconnect ( );

                    Message message = new Message ( );
                    message.what = 0;
                    handler.sendMessage (message);


                } catch (Exception e) {
                    e.printStackTrace ( );
                }


            }
        }).start ( );

    }



    private  Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                case 0:
                    Log.e ("11","ok");
                    break;
                case 1:
                case 2:
                    break;
            }
        }
    };





}




