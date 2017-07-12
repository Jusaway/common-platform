package proj.platform.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proj.platform.entity.FileMgr;

@Repository
@Transactional(rollbackFor = Exception.class)
public class FileMgrDao extends BaseDao<FileMgr> {
	public FileMgr findByFileId(String fileId){
		return this.find("fileId", fileId);
	}
}
