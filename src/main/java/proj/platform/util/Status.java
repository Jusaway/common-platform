package proj.platform.util;

public enum Status {
	SUCCESS(0),	//成功
	ERROR(-1),	//失败
	NO_AUTHORIZATION(-2),//无权限
	HAD_LOGINED(-3);	//当前会话已有用户登录
	
	private final int code;
	private Status(int code){
		this.code = code;
	}
	public int getCode(){
		return this.code;
	}
}
