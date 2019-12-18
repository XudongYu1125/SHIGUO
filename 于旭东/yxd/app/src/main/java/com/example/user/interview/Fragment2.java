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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.user.interview.InterviewActivity.setJobSearchList;

public class Fragment2 extends Fragment {

    private String ip = "10.7.88.185";
    private View f2View;
    private ArrayList <JobSearch> jobSearches = new ArrayList <JobSearch> ( );
    private static JobSearchRecyclerAdapter jobSearchRecyclerAdapter;
    private ArrayList<JobSearch> listA = new ArrayList <> ();

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        f2View = inflater.inflate (R.layout.f2 , null);
//        initJobSearch ( );
        testData ();
        return f2View;

    }


    private void initData(){


        JobSearch j1 = new JobSearch ();
        j1.setDate ("11-12");
        j1.setPosition ("dsa");
        jobSearches.add (j1);
        JobSearch j2 = new JobSearch ();
        j2.setDate ("11-13");
        j2.setPosition ("ads");
        jobSearches.add (j2);
        SortJobSearch sortJobSearch = new SortJobSearch ();
        Collections.sort (jobSearches,sortJobSearch);
        for (int i =0;i<jobSearches.size ();i++){

            JobSearch j = jobSearches.get (i);
            listA.add (j);


        }
        setJobSearchList (listA);
    }
    
    
    

    /**
     * 测试数据
     * */
    private void testData(){

        initData ();
        initRecyclerView ();

    }
    
    
    
    
    //对数据初始化
    private void initJobSearch() {
        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                jobSearches = new ArrayList <> ( );

                Gson gson = new Gson ( );

                String urlStr = "http://"+ip+":8080/ShiguoServerSystem/Position/SearchAll";

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

                    if (jsonArray == null){
                        initData ();
                        initRecyclerView ();

                    }else {
                        for (int i = 0; i < jsonArray.length ( ); i++) {
                            JobSearch cr;
                            cr = gson.fromJson (jsonArray.get (i).toString ( ) , JobSearch.class);
                            jobSearches.add (cr);
                        }
                    }

                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
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


    private Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                case 0:
                    initRecyclerView ( );
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    };

        //对recyclerView初始化
        private void initRecyclerView() {

            final XRecyclerView recyclerView = f2View.findViewById (R.id.rv2);

            recyclerView.setLoadingListener (new XRecyclerView.LoadingListener ( ) {
                @Override
                public void onRefresh() {
                    recyclerView.getDefaultRefreshHeaderView ().setRefreshTimeVisible (true);
                    recyclerView.refreshComplete ();
                }

                @Override
                public void onLoadMore() {

                    for (int i =0;i<5;i++){

                        JobSearch j = new JobSearch ();
                        j.setPlace ("hh"+i);
                        j.setPosition ("sad"+i);
                        j.setSalary ("ad"+i);
                        j.setRequest ("dadadsadasdad"+i);
                        j.setDate ("12-1"+i);
                        listA.add (j);
                    }

                    recyclerView.loadMoreComplete ();
                }
            });

            jobSearchRecyclerAdapter = new JobSearchRecyclerAdapter (getActivity ( ) , listA);
            recyclerView.setAdapter (jobSearchRecyclerAdapter);
            recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( ) , LinearLayout.VERTICAL , false));
            recyclerView.addItemDecoration (new DividerItemDecoration (getActivity ( ) , DividerItemDecoration.VERTICAL));
            jobSearchRecyclerAdapter.setOnItemClickListener (new JobSearchRecyclerAdapter.OnItemClickListener ( ) {
                @Override
                public void OnItemClick( View view , JobSearch data ) {


                    Intent intent = new Intent (getActivity (), ApplyPositionUserInfo.class);
                    intent.putExtra ("求职信息",data);
                    startActivity (intent);

                }
            });
        }

        public static void addItemJobSearch(JobSearch jb){

            jobSearchRecyclerAdapter.addItemJobSearch (jb);

        }



}
