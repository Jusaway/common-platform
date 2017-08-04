package proj.platform.entity;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * SMTP邮件配置实体类
 */
@Entity
@Table(name = "mail_config")
public class MailConfig {

	private Integer id;
	private String host;		//服务器地址
	private String username;	//用户名，一般是邮箱@之前的内容
	private String password;	//密码
	private String senderName;	//发件人姓名
	private String senderEmail;	//发件人邮箱
	
	public MailConfig() {
	}

	public MailConfig(Integer id, String host, String username, String password, String senderName,
			String senderEmail) {
		this.id = id;
		this.host = host;
		this.username = username;
		this.password = password;
		this.senderName = senderName;
		this.senderEmail = senderEmail;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	/**
	 * 获得发件地址
	 * @return
	 */
	@Transient
	public String getFrom(){
		String sender = null;
		if(isNotNull(senderName)){
			try {
				sender = "\""+MimeUtility.encodeText(senderName)+"\"<"+senderEmail+">";
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}else{
			sender = senderEmail;
		}
		return sender;
	}
	/**
	 * 判断必填项是否为空
	 * @return
	 */
	@Transient
	public boolean isEmpty(){
		if(isNotNull(username) 
				&& isNotNull(password)
				&& isNotNull(host)
				&& isNotNull(senderEmail)){
			return false;
		}
		return true;
	}
	private boolean isNotNull(String str){
		if(str != null && !str.isEmpty()){
			return true;
		}
		return false;
	}
}
