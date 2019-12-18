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
import com.shiguo.entity.Conversations;
import com.shiguo.message.chatrecord.service.ChatRecordServiceImpl;
import com.shiguo.message.conversations.service.ConversationsServiceImpl;

/**
 * Servlet implementation class ChatRecordAddServlet
 */
@WebServlet("/ChatRecord/Add")
public class ChatRecordAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatRecordAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Add ChatRecord");
		
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
		
		String content = chatRecord.getContent();
		String date = chatRecord.getDate();
		
		JSONObject res = new JSONObject();
		
		Conversations conversations = null;
		String UUID = null;
		
		if(chatRecord.getUUID() == 0) {
			conversations = new Conversations();
			conversations.setOwnersId(chatRecord.getSenderid() + ";" + chatRecord.getReceiverid());
		}else {
			conversations = new ConversationsServiceImpl().searchConversationsByUUID(chatRecord.getUUID());
		}
		
		conversations.setOwnersStatus(conversations.getOwnersId());
		conversations.setContent(content);
		conversations.setDate(date);
		
		if(chatRecord.getUUID() == 0) {
			UUID = new ConversationsServiceImpl().addConversations(conversations);
			chatRecord.setUUID(Integer.parseInt(UUID));
		}
		
		String chatRecordId = new ChatRecordServiceImpl().addChatRecord(chatRecord);
				
		if(chatRecordId != null && new ConversationsServiceImpl().editConversations(conversations)) {
			
			res.put("isSuccess", true);
			res.put("chatRecordId", chatRecordId);
			
			if(UUID != null) {
				res.put("UUID", UUID);
			}
			
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
