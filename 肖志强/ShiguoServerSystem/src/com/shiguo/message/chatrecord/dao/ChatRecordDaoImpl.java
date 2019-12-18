package com.shiguo.message.chatrecord.dao;

import java.util.List;

import com.shiguo.entity.ChatRecord;
import com.shiguo.util.DBUtil;

public class ChatRecordDaoImpl {
	
	public int insertChatRecord(ChatRecord chatRecord) {
		return DBUtil.executeUpdate("insert into chatrecord(UUID,senderid,receiverid,content,date) values (?,?,?,?,?)", 
									new Object[] {chatRecord.getUUID(),chatRecord.getSenderid(),chatRecord.getReceiverid(),chatRecord.getContent(),chatRecord.getDate()});
	}
	
	public int deleteChatRecord(int id) {
		return DBUtil.executeUpdate("delete from chatrecord where chatrecordid=?", new Object[] {id});
	}
	
	public int deleteChatRecordByUUID(int UUID) {
		return DBUtil.executeUpdate("delete from chatrecord where UUID=?", new Object[] {UUID});
	}
	
	
	public int updateChatRecord(ChatRecord chatRecord) {	
		return DBUtil.executeUpdate("update chatrecord set UUID=?,senderid=?,receiverid=?,content=?,date=? where chatrecordid=?", 
									new Object[] {chatRecord.getUUID(),chatRecord.getSenderid(),chatRecord.getReceiverid(),chatRecord.getContent(),chatRecord.getDate(),chatRecord.getChatrecordid()});
	}
	
	@SuppressWarnings("unchecked")
	public List<ChatRecord> findAllChatRecord() {
		return DBUtil.findAll(ChatRecord.class,"select * from chatrecord");
	}
	
	public ChatRecord findChatRecordById(int id) {
		Object obj = DBUtil.findById(ChatRecord.class,"select * from chatrecord where chatrecordid=?",id);
		return obj != null ? (ChatRecord)obj : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChatRecord> findChatRecordByUUID(int UUID) {
		Object obj = DBUtil.find(ChatRecord.class, "select * from chatrecord where UUID=?", new Object[] {UUID});
		return obj != null ? (List<ChatRecord>)obj : null;
	}
	public String[] findUUIDByUser(int userid) {
		Object obj = DBUtil.find("select DISTINCT UUID from chatrecord where senderid=? or receiverid=?", new Object[] {userid,userid});
		return obj != null ? (String[])obj : null;
	}

}
