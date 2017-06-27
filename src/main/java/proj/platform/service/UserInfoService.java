package proj.platform.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import proj.platform.dao.UserInfoDao;
import proj.platform.entity.UserInfo;

@Service("userInfoService")
public class UserInfoService {
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
}
