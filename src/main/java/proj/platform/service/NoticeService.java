package proj.platform.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import proj.platform.dao.BaseDao;
import proj.platform.dao.NoticeDao;
import proj.platform.entity.Notice;

@Service
public class NoticeService {
	@Resource(name = "noticeDao")
	private NoticeDao noticeDao;
	
	public Notice add(Notice notice){
		noticeDao.save(notice);//事务立即提交（主键自增）
		notice.setQueryOrder(notice.getId());
		return notice;
	}
	//批量更新
	public boolean updateList(Integer[] ids, Integer[] queryOrders){
		if(ids != null && queryOrders != null){
			Arrays.sort(queryOrders);
			for(int i=0;i<ids.length;i++){
				Notice notice = noticeDao.find("id", ids[i]);
				notice.setQueryOrder(queryOrders[i]);
			}
			return true;
		}
		return false;
	}
	
	public List<Notice> findAll(Integer startRow, Integer pageSize){
		return noticeDao.find(startRow, pageSize, "queryOrder", BaseDao.ASC_ORDER);
	}
}
