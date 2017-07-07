package proj.platform.entity;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class Result {
	private Integer stateCode;
	private String debugMsg;
	private Notice notice;
	private List<Notice> notices;
	private Map<String, HttpSession> loginMap; 
	private List<UserInfo> userInfos;
	private List<UserLoginTime> userLoginTimes;
	
	public Integer getStateCode() {
		return stateCode;
	}
	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}
	public String getDebugMsg() {
		return debugMsg;
	}
	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public List<Notice> getNotices() {
		return notices;
	}
	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}
	public Map<String, HttpSession> getLoginMap() {
		return loginMap;
	}
	public void setLoginMap(Map<String, HttpSession> loginMap) {
		this.loginMap = loginMap;
	}
	public List<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	public List<UserLoginTime> getUserLoginTimes() {
		return userLoginTimes;
	}
	public void setUserLoginTimes(List<UserLoginTime> userLoginTimes) {
		this.userLoginTimes = userLoginTimes;
	}
}
