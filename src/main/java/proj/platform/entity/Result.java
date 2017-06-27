package proj.platform.entity;

import java.util.List;
import java.util.Set;

public class Result {
	private Integer stateCode;
	private String debugMsg;
	private Notice notice;
	private Set<String> loginSet;
	private List<Notice> notices;
	
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
	public Set<String> getLoginSet() {
		return loginSet;
	}
	public void setLoginSet(Set<String> loginSet) {
		this.loginSet = loginSet;
	}
}
