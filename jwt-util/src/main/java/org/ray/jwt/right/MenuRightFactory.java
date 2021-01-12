package org.ray.jwt.right;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * MenuRightFactory.java
 * <br><br>
 * 负责创建MenuRightUtil，并支持多个Util同时存在，以满足多系统的菜单权限问题。
 * 详细的使用请阅读 {@link org.ray.jwt.right.MenuRightUtil}
 * @author: ray
 * @date: 2020年1月9日
 */
public class MenuRightFactory {

	private static ConcurrentMap<String, MenuRightUtil> mruMap = new ConcurrentHashMap<>(10);
	
	public static MenuRightUtil createMenuRightUtil(){
		return new MenuRightUtil();
	}
	
	public static MenuRightUtil createMenuRightUtil(String systemName){
		if(mruMap.containsKey(systemName)){
			return mruMap.get(systemName);
		}
		MenuRightUtil mru = new MenuRightUtil();
		mruMap.put(systemName, mru);
		return mru;
	}
}

