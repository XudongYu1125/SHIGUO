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

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.interview.InterviewFragment.setCompanyImg;
import static com.example.user.interview.InterviewFragment.setCompanyName;
import static com.example.user.interview.InterviewFragment.setPositionList;

/**
 * @author june
 */
public class Fragment1 extends Fragment {


    private View f1View;
    private ArrayList <Position> positions = new ArrayList <Position> ( );
    private static PositionRecyclerAdapter positionRecyclerAdapter;
    private String ip = Constant.ip;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private User user;
    private List userId;
    private List companyImg = new ArrayList ( );
    private static List companyName = new ArrayList ( );
    private String time = "";
    private ArrayList <Position> listA = new ArrayList <> ( );

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {


        positions = new ArrayList <> ();
        companyImg = new ArrayList ();
        companyName = new ArrayList ();
        listA = new ArrayList <> ();
        userId = new ArrayList ();
        gson = new Gson ( );
        sharedPreferences = getActivity ( ).getSharedPreferences ("loginInfo" , MODE_PRIVATE);
        user = gson.fromJson (sharedPreferences.getString ("user" , null) , User.class);
        f1View = inflater.inflate (R.layout.f1 , null);
        initPosition ( );
        // testData ();
        return f1View;

    }


    private void initData() {
        Position p1 = new Position ( );
        p1.setName ("111");
        p1.setPlace ("000");
        p1.setDate ("12-11");
        Position p2 = new Position ( );
        p2.setName ("222");
        p2.setPlace ("000");
        p2.setDate ("12-12");
        Position p3 = new Position ( );
        p3.setName ("333");
        p3.setPlace ("000");
        p3.setDate ("12-13");
        positions.add (p1);
        positions.add (p2);
        positions.add (p3);
        SortPosition sortPosition = new SortPosition ( );
        Collections.sort (positions , sortPosition);
        for (int i = 0; i < positions.size ( ); i++) {

            Position p = positions.get (i);
            listA.add (p);


        }
        setPositionList (listA);
    }


    /**
     * 数据测试
     */
    private void testData() {

        initData ( );
        initRecyclerView ( );
    }


    /**
     * 对数据初始化
     */
    private void initPosition() {

        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                positions = new ArrayList <> ( );
                Gson gson = new Gson ( );
                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Position/SearchAll";
                try {
                    URL url = new URL (urlStrPosition);
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

                    Log.e ("222" , response.toString ( ));
                    JSONArray jsonArrayPositon = response.getJSONArray ("positionList");
                    if (jsonArrayPositon == null) {
                        initData ( );
                        initRecyclerView ( );

                    } else {
                        for (int i = 0; i < jsonArrayPositon.length ( ); i++) {
                            Position cr = new Position ( );
                            cr = gson.fromJson (jsonArrayPositon.get (i).toString ( ) , Position.class);
                            positions.add (cr);
                        }
                    }

                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    SortPosition sortPosition = new SortPosition ( );
                    Collections.sort (positions , sortPosition);
                    for (int i = 0; i < positions.size ( ); i++) {

                        Position j = positions.get (i);
                        listA.add (j);
                    }
                    positions = listA;
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


    private void initCompany() {
        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                userId = new ArrayList <Integer> ( );

                for (int i = 0; i < positions.size ( ); i++) {
                    userId.add (positions.get (i).getUserid ( ));
                }

                Gson gson = new Gson ( );
                String userList = gson.toJson (userId);


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Company/SearchListByUserId";
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
                        companyName.add (list.get (i)[0]);
                        companyImg.add (list.get (i)[1]);
                    }
//                        Log.e ("1231",companyName.toString ());
//                        Log.e ("1233",companyImg.toString ());
                    setCompanyName (companyName);
                    setCompanyImg (companyImg);

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
                    List <Position> list = (List <Position>) msg.getData ( ).getSerializable ("list");
                    if (list != null){
                        SortPosition sortPosition = new SortPosition ( );
                        Collections.sort (list , sortPosition);
                        for (int i = 0; i < list.size ( ); i++) {

                            Position j = list.get (i);
                            listA.add (j);
                        }
                        list = listA;
                        positionRecyclerAdapter.reflashItem (list);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                        Date date1 = new Date (System.currentTimeMillis ( ));
                        time = simpleDateFormat1.format (date1);
                    }
                    break;
                default:
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

                countPositionTime ();
                recyclerView.refreshComplete ( );
            }

            @Override
            public void onLoadMore() {
                companyImg.add("");
                companyName.add("");
                recyclerView.loadMoreComplete ( );
            }
        });
        positionRecyclerAdapter = new PositionRecyclerAdapter (getContext ( ) , positions , companyName , companyImg);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( ) , LinearLayout.VERTICAL , false));
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity ( ) , DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter (positionRecyclerAdapter);
        positionRecyclerAdapter.setOnItemClickListener (new PositionRecyclerAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick( View view , Position data ) {

                Intent intent = new Intent (getActivity ( ) , PositionInfo.class);
                Gson gson = new Gson ( );
                intent.putExtra ("职位信息" , gson.toJson (data));
                startActivity (intent);


            }
        });
    }


//    public static void addItemPosition( Position position ) {
//        positionRecyclerAdapter.addItem (position);
//    }
//
//    public static void addItemCompanyName(String s ) {
//        positionRecyclerAdapter.addCompanyName(s);
//    }
//
//    public static void addItemCompanyImg(String s){
//        positionRecyclerAdapter.addImgUrl (s);
//    }


    public void countPositionTime() {

        new Thread (new Runnable ( ) {
            @Override
            public void run() {

                Gson gson = new Gson ( );


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Position/Reflash";
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
                    List <Position> list = gson.fromJson (stringBuffer.toString ( ) , type);


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





