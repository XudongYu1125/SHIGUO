package com.shiguo.message.conversations.controller;

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
import com.shiguo.entity.Conversations;
import com.shiguo.message.chatrecord.service.ChatRecordServiceImpl;
import com.shiguo.message.conversations.service.ConversationsServiceImpl;

/**
 * Servlet implementation class ChatRecordDeleteServlet
 */
@WebServlet("/Conversations/Delete")
public class ConversationsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationsDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Delete Conversations");
		
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
	
		Conversations conversations = new Conversations();
		
		JSONObject object = new JSONObject(stringBuffer.toString());
		
		Gson gson = new Gson();		
		conversations = gson.fromJson(object.getString("conversations"), Conversations.class);
		
		String userid = object.getString("userid");
		
		String[] OwnerStatus = conversations.getOwnersStatus().split(";");
		
		if(OwnerStatus.equals(";")) {
					
			if(new ConversationsServiceImpl().dropConversations(conversations.getUUID()) &&
					new ChatRecordServiceImpl().dropChatRecordByUUID(conversations.getUUID())) {
				
				outputStream.write("true".getBytes("UTF-8"));
				
			}else {
				
				outputStream.write("false".getBytes("UTF-8"));
				
			}
			
		}else {
			
			for(int i = 0;i < OwnerStatus.length;i ++) {
				if(OwnerStatus[i].equals(userid)) {
					OwnerStatus[i] = "";
					break;
				}
			}
			
			StringBuffer temp = new StringBuffer();
			for(int i = 0;i < OwnerStatus.length;i ++) {
				temp.append(OwnerStatus[i]);
			}
			
			conversations.setOwnersStatus(new String(temp));
			
			if(new ConversationsServiceImpl().editConversations(conversations)) {
				outputStream.write("true".getBytes("UTF-8"));
			}else {
				outputStream.write("false".getBytes("UTF-8"));
			}
			
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
