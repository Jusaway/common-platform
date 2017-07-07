package proj.platform.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import proj.platform.entity.Notice;
import proj.platform.entity.NoticeSrchVal;
import proj.platform.entity.Result;
import proj.platform.service.NoticeService;
import proj.platform.util.DataUtil;
import proj.platform.util.Status;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	private Result result;
	
	@RequestMapping("/add.do")
	public void add(HttpServletResponse response, Notice notice){
		result = new Result();
		noticeService.add(notice);
		
		result.setNotice(notice);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/find.do")
	public void find(HttpServletRequest request, HttpServletResponse response){
		result = new Result();
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<Notice> notices = noticeService.findAll(startRow, pageSize);
		
		result.setNotices(notices);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/findByNg.do")
	public void findByNg(HttpServletRequest request, HttpServletResponse response){
		result = new Result();
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<Notice> notices = noticeService.findAll(startRow, pageSize);
		
		result.setNotices(notices);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/findByJson.do")
	public void findByJson(HttpServletResponse response, @RequestBody Map<String,Integer> map){
		System.out.println(map);
		Integer startRow = map.get("startRow");
		Integer pageSize = map.get("pageSize");
		List<Notice> notices = noticeService.findAll(startRow, pageSize);
		
		result = new Result();
		result.setNotices(notices);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/findNeedParse.do")
	public void findNeedParse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String data = IOUtils.toString(request.getReader());
		JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
		System.out.println("从Request Payload获得数据");
		int startRow = jsonObject.get("startRow").getAsInt();
		int pageSize = jsonObject.get("pageSize").getAsInt();
		
		List<Notice> notices = noticeService.findAll(startRow, pageSize);
		result = new Result();
		result.setNotices(notices);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/findBySrchVal.do")
	public void findBySrchVal(HttpServletResponse response, @RequestBody NoticeSrchVal srchVal){
		System.out.println(srchVal);
		Integer startRow = srchVal.getStartRow();
		Integer pageSize = srchVal.getPageSize();
		
		List<Notice> notices = noticeService.findAll(startRow, pageSize);
		result = new Result();
		result.setNotices(notices);
		DataUtil.reply(response, result);
	}
	@RequestMapping("/updateOrder.do")
	public void updateOrder(HttpServletResponse response,
			@RequestParam("ids[]") Integer[] ids,
			@RequestParam("queryOrders[]") Integer[] queryOrders){
		result = new Result();
		noticeService.updateList(ids, queryOrders);
		
		result.setStatus(Status.SUCCESS);
		DataUtil.reply(response, result);
	}	
}
