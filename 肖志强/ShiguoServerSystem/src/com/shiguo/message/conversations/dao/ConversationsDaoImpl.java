package com.shiguo.message.conversations.dao;

import java.util.ArrayList;
import java.util.List;

import com.shiguo.entity.Conversations;
import com.shiguo.message.chatrecord.dao.ChatRecordDaoImpl;
import com.shiguo.util.DBUtil;

public class ConversationsDaoImpl {
	
	public int insertConversations(Conversations conversations) {
		return DBUtil.executeUpdate("insert into conversations(UUID,OwnersId,OwnersStatus,content,date) values (?,?,?,?,?)", 
									new Object[] {conversations.getUUID(),conversations.getOwnersId(),conversations.getOwnersStatus(),conversations.getContent(),conversations.getDate()});
	}
	
	public int deleteConversations(int UUID) {
		return DBUtil.executeUpdate("delete from conversations where UUID=?", new Object[] {UUID});
	}
	
	public int updateConversations(Conversations conversations) {	
		return DBUtil.executeUpdate("update conversations set OwnersStatus=?,content=?,date=? where UUID=?", 
									new Object[] {conversations.getOwnersStatus(),conversations.getContent(),conversations.getDate(),conversations.getUUID()});
	}
	
	@SuppressWarnings("unchecked")
	public List<Conversations> findAllConversations() {
		return DBUtil.findAll(Conversations.class,"select * from conversations");
	}
	
	public Conversations findConversationsByUUID(int UUID) {
		Object obj = DBUtil.findById(Conversations.class,"select * from conversations where UUID=?",UUID);
		return obj != null ? (Conversations)obj : null;
	}
	
	public List<Conversations> findConversationsByUser(int userid) {
		
		String[] UUIDList = new ChatRecordDaoImpl().findUUIDByUser(userid);
				
		List<Conversations> list = new ArrayList<Conversations>();
		
		for(int i = 0;i < UUIDList.length;i ++) {
			if(UUIDList[i].equals("")) {
				continue;
			}
			list.add(findConversationsByUUID(Integer.parseInt(UUIDList[i])));
		}
		
		return list;
		
	}
}
