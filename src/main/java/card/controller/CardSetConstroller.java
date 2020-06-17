package card.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import card.dto.CardDTO;
import card.mapper.CardMapper;
import card.service.CardSetService;
import spring.waregg.controller.LocalIPAddress;
import upload.util.DirectoryManagement;
import upload.util.SearchFile;
import upload.util.SpringFileWrite;

@RestController
@CrossOrigin
public class CardSetConstroller {
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
	@Autowired
	private CardSetService cardSetService;
	
	@PostMapping("/insert")
	public void insert(@RequestBody HashMap<String,Object> cardMap) {
		System.out.println(cardMap);

		//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(cardMap);
		
		//JSON 형태의 string 으로 변환
		String card = jsonObject.toJSONString();
		
		//string으로 변환한 것을 다시 JSONObject로 변환
		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
		//변환 오류가 생긴다. 이유는 모름
		JSONObject obj2 = (JSONObject)JSONValue.parse(card);
		
		//cardset 테이블에 넣을 map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", obj2.get("title"));
		map.put("comment", obj2.get("comment"));
		map.put("openPassword", obj2.get("openPassword"));
		map.put("updatePassword", obj2.get("updatePassword"));
		map.put("openScope", obj2.get("openScope"));
		map.put("updateScope", obj2.get("updateScope"));
		
		//cardset에 넣기
		JSONArray arr = (JSONArray) obj2.get("rows");
		//JSONArray로 변환 한 것을 하나씩 꺼내기
		for(int i=0; i<arr.size(); i++){
			CardDTO dto = new CardDTO();
			JSONObject obj = (JSONObject)arr.get(i);
			System.out.println(obj.get("id"));
			System.out.println(obj.get("question"));
			System.out.println(obj.get("answer"));
			System.out.println(obj.get("img"));
			System.out.println("---------------------------------");
			
			
		}
	}
	
//	@PostMapping("/binsert")
//	public void binsert(@RequestBody HashMap<String,Object> cardMap) {
//		System.out.println(cardMap);
//
//		//JSON Object로 변환
//		JSONObject jsonObject = new JSONObject(cardMap);
//		
//		//JSON 형태의 string 으로 변환
//		String card = jsonObject.toJSONString();
//		
//		//string으로 변환한 것을 다시 JSONObject로 변환
//		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
//		//변환 오류가 생긴다. 이유는 모름
//		JSONObject obj2 = (JSONObject)JSONValue.parse(card);
//		System.out.println(" title : "+obj2.get("title"));
//		System.out.println(" comment : "+obj2.get("comment"));
//		System.out.println(" openPassword : "+obj2.get("openPassword"));
//		System.out.println(" updatePassword : "+obj2.get("updatePassword"));
//		JSONArray arr = (JSONArray) obj2.get("rows");
//		System.out.println(arr);
//		System.out.println(arr.size());
//		//JSONArray로 변환 한 것을 하나씩 꺼내기
//		for(int i=0; i<arr.size(); i++){
//			CardDTO dto = new CardDTO();
//			JSONObject obj = (JSONObject)arr.get(i);
//			System.out.println(obj.get("id"));
//			System.out.println(obj.get("question"));
//			System.out.println(obj.get("answer"));
//			System.out.println(obj.get("img"));
//			System.out.println("---------------------------------");
//			
//		}
//	}
	
	
	//문제 이미지 추가
	@RequestMapping(value = "/uploadquestionimgupload", 
			consumes = {"multipart/form-data"}, 
			method = RequestMethod.POST)
    public String uploadProfileImg(
    		@RequestParam MultipartFile uploadFile,
    		MultipartHttpServletRequest request,
    		@RequestParam String no
    ) {
		String path = request.getSession().getServletContext().getRealPath("/card/temp/");
		//임시 폴더 안에 계정의 번호로 된 폴더 생성
		DirectoryManagement dm = new DirectoryManagement();
		dm.createTempFolder(request, no, path);
    	
		//폴더 생성 및 확인 후
		path = request.getSession().getServletContext().getRealPath("/card/temp/"+no+"/");
    	SpringFileWrite sfw = new SpringFileWrite();
		sfw.writeFile(uploadFile, path);
		
		return ROOTPATH+"card/temp/"+no+"/"+uploadFile.getOriginalFilename();
    }
}
