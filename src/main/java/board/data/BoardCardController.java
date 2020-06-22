package board.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import spring.waregg.controller.LocalIPAddress;
import upload.util.BoardDirectoryManagement;

@RestController
@CrossOrigin
public class BoardCardController {
	@Autowired
	private BoardCardService bcs;
	
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
	
	//이미지 검색
	@PostMapping("/boardsearchimg")
	@ResponseBody
	public Map<String, Object> searchImg(@RequestBody Map<String, Object> map){
		List<String> list = new ArrayList<String>();
		String search = map.get("search").toString();
		list = bcs.searchImgFile(search);
		if(list.size() != 0) {
			for(int i=0; i<list.size();i++) {
				String img = list.get(i);
				img = ROOTPATH+"bcard/img/"+img;
				list.set(i, img);
			}
		}
		map.put("list",list);
		return map;
	}
	
	//이미지 검색한 것 선택시
	@PostMapping("/boardsearchimgclick")
	public Map<String,String> searchImgMove(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		Map<String,String> returnMap = new HashMap<String, String>();
		
		String no = map.get("no").toString();
		String src = map.get("src").toString();
		src = src.substring(src.lastIndexOf("/")+1,src.length());
		//파일 복사
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		String fileName = dm.moveImgToTempFolder(request, no, src);
		
		returnMap.put("imgSrc", ROOTPATH+"bcard/temp/"+no+"/"+fileName);
		returnMap.put("img", fileName);
		return returnMap;
	}
	
	@GetMapping("/board/boardcarddatas")
	public List<BoardCardDto> getAlldatas(@RequestParam String board_no){
		System.out.println(board_no);
		return bcs.getAllDatas(board_no);
	}
}
