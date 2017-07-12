package proj.platform.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户详细信息
 */
@Entity
@Table(name = "userInfoDetail")
public class UserInfoDetail {
	private Integer id;
	private String address;//住址
	private String phone;//电话
	private String email;//邮箱
	private FileMgr fileMgr;//用户头像信息
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pic_id")
	public FileMgr getFileMgr() {
		return fileMgr;
	}
	public void setFileMgr(FileMgr fileMgr) {
		this.fileMgr = fileMgr;
	}
}
