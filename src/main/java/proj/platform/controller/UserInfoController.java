package proj.platform.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.platform.entity.Result;
import proj.platform.entity.UserInfo;
import proj.platform.service.UserInfoService;
import proj.platform.util.DataUtil;
import proj.platform.util.State;

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
		Set<String> loginSet = (Set<String>) application.getAttribute("loginSet");//已经通过监听器创建
		boolean isCorrect = userInfoService.login(userInfo);
		boolean isLogin = loginSet.contains(userInfo.getUserName());
		if(isCorrect && !isLogin){
			result.setStateCode(State.SUCCESS_CODE);
			result.setDebugMsg("登录成功！");
			loginSet.add(userInfo.getUserName());
			application.setAttribute("loginSet", loginSet);
			result.setLoginSet(loginSet);
		}else if(!isCorrect){
			result.setStateCode(State.ERROR_CODE);
			result.setDebugMsg("用户名或密码错误！");
		}else if(isLogin){
			result.setStateCode(State.ERROR_CODE);
			result.setDebugMsg("当前用户已登录！");
			result.setLoginSet(loginSet);
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
			result.setStateCode(State.ERROR_CODE);
			result.setDebugMsg("用户名已存在!");
		}else{
			userInfoService.add(userInfo);
			result.setStateCode(State.SUCCESS_CODE);
			result.setDebugMsg(userInfo.getUserName()+"注册成功！");
		}
		DataUtil.reply(response, result);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request, 
			HttpServletResponse response,
			String userName){
		ServletContext application = request.getServletContext();
		Set<String> loginSet = (Set<String>) application.getAttribute("loginSet");
		loginSet.remove(userName);
		HttpSession session = request.getSession();
		session.invalidate();
		result = new Result();
		result.setStateCode(State.SUCCESS_CODE);
		result.setDebugMsg("注销成功！");
		DataUtil.reply(response, result);
	}
}
