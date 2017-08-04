package proj.platform.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import proj.platform.entity.MailConfig;

/**
 * 基于Java Mail的邮件发送工具类
 * @version 1.0
 */
public class MailUtil {
	public static Logger logger = Logger.getLogger(MailUtil.class);
    public static final String PROTOCOL = "smtp";
    public static final String PORT = "25";
    public static final boolean IS_AUTH = true;
	/**
	 * 获取Session
	 * @param mailConfig
	 * @return
	 */
    private static Session getSession(final MailConfig mailConfig) {  
        Properties props = new Properties();  
        props.put("mail.smtp.host", mailConfig.getHost());	//设置服务器地址  
        props.put("mail.transport.protocol", PROTOCOL);		//设置协议
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", IS_AUTH);  
          
        Authenticator authenticator = new Authenticator() {  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());  
            }  
        };  
        Session session = Session.getDefaultInstance(props, authenticator);  
          
        return session;  
    }  
    /**
     * 获得接收邮件的地址
     * @param toEmails
     * @return
     */
    private static InternetAddress[] getAddress(String... toEmails){
    	InternetAddress[] address = new InternetAddress[toEmails.length];
    	for(int i=0;i<toEmails.length;i++){
    		try {
				address[i] = new InternetAddress(toEmails[i]);
			} catch (AddressException e) {
				
			}
    	}
    	return address;
    }
    /**
     * 列表转数组
     * @param list
     * @return
     */
    private static String[] toArray(List<String> list){
    	if(list != null){
    		String[] a = new String[list.size()];
    		list.toArray(a);
    		return a;
    	}
    	return null;
    }
    /**
     * 发送邮件
     * @param mailConfig
     * @param subject
     * @param content
     * @param toEmails
     * @param files
     * @return
     */
    public static boolean send(MailConfig mailConfig, String subject, String content, List<String> toEmails, List<File> files) { 
    	//如果配置不为空且接收邮箱地址不为空
    	if(mailConfig != null && !mailConfig.isEmpty() && toEmails != null && !toEmails.isEmpty()){
	        Session session = getSession(mailConfig);  
	        session.setDebug(true);
	        try {  
	            Message msg = new MimeMessage(session);  
	   
	            msg.setFrom(new InternetAddress(mailConfig.getFrom()));
	            msg.setRecipients(Message.RecipientType.TO, getAddress(toArray(toEmails)));  
	            msg.setSubject(subject);  
	            Multipart mp = new MimeMultipart();
	            MimeBodyPart mbp = new MimeBodyPart();
	            mbp.setContent(content, "text/html;charset=utf-8");
	            mp.addBodyPart(mbp);
	            // 附件不为空
	            if(files != null && !files.isEmpty()){
	            	for(File file:files){
	            		mbp = new MimeBodyPart();
	            		FileDataSource fds = new FileDataSource(file);
	            		mbp.setDataHandler(new DataHandler(fds));
	            		mbp.setFileName(MimeUtility.encodeText(fds.getName()));//防止中文乱码
	            		mp.addBodyPart(mbp);
	            	}
	            }
	            msg.setContent(mp);
	            msg.setSentDate(new Date());//发送时间
	            //发送邮件
	            Transport.send(msg); 
	            return true;
	        }  catch (MessagingException | UnsupportedEncodingException e) {  
	            e.printStackTrace();
	            return false;
	        }
    	}
    	return false;
    }  
    private MailConfig mailConfig;
    private List<String> toEmails = new ArrayList<String>();
    @Before
    public void setUp(){
    	mailConfig = new MailConfig(null, "localhost", "admin", "000000", "管理员", "admin@info.net");
    	toEmails.add("what@info.net");
    	toEmails.add("other@info.net");
    }
    @Test
    public void testSendTextEmail(){
    	send(mailConfig, "邮件测试", "今天是个好天气！", toEmails, null);
    	logger.info("发送成功!");
	}
    @Test
    public void testSendEmailWithFiles(){
    	List<File> files = new ArrayList<File>();
    	File file1 = new File("F:\\books\\EffectiveJava.pdf");
    	File file2 = new File("F:\\books\\git命令.jpg");
    	files.add(file1);
    	files.add(file2);
    	
    	send(mailConfig, "发送附件", "发送内容并带有附件", toEmails, files);
    	logger.info("发送成功！");
    }
}
