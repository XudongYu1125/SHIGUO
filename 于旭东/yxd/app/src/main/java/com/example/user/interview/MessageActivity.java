package com.example.user.interview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import static android.content.Context.MODE_PRIVATE;

public class MessageActivity extends Fragment {


    private SmartRefreshLayout refreshLayout;
    private Timer timer;
    private String rename;
    private ListView messlv;
    private List<Conversations> mData = new ArrayList<>();
    private MessageAdapter myAdapter = null;
    private List<String> username;
    private List<String> userurl;
    private List<Integer> userid;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private int id1 = 1;
    private ImageView imgview;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myAdapter = new MessageAdapter(getActivity(), mData, R.layout.message_list, username);
                    myAdapter.Sort();
                    messlv.setAdapter(myAdapter);
                    break;
                case 1:
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        Log.e("tip","进入消息页面");
        Classinit();
        searchUsername();

        messlv = (ListView) view.findViewById(R.id.lv_message);
        refreshLayout = view.findViewById(R.id.smart_layout);

        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);

        //点击进入ChatRecordActivity
        messlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), "第" + position + "个item", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent();
                it2.setClass(getActivity(), ChatRecordActivity.class);
                it2.putExtra("UUID", mData.get(position).getUUID());
                it2.putExtra("ownersId", mData.get(position).getOwnersId());
                it2.putExtra("classname", getClass().getSimpleName());
                Log.e("own的id", (mData.get(position)).toString());
                startActivityForResult(it2, 0);
            }
        });
        setRefresh();


//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Classinit();
//                searchUsername();
//            }
//        }, 0, 5000);


        return view;
    }

    // 从服务器读取Conversation数据,初始化数据
    private void Classinit() {


        new Thread(new Runnable() {

            @Override
            public void run() {
                Conversations con = new Conversations();
                JSONObject object = new JSONObject();
                String str = "";
                String[] arr;
                try {
                    object.put("userid", user.getUserid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    URL url = new URL(Constant.URL_MESSAGE_LOCAL+"SearchByUser");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    //接收Conversation类列表
                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }

                    JSONObject response = new JSONObject(stringBuffer.toString());
                    mData.clear();
                    JSONArray jsonArray = response.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        con = gson.fromJson(jsonArray.get(i).toString(), Conversations.class);

                        str = mData.get(i).getOwnersId();
                        arr = str.split(";");

                        if (String.valueOf(user.getUserid()).equals(arr[0])) {
                            id1 = Integer.parseInt(arr[1]);
                        } else {
                            id1 = Integer.parseInt(arr[0]);
                        }

                        userid.add(id1);
                        mData.add(con);
                        Log.e("userid", String.valueOf(id1));
                        Log.e("123", con.toString());
                    }

                    inputStream.close();//关闭数据流
                    conn.disconnect();//断开连接

                    //向Handler传递message,处理ui
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //刷新
    public void flush() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new ArrayList<>();
                String urlStr = "http://10.7.88.185:8080/ShiguoServerSystem/ChatRecord/SearchById";
                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }

                    JSONObject response = new JSONObject(stringBuffer.toString());
                    List<Conversations> list = (List<Conversations>) response.get("list");

                    inputStream.close();//关闭数据流
                    conn.disconnect();
                    mData = list;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ;
            }
        }).start();

    }

    //获取ChatRecordActivity回传的值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("接收返回", "456");
        switch (resultCode) {
            case 1:
                String newcontent = data.getStringExtra("content");
                String newdate = data.getStringExtra("date");
                int id = data.getIntExtra("UUID", 123);
                Log.e("cr返回的数据", newcontent);
                //修改最近日期
                myAdapter.editItem(id, newcontent, newdate);
                myAdapter.Sort();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //上拉刷新
    private void setRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                Classinit();
            }
        });
    }

    private void searchUsername() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                User user2 = null;
                try {
                    String result = null;
                    URL url = new URL(Constant.URL_LOCAL+"SearchListByUserId");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(gson.toJson(userid).getBytes());
                    conn.getOutputStream().flush();

                    InputStream is = conn.getInputStream();
                    byte[] btr = new byte[1024];
                    int len;

                    while ((len = is.read(btr)) != -1) {
                        result = new String(btr, 0, len);
                    }
                    List<User> userList = gson.fromJson(result,new TypeToken<List<User>>(){}.getType());
                    for (int i = 0;i<userList.size();i++){
                        username.add(userList.get(i).getNickname());
                        userid.add(userList.get(i).getUserid());
                    }
                    //向Handler传递message,处理ui
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);

                    is.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
