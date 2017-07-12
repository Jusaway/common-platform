package proj.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import proj.platform.dao.UserInfoDao;
import proj.platform.entity.FileMgr;
import proj.platform.entity.UserInfo;
import proj.platform.entity.UserInfoDetail;
import proj.platform.entity.UserLoginTime;
import proj.platform.util.FileMgrUtil;

@Service("userInfoService")
public class UserInfoService {
	private static Logger log = Logger.getLogger(UserInfoService.class);
	@Resource(name = "userInfoDao")
	private UserInfoDao userInfoDao;
	@Resource(name = "fileMgrService")
	private FileMgrService fileMgrServcie;
	
	public boolean login(UserInfo userInfo){
		UserInfo current = userInfoDao.findByUserName(userInfo.getUserName());
		//用户名和密码正确
		if(current != null && current.getPassword().equals(userInfo.getPassword())){
			return true;
		}
		return false;
	}
	
	public UserInfo add(UserInfo userInfo){
		userInfoDao.save(userInfo);
		return userInfo;
	}
	
	public UserInfo findByUserName(String userName){
		return userInfoDao.findByUserName(userName);
	}
	/**
	 * 获得对应的附件
	 * @param session
	 * @return
	 */
	public FileMgr getFileMgr(HttpSession session){
		String userName = (String) session.getAttribute("userName");
		UserInfo userInfo = this.findByUserName(userName);
		if(userInfo != null){
			UserInfoDetail detail = userInfo.getUserInfoDetail();
			if(detail != null){
				return detail.getFileMgr();
			}
		}
		return null;
	}
	public List<UserLoginTime> getOnlineUsers(HttpServletRequest request){
		ServletContext application = request.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, HttpSession> loginMap = (Map<String, HttpSession>) application.getAttribute("loginMap");
		log.info("登录用户人数："+loginMap.size());
		Set<Entry<String, HttpSession>> entrys = loginMap.entrySet();
		List<UserLoginTime> userLoginTimes = new ArrayList<>();
		for(Entry<String, HttpSession> entry:entrys){
			UserLoginTime userLoginTime = new UserLoginTime();
			userLoginTime.setUserName(entry.getKey());
			userLoginTime.setLoginTime((String) entry.getValue().getAttribute("loginTime"));
			userLoginTimes.add(userLoginTime);
		}
		return userLoginTimes;
	}
	
	public UserInfo edit(HttpSession session, UserInfoDetail userInfoDetail, 
			MultipartFile file){
		userInfoDetail.setFileMgr(FileMgrUtil.upload(file));
		String userName = (String) session.getAttribute("userName");
		UserInfo userInfo = userInfoDao.findByUserName(userName);
		UserInfoDetail current = userInfo.getUserInfoDetail();
		
		//数据库已存在用户详细信息
		if(current != null){
			current.setAddress(userInfoDetail.getAddress());
			current.setEmail(userInfoDetail.getEmail());
			current.setPhone(userInfoDetail.getPhone());
			//文件为空默认不变更FileMgr
			if(file != null){
				//原本有附件，先删除上传记录和附件
				if(current.getFileMgr() != null){
					String fileId = current.getFileMgr().getFileId();
					fileMgrServcie.delete(fileId);
				}
				current.setFileMgr(userInfoDetail.getFileMgr());
			}
		}else{
			userInfo.setUserInfoDetail(userInfoDetail);
			userInfoDao.saveOrUpdate(userInfo);	//测试级联保存和更新
		}
		return userInfo;
	}
}
