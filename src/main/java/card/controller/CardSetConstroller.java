package card.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import card.dto.CardDTO;
import card.dto.CardSetDTO;
import card.dto.SearchDTO;
import card.service.CardService;
import card.service.CardSetService;
import spring.waregg.controller.LocalIPAddress;
import upload.util.DirectoryManagement;
import upload.util.SpringFileWrite;

@RestController
@CrossOrigin
public class CardSetConstroller {
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
	@Autowired
	private CardSetService cardSetService;
	@Autowired
	private CardService cardService;
	@PostMapping("/insert")
	public void insert(@RequestBody HashMap<String,Object> cardMap,HttpServletRequest request) {
		//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(cardMap);
		
		//JSON 형태의 string 으로 변환
		String card = jsonObject.toJSONString();
		
		//string으로 변환한 것을 다시 JSONObject로 변환
		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
		//변환 오류가 생긴다. 이유는 모름
		JSONObject obj2 = (JSONObject)JSONValue.parse(card);

		//cardset 테이블에 넣을 map
		CardSetDTO dto = new CardSetDTO();
		
		dto.setNo(Integer.parseInt(obj2.get("no").toString()));
		dto.setTitle(obj2.get("title").toString());
		dto.setComment(obj2.get("comment").toString());
		dto.setOpen_password(obj2.get("openPassword").toString());
		dto.setUpdate_password(obj2.get("updatePassword").toString());
		dto.setOpen_scope(obj2.get("openScope").toString());
		dto.setUpdate_scope(obj2.get("updateScope").toString());
		
		//cardset에 넣기
		cardSetService.insertCardSet(dto);
		int cardSetNo = cardSetService.getCardSetNo(dto.getNo());
		
		DirectoryManagement dm = new DirectoryManagement();
		//해당 계정이 작성중인 이미지 임시 폴더
		String path = request.getSession().getServletContext().getRealPath("/");
		JSONArray arr = (JSONArray) obj2.get("rows");
		//JSONArray로 변환 한 것을 하나씩 꺼내기
		for(int i=0; i<arr.size(); i++){
			CardDTO cdto = new CardDTO();
			JSONObject obj = (JSONObject)arr.get(i);
			cdto.setQuestion_no(i+1);
			cdto.setCardset_no(cardSetNo);
			cdto.setQuestion(obj.get("question").toString());
			cdto.setAnswer(obj.get("answer").toString());
			String img = obj.get("img").toString();
			if(img == null || img.equals("")) {
				img = "";
				cdto.setImgFile(img);
			}else {
				//이미지 파일 옮기면서 파일명 바꾸기
				String imgFileName = dm.moveTempToImgFolder(request, dto.getNo(), cardSetNo, i+1, img);
				
				cdto.setImgFile(imgFileName);
			}			
			cardService.insertCard(cdto);
		}
		
		dm.deleteFile(request, dto.getNo());
		dm.deleteTempFolder(request, dto.getNo());
	}
	
	//문제 이미지 추가
	@RequestMapping(value = "/uploadquestionimgupload", 
			consumes = {"multipart/form-data"}, 
			method = RequestMethod.POST)
    public Map<String, String> uploadProfileImg(
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
		String fileName = sfw.fileUpload(uploadFile, path);
		Map<String, String> map = new HashMap<String, String>();
		map.put("img", fileName);
		map.put("imgSrc", ROOTPATH+"card/temp/"+no+"/"+fileName);
		return map;
    }
	
	//세트 페이지 리스트 출력
	@PostMapping("/getcardsetlist")
	@ResponseBody
	public List<CardSetDTO> getCardSetList(@RequestBody Map<String,Object> map){
		List<CardSetDTO> list = new ArrayList<CardSetDTO>();
		list = cardSetService.getCardSetList(map);
		return list;
	}
	
	
	//메뉴에 세트 갯수 출력
	@PostMapping("/getsetcount")
	@ResponseBody
	public int getSetCount(@RequestBody Map<String,Object> map) {
		return cardSetService.getSetCount(map);
	}
	
	//카드세트 비밀번호 확인
	@PostMapping("/cardsetpasscheck")
	public boolean passCheck(@RequestBody Map<String,Object> map) {
		return cardSetService.passCheck(map);
	}
	
