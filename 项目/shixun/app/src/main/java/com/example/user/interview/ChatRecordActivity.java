package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatRecordActivity extends AppCompatActivity {

    private Timer timer;
    private boolean stopMe = true;
    private ListView mylistview;
    private List<ChatRecord> mData = new ArrayList<>();
    private ChatRecordAdapter myAdapter = null;
    private int newid = 123; // chatrecord 最新id
    private int recevieid;
    private List<Integer> userid = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<String> userurl = new ArrayList<>();
    private String input;
    private EditText et;
    private int uuid;
    private Gson gson;
    private static User user;
    private SharedPreferences sharedPreferences = null;
    private String imageurl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("213", msg.what + "");
            switch (msg.what) {
                case 0:
                    myAdapter = new ChatRecordAdapter(ChatRecordActivity.this, mData, userurl, R.layout.message_talk);
                    myAdapter.Sort();
                    mylistview.setAdapter(myAdapter);
                    break;
                case 1:
                    Log.e("213", "21");
                    Toast.makeText(ChatRecordActivity.this, "发送失败", Toast.LENGTH_SHORT);
                    break;
                case 2:
                    if (myAdapter == null) {
                        userurl.add(user.getImageurl());
                        myAdapter = new ChatRecordAdapter(ChatRecordActivity.this, mData, userurl, R.layout.message_talk);
                        mylistview.setAdapter(myAdapter);
                    }
                    ChatRecord chatRecord = (ChatRecord) msg.getData().getSerializable("ChatRecord");
                    myAdapter.addItem(chatRecord);
                    et.setText("");
                    break;
                case 3:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_talk);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);

        uuid = getIntent().getIntExtra("UUID", 0);
        String ownersid = getIntent().getStringExtra("ownersId");
        String[] arr = ownersid.split(";");

        if (String.valueOf(user.getUserid()).equals(arr[0])) {
            recevieid = Integer.parseInt(arr[1]);
        } else {
            recevieid = Integer.parseInt(arr[0]);
        }
        Log.e("收到的id", recevieid + "");
        userid.add(user.getUserid());
        userid.add(recevieid);

        if (uuid == 0) {

        } else {
            search();
            Classinit();
        }
        mylistview = findViewById(R.id.con_lv);
        mylistview.setDividerHeight(0);

//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Classinit();
//            }
//        }, 0, 1000);


    }

    //发送消息
    public void send(View view) {
        //发送消息
        Log.e("send", "aa");
        et = findViewById(R.id.con_et);
        input = et.getText().toString();
        if (input.equals("")) {
            Toast.makeText(ChatRecordActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatRecord chatrecord = new ChatRecord();

                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                Date dt = new Date();
                String date = sdf.format(dt.getTime());
                Log.e("时间哦", date);
                chatrecord.setContent(input);
                chatrecord.setDate(date);
                chatrecord.setSenderid(user.getUserid());
                chatrecord.setReceiverid(recevieid);
                chatrecord.setUUID(uuid);

                try {
                    URL url = new URL(Constant.URL_CHAT_LOCAL + "Add");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(gson.toJson(chatrecord).getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());
                    boolean bl = (Boolean) response.get("isSuccess");
                    Log.e("成功", bl + "");
                    if (bl == true) {
                        Log.e("成功", bl + "");
                        if (uuid == 0) {
                            uuid = Integer.parseInt((String) response.get("UUID"));
                            Log.e("uud=的id", "" + uuid);
                            chatrecord.setUUID(uuid);
                        }
                        String strid = (String) response.get("chatRecordId");
                        int crid = Integer.parseInt(strid);
                        chatrecord.setChatrecordid(crid);
                        newid = crid;
                        Log.e("这是新的chatrecordid", String.valueOf(newid));

                        Bundle bundle = new Bundle();
                        Message msg = new Message();
                        msg.what = 2;
                        bundle.putSerializable("ChatRecord", (Serializable) chatrecord);
                        msg.setData(bundle);
                        handler.sendMessage(msg);


                    } else {
                        Log.e("12321321321", "2");
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }

                    inputStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void sendemoji(View view) {
        //表情
    }

    // 查看个人信息
    public void toself(View view) {
        Intent it1 = new Intent();
        it1.setClass(ChatRecordActivity.this, SelfActivity.class);
        // 判断点击的是sender还是receiver 传递id
        switch (view.getId()) {
            case R.id.con_imgi:
                it1.putExtra("userid", user.getUserid());
                break;
            case R.id.con_imgu:
                it1.putExtra("userid", recevieid);
                break;
        }
        startActivity(it1);
    }

    //从服务器读取ChatRecord,初始化数据
    private void Classinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new ArrayList<>();

                Gson gson = new Gson();

                JSONObject object = new JSONObject();
                try {
                    object.put("UUID", uuid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    URL url = new URL(Constant.URL_CHAT_LOCAL + "SearchByUUID");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());

                    JSONArray jsonArray = response.getJSONArray("list");
                    mData.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ChatRecord cr = new ChatRecord();
                        cr = gson.fromJson(jsonArray.get(i).toString(), ChatRecord.class);
                        mData.add(cr);
                        Log.e("123", cr.toString());
                    }

                    inputStream.close();//关闭数据流
                    conn.disconnect();

                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //刷新
    public void refresh() {
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
                    List<ChatRecord> list = (List<ChatRecord>) response.get("list");

                    inputStream.close();//关闭数据流
                    conn.disconnect();
                    mData = list;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static int getUserId() {
        int id = user.getUserid();
        return id;
    }

    // 返回时向MessageActivity传递数据
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        Log.e("返回事件", "123");
        String content = "1231";
        String date = "1232";
        int id = 123;
        for (int j = 0; j < mData.size(); j++) {
            if (mData.get(j).getChatrecordid() == newid) {
                content = mData.get(j).getContent();
                date = mData.get(j).getDate();
                id = mData.get(j).getUUID();
            }
        }
        i.putExtra("content", content);
        i.putExtra("date", date);
        i.putExtra("UUID", id);
        ChatRecordActivity.this.setResult(1, i);
        ChatRecordActivity.this.finish();
    }

    private void search() {
        new Thread(new Runnable() {

            public void stopMe() {
                stopMe = false;
            }

            @Override
            public void run() {

                while (stopMe) {
                    try {
                        String result = null;
                        URL url = new URL(Constant.URL_LOCAL + "SearchListByUserId");
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
                        if (result == null) {
                            break;
                        }
                        userList = gson.fromJson(result, new TypeToken<List<User>>() {
                        }.getType());
                        Log.e("888", userList.get(0).getUserid() + "");
                        for (int i = 0; i < userList.size(); i++) {
                            userurl.add(userList.get(i).getImageurl());
                        }
                        //向Handler传递message,处理ui
                        is.close();//关闭数据流
                        conn.disconnect();
                        break;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}


