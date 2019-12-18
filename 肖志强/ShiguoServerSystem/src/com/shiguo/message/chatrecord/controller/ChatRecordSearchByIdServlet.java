package com.shiguo.message.chatrecord.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.shiguo.entity.ChatRecord;
import com.shiguo.message.chatrecord.service.ChatRecordServiceImpl;

/**
 * Servlet implementation class ChatRecordSeachServlet
 */
@WebServlet("/ChatRecord/SearchById")
public class ChatRecordSearchByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatRecordSearchByIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search ChatRecord By Id");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		InputStream inputStream = request.getInputStream();
		OutputStream outputStream = response.getOutputStream();
		
		byte[] buffer = new byte[2048];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = inputStream.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
		
		ChatRecord chatRecord = new ChatRecord();
		
		Gson gson = new Gson();		
		chatRecord = gson.fromJson(stringBuffer.toString(), ChatRecord.class);
		
		ChatRecord result = new ChatRecordServiceImpl().searchChatRecordById(chatRecord.getChatrecordid());
		
		JSONObject res = new JSONObject();
		
		if(result != null) {
			res.put("isSuccess", true);
			res.put("result", chatRecord);
		}else {
			res.put("isSuccess", false);
		}
		
		outputStream.write(res.toString().getBytes("UTF-8"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
