package card.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import card.service.CardService;
import spring.waregg.controller.LocalIPAddress;
import upload.util.DirectoryManagement;

@RestController
@CrossOrigin
public class CardController {
	
	@Autowired
	private CardService cs;
	
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
	
	//이미지 검색
	@PostMapping("/searchimg")
	@ResponseBody
	public Map<String, Object> searchImg(@RequestBody Map<String, Object> map){
		List<String> list = new ArrayList<String>();
		String search = map.get("search").toString();
		list = cs.searchImgFile(search);
		if(list.size() != 0) {
			for(int i=0; i<list.size();i++) {
				String img = list.get(i);
				img = ROOTPATH+"card/img/"+img;
				list.set(i, img);
			}
		}
		map.put("list",list);
		return map;
	}
	
	//이미지 검색한 것 선택시
	@PostMapping("/searchimgclick")
	public Map<String,String> searchImgMove(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		Map<String,String> returnMap = new HashMap<String, String>();
		
		String no = map.get("no").toString();
		String src = map.get("src").toString();
		src = src.substring(src.lastIndexOf("/")+1,src.length());
		//파일 복사
		DirectoryManagement dm = new DirectoryManagement();
		String fileName = dm.moveImgToTempFolder(request, no, src);
		
		returnMap.put("imgSrc", ROOTPATH+"card/temp/"+no+"/"+fileName);
		returnMap.put("img", fileName);
		return returnMap;
	}
}
