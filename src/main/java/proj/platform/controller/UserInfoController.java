package proj.platform.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.platform.entity.Result;
import proj.platform.entity.UserInfo;
import proj.platform.entity.UserLoginTime;
import proj.platform.service.UserInfoService;
import proj.platform.util.DataUtil;
import proj.platform.util.Status;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;
	private Result result;
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param userInfo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login.do")
	public void login(HttpServletRequest request, 
			HttpServletResponse response, 
			UserInfo userInfo){
		result = new Result();
		ServletContext application = request.getServletContext(); 
		Map<String, HttpSession> loginMap = (Map<String, HttpSession>) application.getAttribute("loginMap");
		HttpSession session = request.getSession();
		//当前会话已有用户登录
		if(loginMap.containsValue(session)){
			result.setStatus(Status.HAD_LOGINED);
			result.setDebugMsg("当前会话已有用户登录！");
		}else{
			boolean isCorrect = userInfoService.login(userInfo);
			if(isCorrect){
				result.setStatus(Status.SUCCESS);
				result.setDebugMsg("登录成功！");
				session.setAttribute("userName", userInfo.getUserName());
				session.setAttribute("loginTime", DataUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				loginMap.put(userInfo.getUserName(), session);
			}else{
				result.setStatus(Status.ERROR);
				result.setDebugMsg("用户名或密码错误！");
			}
		}
		DataUtil.reply(response, result);
	}
	@RequestMapping("/register.do")
	public void register(HttpServletRequest request,
			HttpServletResponse response,
			UserInfo userInfo){
		//假设前端已对密码前后两次密码判断通过
		result = new Result();
		UserInfo current = userInfoService.findByUserName(userInfo.getUserName());
		if(current != null){
			result.setStatus(Status.ERROR);
			result.setDebugMsg("用户名已存在!");
		}else{
			userInfoService.add(userInfo);
			result.setStatus(Status.SUCCESS);
			result.setDebugMsg(userInfo.getUserName()+"注册成功！");
		}
		DataUtil.reply(response, result);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request, 
			HttpServletResponse response){
		ServletContext application = request.getServletContext();
		Map<String, HttpSession> loginMap = (Map<String, HttpSession>) application.getAttribute("loginMap");
		HttpSession session = request.getSession();
		loginMap.remove(session.getAttribute("userName"));
//		session.invalidate();
		session.removeAttribute("userName");
		result = new Result();
		result.setStatus(Status.SUCCESS);
		result.setDebugMsg("注销成功！");
		DataUtil.reply(response, result);
		return "redirect:/login.html";
	}
	@RequestMapping("/getOnlineUsers.do")
	public void getOnlineUsers(HttpServletRequest request,
			HttpServletResponse response){
		List<UserLoginTime> userLoginTimes = userInfoService.getOnlineUsers(request);
		result = new Result();
		result.setUserLoginTimes(userLoginTimes);
		DataUtil.reply(response, result);
	}
}
