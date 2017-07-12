package proj.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class StateInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURL().toString();
		HttpSession session = request.getSession();
		if(url.indexOf("login.html")>0){
			//已有用户登录，跳转到首页
			if(session != null && session.getAttribute("userName") != null){
				response.sendRedirect("index.html");
			}
			return true;
		}
		if(session != null && session.getAttribute("userName") != null){
			return true;
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