	//카드 삭제
	@PostMapping("/deletecardset")
	public void deleteCardSet(@RequestBody Map<String,Object> map) {
		cardSetService.deleteCardSet(map);
	}
	
	
	//업데이트 비밀번호 확인
	@PostMapping("/updatepasscheck")
	public boolean updatePassCheck(@RequestBody Map<String,Object> map) {
		return cardSetService.updatePassCheck(map);
	}
	
	//카드 세트 수정할 정보 가져오기
	@PostMapping("/getcardset")
	public Map<String,Object> getCardSet(@RequestBody Map<String,Object> map, HttpServletRequest request){
		CardSetDTO csdto = cardSetService.getCardSet(map);
		List<CardDTO> list = cardService.getCardList(map);
		DirectoryManagement dm = new DirectoryManagement();
		//문제 사진이 있을 경우 temp폴더로 복사 후 list안에 사진 파일명 변경
		for(int i =0;i<list.size();i++) {
			CardDTO dto = list.get(i);
			String fileName = dto.getImgFile();
			if(fileName == null || fileName == "") 
				continue;
			String rootPath = ROOTPATH+"card/temp/"+map.get("member_no").toString()+"/";
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
			fileName = dm.moveImgToTempFolder(request, map.get("member_no").toString(), fileName);
			dto.setImgFile(fileName);
			dto.setImgSrc(rootPath+fileName);
			list.set(i, dto);
		}
		map.put("csdto", csdto);
		map.put("list", list);
		
		return map;
	}
	
	
	//카드 세트 수정
	@PostMapping("/updatecardset")
	public void updateCardSet(@RequestBody Map<String,Object> cardMap,HttpServletRequest request) {
		//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(cardMap);
		
		//JSON 형태의 string 으로 변환
		String card = jsonObject.toJSONString();
		
		//string으로 변환한 것을 다시 JSONObject로 변환
		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
		//변환 오류가 생긴다. 이유는 모름
		JSONObject obj2 = (JSONObject)JSONValue.parse(card);

		//cardset 테이블에 넣을 map
		CardSetDTO dto = new CardSetDTO();
		
		dto.setNo(Integer.parseInt(obj2.get("no").toString()));
		dto.setMember_no(Integer.parseInt(obj2.get("member_no").toString()));
		dto.setTitle(obj2.get("title").toString());
		dto.setComment(obj2.get("comment").toString());
		dto.setOpen_password(obj2.get("openPassword").toString());
		dto.setUpdate_password(obj2.get("updatePassword").toString());
		dto.setOpen_scope(obj2.get("openScope").toString());
		dto.setUpdate_scope(obj2.get("updateScope").toString());
		
		cardSetService.updateCardSet(dto);
		
		//기존에 있는 사진 목록 가져오기
		List<String> imgList = cardService.getImgList(dto.getNo());
		
		//기존에 있는 이미지 목록 지우기
		DirectoryManagement dm = new DirectoryManagement();
		dm.deleteImgFile(request, imgList);
		
		//테이블 레코드 지우기
		cardService.deleteCard(dto.getNo());
		
		//이미지 바꾸기
		JSONArray arr = (JSONArray) obj2.get("rows");
		//JSONArray로 변환 한 것을 하나씩 꺼내기
		for(int i=0; i<arr.size(); i++){
			CardDTO cdto = new CardDTO();
			JSONObject obj = (JSONObject)arr.get(i);
			cdto.setQuestion_no(i+1);
			cdto.setCardset_no(dto.getNo());
			cdto.setQuestion(obj.get("question").toString());
			cdto.setAnswer(obj.get("answer").toString());
			String img = obj.get("img").toString();
			System.out.println(img);
			if(img == null || img.equals("")) {
				img = "";
				cdto.setImgFile(img);
			}else {
				//이미지 파일 옮기면서 파일명 바꾸기
				String imgFileName = dm.moveTempToImgFolder(request, dto.getMember_no(), dto.getNo(), i+1, img);
				
				cdto.setImgFile(imgFileName);
			}			
			cardService.insertCard(cdto);
		}
		
		dm.deleteFile(request, dto.getMember_no());
		dm.deleteTempFolder(request, dto.getMember_no());
	}
	
	
	//검색
	@PostMapping("/getcardsetsearchlist")
	@ResponseBody
	public Map<String, Object> getCardSetSearchList(@RequestBody Map<String,Object> map){
		System.out.println(map);
		List<SearchDTO> slist = cardSetService.getCardSetSearchList(map);
		map.put("slist", slist);
		return map;
	}
}
