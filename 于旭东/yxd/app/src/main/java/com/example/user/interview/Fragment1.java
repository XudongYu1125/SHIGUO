package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.JsonArray;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.interview.InterviewActivity.setPositionList;

/**
 * @author june
 */
public class Fragment1 extends Fragment {


    private View f1View;
    private ArrayList <Position> positions = new ArrayList <Position> ( );
    private ArrayList<User> users = new ArrayList <> ();
    private ArrayList<Company> companies = new ArrayList <> ();
    private static PositionRecyclerAdapter positionRecyclerAdapter;
    private String ip = "10.7.88.185";
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private User user;
    private List userId;
    private List companyId;
    private List companyName;
    private ArrayList<Position> listA = new ArrayList <> ();

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        f1View = inflater.inflate (R.layout.f1 , null);
//        initPosition ( );
        testData ();
        return f1View;

    }


    private void initData(){
        Position p1 = new Position ();
        p1.setName ("111");
        p1.setPlace ("000");
        p1.setDate ("12-11");
        Position p2 = new Position ();
        p2.setName ("222");
        p2.setPlace ("000");
        p2.setDate ("12-12");
        Position p3 = new Position ();
        p3.setName ("333");
        p3.setPlace ("000");
        p3.setDate ("12-13");
        positions.add (p1);
        positions.add (p2);
        positions.add (p3);
        SortPosition sortPosition = new SortPosition ();
        Collections.sort (positions,sortPosition);
        for (int i =0;i<positions.size ();i++){

            Position p = positions.get (i);
            listA.add (p);


        }
        setPositionList (listA);
    }


    /**
     * 数据测试
     * */
    private void testData(){

        initData ();
        initRecyclerView ();
    }






    /**
     * 对数据初始化
     */
    private void initPosition() {
//
        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                positions = new ArrayList <> ( );

                Gson gson = new Gson ( );


                JSONObject object = new JSONObject();
                for (int i=0;i<positions.size ();i++){
                    try {
                        object.put ("",positions.get (i).getUserid ());
                    } catch (JSONException e) {
                        e.printStackTrace ( );
                    }
                }


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Position/SearchAll";
                String urlStrUser = "http://" + ip + ":8080/ShiguoServerSystem/Resume/SearchByUserId";
                String urlStrCompany = "http://" + ip + ":8080/ShiguoServerSystem/Company/SearchByCompanyId";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

//                    OutputStream outputStream = ;
                    InputStream inputStream = conn.getInputStream ( );
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer ( );
                    while ((len = inputStream.read (buffer)) != -1) {
                        stringBuffer.append (new String (buffer , 0 , len));
                    }
                    JSONObject response = new JSONObject (stringBuffer.toString ( ));

                    Log.e ("11" , response.toString ( ));

                    JSONArray jsonArray = response.getJSONArray ("positionList");
                    if (jsonArray == null){
                        initData ();
                        initRecyclerView ();

                    }else {
                        for (int i = 0; i < jsonArray.length ( ); i++) {
                            Position cr = new Position ( );
                            cr = gson.fromJson (jsonArray.get (i).toString ( ) , Position.class);
                            positions.add (cr);
                        }
                    }
                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    setPositionList (positions);
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
                    initRecyclerView ( );
                    break;
                case 1:
                case 2:
                    break;
            }
        }
    };


    /**
     * 对recyclerView初始化
     */
    private void initRecyclerView() {
        final XRecyclerView recyclerView = f1View.findViewById (R.id.rv1);
        recyclerView.setLoadingListener (new XRecyclerView.LoadingListener ( ) {
            @Override
            public void onRefresh() {
                recyclerView.getDefaultRefreshHeaderView ().setRefreshTimeVisible (true);
                Log.e ("1",positions.get (0).toString ());
                recyclerView.refreshComplete ();
            }

            @Override
            public void onLoadMore() {
                Log.e ("2","我加载更多了");
                Position p = new Position ();
                p.setName ("sss");
                p.setPlace ("sdad");
                p.setDate ("2321");
                positions.add (p);
                recyclerView.loadMoreComplete ();
            }
        });
        positionRecyclerAdapter = new PositionRecyclerAdapter (getContext ( ) , listA);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( ) , LinearLayout.VERTICAL , false));
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity ( ) , DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter (positionRecyclerAdapter);
        positionRecyclerAdapter.setOnItemClickListener (new PositionRecyclerAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick( View view , Position data ) {

                Intent intent = new Intent (getActivity (),PositionInfo.class);
                intent.putExtra ("职位信息",data);
                startActivity (intent);



            }
        });
    }


    public static void addItemPosition( Position position){
        positionRecyclerAdapter.addItem (position);
    }



}
