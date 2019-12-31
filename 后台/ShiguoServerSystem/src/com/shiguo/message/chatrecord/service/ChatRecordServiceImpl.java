package com.shiguo.message.chatrecord.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.ChatRecord;
import com.shiguo.message.chatrecord.dao.ChatRecordDaoImpl;

public class ChatRecordServiceImpl {
	
	public String addChatRecord(ChatRecord chatRecord) {
		
		List<ChatRecord> oldList = chatrecordList();

		if(new ChatRecordDaoImpl().insertChatRecord(chatRecord) > 0) {
			
			List<ChatRecord> newList = chatrecordList();
			
			newList.removeAll(oldList);
			
			int id = newList.get(0).getChatrecordid();
			
			return String.valueOf(id);
			
		}
		
		return null;
		
	}
	
	public boolean dropChatRecord(int id) {
		
		return new ChatRecordDaoImpl().deleteChatRecord(id) > 0;
		
	}
	
	public boolean dropChatRecordByUUID(int UUID) {
		
		return new ChatRecordDaoImpl().deleteChatRecordByUUID(UUID) > 0;
		
	}
	
	
	public boolean editChatRecord(ChatRecord chatRecord) {

		return new ChatRecordDaoImpl().updateChatRecord(chatRecord) > 0;
		
	}
	
	public List<ChatRecord> chatrecordList() {
		
		return new ChatRecordDaoImpl().findAllChatRecord();
		
	}
	
	public ChatRecord searchChatRecordById(int id) {
		
		return new ChatRecordDaoImpl().findChatRecordById(id);
		
	}
	
	public List<ChatRecord> searchChatRecordByUUID(int UUID) {
		
		return new ChatRecordDaoImpl().findChatRecordByUUID(UUID);
		
	}
	
	public List<ChatRecord> reflashChatRecord(int UUID, String lastTime) {
		
		List<ChatRecord> result = searchChatRecordByUUID(UUID);
		
		if(result == null) {return null;}
				
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date clienttime = df.parse(lastTime);
				
			for(int i = 0;i < result.size();) {
				
				Date update = df.parse(result.get(i).getDate());
				
				if(clienttime.getTime() >= update.getTime()) {
					result.remove(i);
				}else {
					i ++;
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.size() == 0 ? null : result;
	}
	
	
}
