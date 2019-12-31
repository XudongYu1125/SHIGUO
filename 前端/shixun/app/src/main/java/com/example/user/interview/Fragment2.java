package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static com.example.user.interview.InterviewFragment.setJobSearchList;
import static com.example.user.interview.InterviewFragment.setUserImg;
import static com.example.user.interview.InterviewFragment.setUserName;

public class Fragment2 extends Fragment {

    private String ip = Constant.ip;
    private View f2View;
    private ArrayList <JobSearch> jobSearches = new ArrayList <JobSearch> ( );
    private static JobSearchRecyclerAdapter jobSearchRecyclerAdapter;
    private ArrayList <JobSearch> listA = new ArrayList <> ( );
    private List userName = new ArrayList ( );
    private List userImg = new ArrayList ( );
    private List userId;
    private String time = "";

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        jobSearches = new ArrayList <> ();
        listA = new ArrayList <> ();
        userName = new ArrayList ();
        userImg = new ArrayList ();
        userId = new ArrayList ();
        f2View = inflater.inflate (R.layout.f2 , null);
        initJobSearch ( );
        // testData ();
        return f2View;

    }


    private void initData() {


        JobSearch j1 = new JobSearch ( );
        j1.setDate ("11-12");
        j1.setPosition ("dsa");
        jobSearches.add (j1);
        JobSearch j2 = new JobSearch ( );
        j2.setDate ("11-13");
        j2.setPosition ("ads");
        jobSearches.add (j2);
        SortJobSearch sortJobSearch = new SortJobSearch ( );
        Collections.sort (jobSearches , sortJobSearch);
        for (int i = 0; i < jobSearches.size ( ); i++) {

            JobSearch j = jobSearches.get (i);
            listA.add (j);


        }
        setJobSearchList (listA);
    }


    /**
     * 测试数据
     */
    private void testData() {

        initData ( );
        initRecyclerView ( );

    }


    //对数据初始化
    private void initJobSearch() {
        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                jobSearches = new ArrayList <> ( );

                Gson gson = new Gson ( );

                String urlStr = "http://" + ip + ":8080/ShiguoServerSystem/JobSearch/SearchAll";

                try {
                    URL url = new URL (urlStr);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    InputStream inputStream = conn.getInputStream ( );
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer ( );
                    while ((len = inputStream.read (buffer)) != -1) {
                        stringBuffer.append (new String (buffer , 0 , len));
                    }
                    JSONObject response = new JSONObject (stringBuffer.toString ( ));
                    JSONArray jsonArray = response.getJSONArray ("jobsearchList");
                    if (jsonArray == null) {
                        initData ( );
                        initRecyclerView ( );

                    } else {
                        for (int i = 0; i < jsonArray.length ( ); i++) {
                            JobSearch cr;
                            cr = gson.fromJson (jsonArray.get (i).toString ( ) , JobSearch.class);
                            jobSearches.add (cr);
                        }
                    }

                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    SortJobSearch sortJobSearch = new SortJobSearch ( );
                    Collections.sort (jobSearches , sortJobSearch);
                    for (int i = 0; i < jobSearches.size ( ); i++) {

                        JobSearch j = jobSearches.get (i);
                        listA.add (j);
                    }
                    jobSearches = listA;
                    Log.e ("jb" , jobSearches.toString ( ));
                    setJobSearchList (jobSearches);
                    Message message = new Message ( );
                    message.what = 0;
                    handler.sendMessage (message);

                } catch (Exception e) {
                    e.printStackTrace ( );
                }
            }
        }).start ( );

    }


    private void initCompany() {
        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                userId = new ArrayList <Integer> ( );

                for (int i = 0; i < jobSearches.size ( ); i++) {
                    userId.add (jobSearches.get (i).getUserid ( ));
                }
                Log.e ("userId" , userId.toString ( ));
                Gson gson = new Gson ( );
                String userList = gson.toJson (userId);


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/User/SearchByUserId";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    conn.getOutputStream ( ).write (userList.getBytes ("UTF-8"));
                    conn.getOutputStream ( ).flush ( );


                    InputStream inputStream = conn.getInputStream ( );
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer ( );
                    while ((len = inputStream.read (buffer)) != -1) {
                        stringBuffer.append (new String (buffer , 0 , len));
                    }

                    Type type = new TypeToken <List <String[]>> ( ) {
                    }.getType ( );
                    List <String[]> list = gson.fromJson (stringBuffer.toString ( ) , type);
                    for (int i = 0; i < list.size ( ); i++) {
                        userName.add (list.get (i)[0]);
                        userImg.add (list.get (i)[1]);
                    }
                    Log.e ("1231" , userName.toString ( ));
                    Log.e ("1233" , userImg.toString ( ));
                    setUserName (userName);
                    setUserImg (userImg);

                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    Message message = new Message ( );
                    message.what = 1;
                    handler.sendMessage (message);


                } catch (Exception e) {
                    e.printStackTrace ( );
                }


            }
        }).start ( );
    }


    private Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                case 0:
                    initCompany ( );
                    break;
                case 1:
                    initRecyclerView ( );
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date (System.currentTimeMillis ( ));
                    time = simpleDateFormat.format (date);
                    break;
                case 2:
                    List <JobSearch> list = (List <JobSearch>) msg.getData ( ).getSerializable ("list");
                    if (list != null){
                        SortPosition sortPosition = new SortPosition ( );
                        Collections.sort (list , sortPosition);
                        for (int i = 0; i < list.size ( ); i++) {

                            JobSearch j = list.get (i);
                            listA.add (j);
                        }
                        list = listA;
                        jobSearchRecyclerAdapter.reflashItem (list);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                        Date date1 = new Date (System.currentTimeMillis ( ));
                        time = simpleDateFormat1.format (date1);
                    }
                    break;
            }
        }
    };

    //对recyclerView初始化
    private void initRecyclerView() {

        final XRecyclerView recyclerView = f2View.findViewById (R.id.rv2);
        recyclerView.getDefaultRefreshHeaderView ( ).setRefreshTimeVisible (true);
        recyclerView.setLoadingListener (new XRecyclerView.LoadingListener ( ) {
            @Override
            public void onRefresh() {
                countJobSearchTime ( );
                recyclerView.refreshComplete ( );
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete ( );
            }
        });

        jobSearchRecyclerAdapter = new JobSearchRecyclerAdapter (getActivity ( ) , jobSearches , userName , userImg);
        recyclerView.setAdapter (jobSearchRecyclerAdapter);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( ) , LinearLayout.VERTICAL , false));
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity ( ) , DividerItemDecoration.VERTICAL));
        jobSearchRecyclerAdapter.setOnItemClickListener (( view , data , user ) -> {

            Intent intent = new Intent (getActivity ( ) , ApplyPositionUserInfo.class);
            Gson gson = new Gson ( );
            intent.putExtra ("求职信息" , gson.toJson (data));
            intent.putExtra ("用户信息",gson.toJson (user));
            startActivity (intent);

        });
    }

    public static void addItemJobSearch( JobSearch jb ) {

        jobSearchRecyclerAdapter.addItemJobSearch (jb);

    }

    public static void addItemUser(User u){
        jobSearchRecyclerAdapter.addItemUser (u);
    }



    private void countJobSearchTime() {

        new Thread (new Runnable ( ) {
            @Override
            public void run() {

                Gson gson = new Gson ( );


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/JobSearch/Reflash";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    conn.getOutputStream ( ).write (time.getBytes ("UTF-8"));
                    conn.getOutputStream ( ).flush ( );


                    InputStream inputStream = conn.getInputStream ( );
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer ( );
                    while ((len = inputStream.read (buffer)) != -1) {
                        stringBuffer.append (new String (buffer , 0 , len));
                    }

                    Type type = new TypeToken <List <Position>> ( ) {
                    }.getType ( );
                    List <JobSearch> list = gson.fromJson (stringBuffer.toString ( ) , type);


                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    Bundle bundle = new Bundle ( );
                    Message message = new Message ( );
                    bundle.putSerializable ("list" , (Serializable) list);
                    message.setData (bundle);
                    message.what = 2;
                    handler.sendMessage (message);


                } catch (Exception e) {
                    e.printStackTrace ( );
                }


            }
        }).start ( );
    }


}

