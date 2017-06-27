package proj.platform.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proj.platform.entity.Notice;

@Repository(value = "noticeDao")
@Transactional(rollbackFor = Exception.class)
public class NoticeDao extends BaseDao<Notice> {

}
