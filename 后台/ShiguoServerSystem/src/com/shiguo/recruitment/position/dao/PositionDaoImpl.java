package com.shiguo.recruitment.position.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.Position;
import com.shiguo.util.DBUtil;

public class PositionDaoImpl {
	
	public int insertPosition(Position position) {
		return DBUtil.executeUpdate("insert into position (name,place,request,salary,userid,date) values (?,?,?,?,?,?)", 
									new Object[] {position.getName(),position.getPlace(),position.getRequest(),position.getSalary(),position.getUserid(),position.getDate()});
	}
	
	public int deletePosition(int id) {
		return DBUtil.executeUpdate("delete from position where positionid=?", new Object[] {id});
	}
	
	public int updatePosition(Position position) {	
		return DBUtil.executeUpdate("update position set name=?,place=?,request=?,salary=?,userid=?,date=? where positionid=?", 
									new Object[] {position.getName(),position.getPlace(),position.getRequest(),position.getSalary(),position.getUserid(),position.getDate(),position.getPositionid()});
	}
	
	@SuppressWarnings("unchecked")
	public List<Position> findAllPosition() {
		return DBUtil.findAll(Position.class,"select * from position");
	}
	
	public Position findPositionById(int id) {
		Object obj = DBUtil.findById(Position.class,"select * from position where positionid=?",id);
		return obj != null ? (Position)obj : null;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Position> findPositionByUser(int userid) {
		Object obj = new DBUtil().find(Position.class, "select * from position where userid=?", new Object[]{userid});
		return obj != null ? (List<Position>)obj : null;
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Position> findPositionByCondition(String condition, String lastTime) {
		
		Object obj = new DBUtil().find(Position.class, "select * from position where name=? or place=?", new Object[]{condition});
		
		List<Position> result = null;
		
		if(obj == null) {return null;}
		
		try {
			
			result = (List<Position>) obj;
			
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
