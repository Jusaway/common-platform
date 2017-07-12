package proj.platform.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import proj.platform.entity.FileMgr;
import proj.platform.service.FileMgrService;
/**
 * 文件管理工具类
 */
public class FileMgrUtil {
	public static String getWebRootPath(){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("../../");
		String path = url.getPath();
		return path;
	}
	/**
	 * 获得要上传的文件路径
	 * @param directoryName
	 * @return
	 */
	public static String getFilePath(String directoryName){
		String fileRootPath = new File(getWebRootPath()).getParent() + File.separator + "datas" + File.separator;
		if(directoryName != null){
			fileRootPath = fileRootPath + directoryName + File.separator;
		}
		return fileRootPath;
	}
	/**
	 * 文件上传，获得包含文件信息的实体对象
	 * @param file
	 * @return
	 */
	public static FileMgr upload(MultipartFile file){
		if(file == null){
			return null;
		}
		String filePath = getFilePath(DataUtil.getCurrentDate("yyyyMMdd"));
		File fileDirectory = new File(filePath);
		//当天的目录不存在，则返回
		if(!fileDirectory.exists()){
			fileDirectory.mkdirs();
		}
		filePath = filePath + DataUtil.getCurrentTimestamp() + file.getOriginalFilename();
		try {
			file.transferTo(new File(filePath));
		} catch (Exception e) {
			return null;	//出现异常，返回null
		}
		FileMgr fileMgr = new FileMgr(DataUtil.getUUID(), file.getOriginalFilename(), filePath, DataUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		return fileMgr;
	}
	/**
	 * 删除某个文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
			return true;
		}
		return false;
	}
	/**
	 * 下载文件
	 * @param response
	 * @param fileId
	 */
	public static void download(HttpServletResponse response, String fileId){
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		FileMgrService fileMgrService = (FileMgrService) context.getBean("fileMgrService");
		FileMgr fileMgr = fileMgrService.find(fileId);
		if(fileMgr != null){
            File file = new File(fileMgr.getLocation());
            OutputStream out = null;
            if(file.exists()){
                 try {
                      //指定下载的文件类型：二进制流 .*
                      response.setContentType("application/octet-stream;charset=UTF-8");
                      //确保下载的文件名正常显示
                      response.setHeader("Content-Disposition","attachment;filename*=UTF-8''"+DataUtil.urlEncodeUTF8(fileMgr.getFileName()));//使用URLEncoder.encode()
                      byte[] b = FileUtils.readFileToByteArray(file);
                      out = response.getOutputStream();
                      out.write(b);
                 } catch (IOException e) {
                      e.printStackTrace();
                 } finally{
                      if(out != null){
                           try {
                                out.close();
                           } catch (IOException e) {
                                e.printStackTrace();
                           }
                      }
                 }
            }
        }

	}
}
