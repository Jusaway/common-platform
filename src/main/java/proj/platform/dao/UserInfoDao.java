package proj.platform.dao;

import org.springframework.stereotype.Repository;
import proj.platform.entity.UserInfo;

@Repository("userInfoDao")
public class UserInfoDao extends BaseDao<UserInfo>{
	/**
	 * 根据用户账号查询用户信息
	 * @param userName
	 * @return
	 */
	public UserInfo findByUserName(String userName){
		return this.find("userName", userName);
	}
	/**
	 * 增加用户
	 * @param userInfo
	 * @return
	 */
	public UserInfo add(UserInfo userInfo){
		this.save(userInfo);
		return userInfo;
	}
}
