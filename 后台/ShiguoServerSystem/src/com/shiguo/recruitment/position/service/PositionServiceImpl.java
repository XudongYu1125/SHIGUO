package com.shiguo.recruitment.position.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.Position;
import com.shiguo.recruitment.position.dao.PositionDaoImpl;

public class PositionServiceImpl {

	public String addPosition(Position position) {
		
		List<Position> oldList = positionList();

		if(new PositionDaoImpl().insertPosition(position) > 0) {
			
			List<Position> newList = positionList();
			
			newList.removeAll(oldList);
			
			int id = newList.get(0).getPositionid();
			
			return String.valueOf(id);
			
		}
		
		return null;
		
	}
	
	public boolean dropPosition(int id) {
		
		return new PositionDaoImpl().deletePosition(id) > 0;
		
	}
	
	public boolean editPosition(Position position) {

		return new PositionDaoImpl().updatePosition(position) > 0;
		
	}
	
	public List<Position> positionList() {
		
		return new PositionDaoImpl().findAllPosition();
		
	}
	
	public Position searchPositionById(int id) {
		
		return new PositionDaoImpl().findPositionById(id);
		
	}
	
	public List<Position> searchPositionByUser(int userid) {
		
		return new PositionDaoImpl().findPositionByUser(userid);
		
	}
	
	public List<Position> searchPositionByCondition(String condition, String lastTime) {
		
		return new PositionDaoImpl().findPositionByCondition(condition, lastTime);
		
	}
	
	
	public List<Position> reflashPosition(String lastTime) {
		
		List<Position> result = positionList();
		
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
