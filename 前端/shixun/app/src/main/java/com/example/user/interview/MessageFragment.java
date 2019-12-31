package com.example.user.interview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import static android.content.Context.MODE_PRIVATE;

public class MessageFragment extends Fragment {

    private boolean stopMe = true;
    private SmartRefreshLayout refreshLayout;
    private ListView messlv;
    private Conversationoo conoo = new Conversationoo();
    private List<Conversationoo> mData = new ArrayList<>();
    private MessageAdapter myAdapter = null;
    private List<String> username = new ArrayList<>();
    private List<String> userurl = new ArrayList<>();
    private android.widget.SearchView searchView;
    private List<Integer> userid = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private int id1 = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myAdapter = new MessageAdapter(getActivity(), mData, R.layout.message_list);
                    myAdapter.Sort();
                    messlv.setAdapter(myAdapter);
                    messlv.setTextFilterEnabled(true);
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
        Log.e("tip", "进入消息页面");

        messlv = (ListView) view.findViewById(R.id.lv_message);
        refreshLayout = view.findViewById(R.id.smart_layout);


        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        Log.e("user的那么", user.getUserid() + "");
        userid.clear();
        mData.clear();
        username.clear();
        userurl.clear();

        Init init = new Init();
        init.execute("");


        //点击进入ChatRecordActivity
        messlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), "第" + position + "个item", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent();
                it2.setClass(getActivity(), ChatRecordActivity.class);
                it2.putExtra("UUID", mData.get(position).getCon().getUUID());
                it2.putExtra("ownersId", mData.get(position).getCon().getOwnersId());
                it2.putExtra("classname", getClass().getSimpleName());
                Log.e("own的id", (mData.get(position)).toString());
                startActivityForResult(it2, 0);
            }
        });
        setRefresh();

        return view;
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
                Init init = new Init();
                init.execute("");
            }
        });
    }


    public class Init extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

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
                URL url = new URL(Constant.URL_MESSAGE_LOCAL + "SearchByUser");
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
                if (jsonArray.length() == 0) {
                    return "";

                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        con = gson.fromJson(jsonArray.get(i).toString(), Conversations.class);

                        conoo = new Conversationoo();

                        str = con.getOwnersId();
                        arr = str.split(";");

                        if (String.valueOf(user.getUserid()).equals(arr[0])) {
                            id1 = Integer.parseInt(arr[1]);
                        } else {
                            id1 = Integer.parseInt(arr[0]);
                        }
                        conoo.setCon(con);
                        Log.e("145", conoo.getCon().getContent());
                        userid.add(id1);
                        mData.add(conoo);
                        Log.e("userid", String.valueOf(id1));
                        Log.e("123", con.toString());
                    }
                }

                inputStream.close();//关闭数据流
                conn.disconnect();//断开连接
            } catch (Exception e) {
                e.printStackTrace();
            }
            return gson.toJson(userid);
        }

        @Override
        protected void onPostExecute(String result) {


//            searchUsername();
            if (!result.equals("")) {
                Log.e("111", result);
                SearchUser searchUser = new SearchUser();
                searchUser.execute(result);
            }
        }
    }

    public class SearchUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                URL url = new URL(Constant.URL_LOCAL + "SearchListByUserId");
                //获得连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                conn.getOutputStream().write(strings[0].getBytes());
                conn.getOutputStream().flush();

                InputStream is = conn.getInputStream();
                byte[] btr = new byte[1024];
                int len;

                while ((len = is.read(btr)) != -1) {
                    response = new String(btr, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            userList = gson.fromJson(result, new TypeToken<List<User>>() {
            }.getType());
            for (int i = 0; i < userList.size(); i++) {
                username.add(userList.get(i).getNickname());
                mData.get(i).setName(userList.get(i).getNickname());
                Log.e("名字", userList.get(i).getNickname());
                userurl.add(userList.get(i).getImageurl());
                mData.get(i).setUrl(userList.get(i).getImageurl());
            }
            myAdapter = new MessageAdapter(getActivity(), mData, R.layout.message_list);
            myAdapter.Sort();
            messlv.setAdapter(myAdapter);
        }
    }

}
