package proj.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import proj.platform.dao.UserInfoDao;
import proj.platform.entity.UserInfo;
import proj.platform.entity.UserLoginTime;

@Service("userInfoService")
public class UserInfoService {
	private static Logger log = Logger.getLogger(UserInfoService.class);
	@Resource(name = "userInfoDao")
	private UserInfoDao userInfoDao;
	
	public boolean login(UserInfo userInfo){
		UserInfo current = userInfoDao.findByUserName(userInfo.getUserName());
		//用户名和密码正确
		if(current != null && current.getPassword().equals(userInfo.getPassword())){
			return true;
		}
		return false;
	}
	
	public UserInfo add(UserInfo userInfo){
		userInfoDao.save(userInfo);
		return userInfo;
	}
	
	public UserInfo findByUserName(String userName){
		return userInfoDao.findByUserName(userName);
	}
	
	public List<UserLoginTime> getOnlineUsers(HttpServletRequest request){
		ServletContext application = request.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, HttpSession> loginMap = (Map<String, HttpSession>) application.getAttribute("loginMap");
		log.info("登录用户人数："+loginMap.size());
		Set<Entry<String, HttpSession>> entrys = loginMap.entrySet();
		List<UserLoginTime> userLoginTimes = new ArrayList<>();
		for(Entry<String, HttpSession> entry:entrys){
			UserLoginTime userLoginTime = new UserLoginTime();
			userLoginTime.setUserName(entry.getKey());
			userLoginTime.setLoginTime((String) entry.getValue().getAttribute("loginTime"));
			userLoginTimes.add(userLoginTime);
		}
		return userLoginTimes;
	}
}
