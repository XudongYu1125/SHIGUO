package com.example.user.interview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.stx.xhb.xbanner.XBanner;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.interview.Constant.ip;
import static com.example.user.interview.Fragment2.addItemJobSearch;
import static com.example.user.interview.Fragment2.addItemUser;


/**
 * @author june
 */
public class InterviewFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private View mView;
    private ViewPager viewPager;
    private FloatingActionMenu actionMenu;
    private FloatingActionButton actionButton;
    private SubActionButton btnJob;
    private SubActionButton btnRecruit;
    private static List <Position> positionList;
    private static List <JobSearch> jobSearchList;
    private static List companyName;
    private static List companyImg;
    private static List userName;
    private static List userImg;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;


    /**
     * 存放选项卡标题
     */
    private String[] tabTitles = new String[]{
            "职位介绍" ,
            "求职广场"
    };


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        mView = inflater.inflate (R.layout.fragment_interview , null);
        gson = new Gson ( );
        sharedPreferences = getActivity ( ).getSharedPreferences ("loginInfo" , MODE_PRIVATE);
        user = gson.fromJson (sharedPreferences.getString ("user" , null) , User.class);
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


    /**
     * 轮播
     */
    public void setView() {
        List images = new ArrayList ( );
        images.add ("https://icweiliimg9.pstatp.com/weili/l/540018848208913173.webp");
        images.add ("https://image.shutterstock.com/z/stock-photo-female-human-hand-and-robot-s-as-a-symbol-of-connection-between-people-and-artificial-intelligence-695563669.jpg");
        images.add ("https://image.shutterstock.com/z/stock-photo-robots-in-love-funny-man-mechanism-with-monitor-head-love-heart-abstract-message-on-blue-screen-535550908.jpg");
        XBanner xBanner = mView.findViewById (R.id.xbanner);
        xBanner.setData (images , null);
        xBanner.loadImage (( banner , model , view , position ) -> Glide.with (getContext ( )).load (images.get (position)).into ((ImageView) view));
        xBanner.setOnItemClickListener (( banner , model , view , position ) -> {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent (getActivity ( ) , WebActivity1.class);
                    startActivity (intent);
                    break;
                case 1:
                    intent = new Intent (getActivity ( ) , WebActivity2.class);
                    startActivity (intent);
                    break;
                case 2:
                    intent = new Intent (getActivity ( ) , WebActivity3.class);
                    startActivity (intent);
                    break;
                default:
                    break;
            }
        });

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
        btnRecruit.setOnClickListener (v -> {
            if (user.getCompanyid ( ) != 0) {
                Intent intent = new Intent (getActivity ( ) , AddNewPosition.class);
                startActivityForResult (intent , 0);
            } else {
                Toast.makeText (getContext ( ) , "您不是认证用户，请前往个人中心进行面试官验证" , Toast.LENGTH_LONG).show ( );
            }
        });
//      求职按钮点击事件
        btnJob.setOnClickListener (v -> {
            if (user.getCompanyid ( ) == 0) {
                Intent intent = new Intent (getActivity ( ) , AddNewApply.class);
                startActivityForResult (intent , 1);
            } else {
                Toast.makeText (getContext ( ) , "您是面试官认证用户，不能进行求职操作！" , Toast.LENGTH_LONG).show ( );
            }
        });

    }

    @Override
    public void onActivityResult( int requestCode , int resultCode , Intent data ) {
        switch (resultCode) {
            case 0:
                String str = data.getStringExtra ("发布新的职位");
                Position p = gson.fromJson (str , Position.class);
                boolean status = data.getBooleanExtra ("状态" , false);
                if (status) {
                    initTab ( );
                }
                break;
            case 1:
                String s = data.getStringExtra ("发布新的求职");

                boolean stutas = data.getBooleanExtra ("状态" , false);
                if (stutas) {
                    initTab ( );
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
        ImageView imageView = mView.findViewById (R.id.roundImg);
        RequestOptions requestOptions = new RequestOptions ( ).circleCrop ( );
        Glide.with (Objects.requireNonNull (getContext ( )))
                .load ("http://" + ip + ":8080/ShiguoServerSystem/avatarimg/" + user.getImageurl ( ))
                .placeholder (R.drawable.unlogin)
                .error (R.drawable.error)
                .apply (requestOptions)
                .into (imageView);
    }


    //搜索框点击事件
    public void onClickSearchImg() {
        ImageView imageView = mView.findViewById (R.id.search_img);
        imageView.setOnClickListener (v -> {

            Intent intent = new Intent (getActivity ( ) , SearchActivity.class);
            startActivity (intent);
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

    public static List getCompanyName() {
        return companyName;
    }

    public static List getCompanyImg() {
        return companyImg;
    }

    public static void setCompanyName( List list ) {
        companyName = list;
    }

    public static void setCompanyImg( List list ) {
        companyImg = list;
    }

    public static List getUserName() {
        return userName;
    }

    public static List getUserImg() {
        return userImg;
    }

    public static void setUserName( List list ) {
        userName = list;
    }

    public static void setUserImg( List list ) {
        userImg = list;
    }


    private void addNewPosition( Position p ) {

        new Thread (() -> {

            String userid = String.valueOf (user.getUserid ( ));
            String position = gson.toJson (p);

            String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Company/SearchListByUserId";
            try {
                URL url = new URL (urlStrPosition);
                //获得连接

                HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                conn.setRequestMethod ("POST");

                conn.setDoOutput (true);

                conn.setDoInput (true);

                conn.connect ( );

                conn.getOutputStream ( ).write (userid.getBytes ("UTF-8"));
                conn.getOutputStream ( ).write (position.getBytes ("UTF-8"));
                conn.getOutputStream ( ).flush ( );


                conn.disconnect ( );
                Message message = new Message ( );
                message.what = 1;
                handler.sendMessage (message);


            } catch (Exception e) {
                e.printStackTrace ( );
            }


        }).start ( );


    }

    private Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                default:
                    break;
            }
        }
    };


}




