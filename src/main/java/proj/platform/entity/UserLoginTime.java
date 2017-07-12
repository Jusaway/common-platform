package proj.platform.entity;

/**
 * 用户及其登录时间
 *
 */
public class UserLoginTime {
	private String userName;
	private String loginTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
}
