package proj.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import proj.platform.entity.UserInfoDetail;
import proj.platform.util.FileMgrUtil;

@Controller
@RequestMapping("/test")
public class FunctionTestController {
	@RequestMapping("/testPath.do")
	public @ResponseBody String testPath(HttpServletRequest request, HttpServletResponse response){
		return FileMgrUtil.getWebRootPath();
	}
	@RequestMapping("/testEdit.do")
	public void testEdit(HttpServletRequest request,
			HttpServletResponse response,
			UserInfoDetail detail,
			MultipartFile file){
		
	}
}
