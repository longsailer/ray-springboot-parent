package org.ray.jwt.right;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ray.jwt.security.Menus;

/**
 * MenuRightUtil.java <br>
 * <br>
 * 把菜单转化为二进制，通过二进制进行菜单权限的生成与判断
 * @author: ray
 * @date: 2020年1月8日
 */
public class MenuRightUtil {
	private Integer maxBit;
	private String rightStringTemplate;
	private Map<String, Menus> menuList;

	public MenuRightUtil() {
	}

	private String getBinaryString(int bits) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < bits; i++) {
			s.append("0");
		}
		return s.toString();
	}

	private static Long getValue(String binaryString) {
		return Long.valueOf(binaryString, 2);
	}

	/**
	 * 创建权限
	 * @author ray
	 * @param allMenus
	 * @param mapper
	 * @return void
	 */
	public <T extends BaseMenu> void createRight(List<T> allMenus, MenuMapper<T, Menus> mapper) {
		this.maxBit = allMenus.size();
		this.rightStringTemplate = getBinaryString(this.maxBit);
		this.menuList = mapper.getRightList(allMenus);
	}

	/**
	 * 根据用户的菜单创建权限值
	 * @param menuList
	 * @param mapper
	 * @return
	 */
	public <T> Long getUserRight(List<T> menuList, RightMapper<T> mapper) {
		String userRight = mapper.getUserRightString(menuList);
		Long right = getValue(userRight);
		return right;
	}

	/**
	 * 根据权限值和序号，判断是否拥有权限
	 * @author ray
	 * @param right
	 * @param menuUrl
	 * @return boolean
	 */
	public boolean isRight(Long right, String menuUrl) {
		try{
			int index = this.menuList.get(menuUrl).getIndex();
			return this.isRight(right, index);
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 根据权限值和序号，判断是否拥有权限
	 * @author ray
	 * @param right
	 * @param index
	 * @return boolean
	 */
	private boolean isRight(Long right, int index) {
		String s = Long.toBinaryString(right);
		s = getBinaryString(maxBit - s.length()) + s;
		char c = s.charAt(index);
		return c == 1;
	}
	
	public Menus getMenu(String menuUrl){
		return this.menuList.get(menuUrl);
	}

	public abstract class RightMapper<T> {
		public String getUserRightString(List<T> ml) {
			String userRight = rightStringTemplate;
			if (ml == null || ml.size() == 0)
				return userRight;
			for (T m : ml) {
				int index = menuList.get(getUrl(m)).getIndex();
				userRight = userRight.substring(0, index) + "1" + userRight.substring(index + 1);
			}
			return userRight;
		}

		public abstract String getUrl(T m);
	}

	public abstract class MenuMapper<T, K extends BaseMenu> {
		public Map<String, K> getRightList(List<T> ml) {
			Map<String, K> menuList = new HashMap<String, K>();
			if (ml == null || ml.size() == 0)
				return menuList;
			for (int i = 0; i < ml.size(); i++) {
				T t = ml.get(i);
				K m = getMenu(t);
				m.setIndex(i);
				menuList.put(m.getUrl(), m);
			}
			return menuList;
		}

		public abstract K getMenu(T t);
	}
}