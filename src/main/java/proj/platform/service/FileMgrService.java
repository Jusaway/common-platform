package proj.platform.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import proj.platform.dao.FileMgrDao;
import proj.platform.entity.FileMgr;
import proj.platform.util.FileMgrUtil;

@Service("fileMgrService")
public class FileMgrService {
	@Resource(name = "fileMgrDao")
	private FileMgrDao fileMgrDao;
	/**
	 * 删除上传的文件
	 * @param fileId
	 * @return
	 */
	public boolean delete(String fileId){
		FileMgr fileMgr = fileMgrDao.findByFileId(fileId);
		if(fileMgr != null){
			String location = fileMgr.getLocation();
			//删除记录
			fileMgrDao.delete(fileMgr);
			//删除文件
			return FileMgrUtil.deleteFile(location);
		}
		return false;
	}
	/**
	 * 查询文件信息
	 * @param fileId
	 * @return
	 */
	public FileMgr find(String fileId){
		if(fileId != null){
			return fileMgrDao.findByFileId(fileId);
		}
		return null;
	}
}
