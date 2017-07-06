package proj.platform.listener;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class LoginListener implements ServletContextListener,
		HttpSessionListener,
		HttpSessionAttributeListener{
	public static Logger log = Logger.getLogger(LoginListener.class);
	private Map<String, HttpSession> loginMap;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		//统计在线人数
		int onlineCount = 0;						
		application.setAttribute("onlineCount", onlineCount);
		//登录用户列表
		loginMap = new HashMap<>();
		application.setAttribute("loginMap", loginMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		

	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext application = se.getSession().getServletContext();
		int onlineCount = (int) application.getAttribute("onlineCount");
		onlineCount ++;
		application.setAttribute("onlineCount", onlineCount);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext application = se.getSession().getServletContext();
		int onlineCount = (int) application.getAttribute("onlineCount");
		onlineCount --;
		application.setAttribute("onlineCount", onlineCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String attrName = event.getName();
		//只对登录的用户进行处理
		if("userName".equals(attrName)){
			HttpSession session = event.getSession();
			ServletContext ctx = session.getServletContext();
			loginMap = (Map<String, HttpSession>) ctx.getAttribute("loginMap");
			
			HttpSession other = loginMap.get(event.getValue());
			//当前用户已登录
			if(other != null){
				other.removeAttribute("userName");
				other.setAttribute("msg", "当前用户已在别处登录！");
			}
			loginMap.put((String) event.getValue(), session);
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

}
