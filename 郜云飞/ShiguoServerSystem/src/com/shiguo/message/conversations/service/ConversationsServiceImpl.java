package com.shiguo.message.conversations.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.Conversations;
import com.shiguo.message.conversations.dao.ConversationsDaoImpl;

public class ConversationsServiceImpl {
	
	public String addConversations(Conversations conversations) {
		
		List<Conversations> oldList = conversationsList();
		
		if(new ConversationsDaoImpl().insertConversations(conversations) > 0) {
			
			List<Conversations> newList = conversationsList();
			
			newList.removeAll(oldList);
			
			int id = newList.get(0).getUUID();
			
			return String.valueOf(id);
			
		}
		
		return null;
		
	}
	
	public boolean dropConversations(int UUID) {
		
		return new ConversationsDaoImpl().deleteConversations(UUID) > 0;
		
	}
	
	public boolean editConversations(Conversations conversations) {

		return new ConversationsDaoImpl().updateConversations(conversations) > 0;
		
	}
	
	public List<Conversations> conversationsList() {
		
		return new ConversationsDaoImpl().findAllConversations();
		
	}
	
	public Conversations searchConversationsByUUID(int UUID) {
		
		return new ConversationsDaoImpl().findConversationsByUUID(UUID);
		
	}
	
	public List<Conversations> searchConversationsByUser(int userid) {
		
		List<Conversations> list = new ConversationsDaoImpl().findConversationsByUser(userid);
		
		if(list == null) {
			return null;
		}
		
		for(int i = 0;i < list.size();) {
			Conversations conversations = list.get(i);
			String[] status = conversations.getOwnersStatus().split(";");
			boolean p = false;
			for(int j = 0;j < status.length;j ++) {
				if(status[j].equals(String.valueOf(userid))) {
					p = true;
					break;
				}
			}
			if(p) {
				i ++;
			}else {
				list.remove(i);
			}
		}
		
		return list;
		
	}
	
	public List<Conversations> reflashConversations(int userid, String lastTime) {
		
		List<Conversations> result = searchConversationsByUser(userid);
		
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
