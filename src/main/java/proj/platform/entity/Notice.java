package proj.platform.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notice")
public class Notice {
	private Integer id;		//主键ID
	private String content;	//内容
	private String time;	//时间
	private Integer queryOrder;//排序号
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getQueryOrder() {
		return queryOrder;
	}
	public void setQueryOrder(Integer queryOrder) {
		this.queryOrder = queryOrder;
	}
}
