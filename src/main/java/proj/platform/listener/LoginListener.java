package proj.platform.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class LoginListener implements ServletContextListener,HttpSessionListener {
	public static Logger log = Logger.getLogger(LoginListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		Set<String> loginSet = new HashSet<String>();
		int onlineCount = 0;						//统计在线人数
		application.setAttribute("loginSet", loginSet);
		application.setAttribute("onlineCount", onlineCount);
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

}
