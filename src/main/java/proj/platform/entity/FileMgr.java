package proj.platform.entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fileMgr")
public class FileMgr {
	private String fileId;//附件ID
	private String fileName;
	private String location;
	private String uploadTime;
	
	public FileMgr() {
		
	}
	public FileMgr(String fileId, String fileName, String location, String uploadTime) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.location = location;
		this.uploadTime = uploadTime;
	}
	@Id
	@GenericGenerator(name = "generator",strategy="assigned")
	@Generated(value = "generator")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
}
