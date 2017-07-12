package proj.platform.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 处理数据的工具类
 */
public class DataUtil {
	public static final String FORMAT_DATE = "yyyyMMdd";
	public static final String FORMAT_DATE_SEP = "yyyy-MM-dd";

	private static Logger log = Logger.getLogger(DataUtil.class);
	private static Gson gson = new GsonBuilder().create();

	public static void reply(ServletResponse response, Object result) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(gson.toJson(result));
			log.info(gson.toJson(result));
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * 指定格式获得当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}

	/**
	 * 获得当前时间戳
	 * 
	 * @return
	 */
	public static String getCurrentTimestamp() {
		return String.valueOf(new Date().getTime());
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * UTF-8编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return source;
		}
		return result;
	}

	/**
	 * UTF-8解码
	 * 
	 * @param url
	 * @return
	 */
	public static String urlDecodeUTF8(String url) {
		String result = url;
		try {
			result = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
		return result;
	}

    /**      
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException      
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**      
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException      
     */
    public static String getRequestPostStr(HttpServletRequest request) throws IOException{
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
	
}
