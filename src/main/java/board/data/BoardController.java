package board.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import card.dto.CardDTO;
import card.dto.CardSetDTO;
import spring.waregg.controller.LocalIPAddress;
import upload.util.BoardDirectoryManagement;
import upload.util.DirectoryManagement;
import upload.util.SpringFileWrite;

@RestController
@CrossOrigin
public class BoardController {
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
	
	@Autowired
	private BoardService bservice;
	@Autowired
	private BoardCardService bcservice;
	
	@PostMapping("/board/insert")
	public int insert(@RequestBody HashMap<String,Object> cardMap,HttpServletRequest request) {
//		System.out.println(cardMap);

		JSONObject jsonObject = new JSONObject(cardMap);
		String card = jsonObject.toJSONString();
		JSONObject obj2 = (JSONObject)JSONValue.parse(card);
		
		BoardDto bdto = new BoardDto();
		bdto.setNo(obj2.get("no").toString());
		bdto.setSubject(obj2.get("title").toString());
		bdto.setContent(obj2.get("comment").toString());
		bdto.setRequirepoint(Integer.parseInt(obj2.get("point").toString()));		
		
		bservice.BoardInsert(bdto);
		int board_no = bservice.getInsertNum(obj2.get("no").toString());
		
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		//해당 계정이 작성중인 이미지 임시 폴더
		String path = request.getSession().getServletContext().getRealPath("/");
		JSONArray arr = (JSONArray) obj2.get("rows");
		
		for(int i=0; i<arr.size(); i++){
			BoardCardDto bcdto = new BoardCardDto();
			JSONObject obj = (JSONObject)arr.get(i);
			bcdto.setCardset_no(board_no);
			bcdto.setQuestion_no(Integer.parseInt(obj.get("id").toString()));
			bcdto.setQuestion(obj.get("question").toString());
			bcdto.setAnswer(obj.get("answer").toString());
			String img = obj.get("img").toString();
			if(img == null || img.equals("")) {
				img = "";
				bcdto.setImgFile(img);
			}else {
				//이미지 파일 옮기면서 파일명 바꾸기
				String imgFileName = dm.moveTempToImgFolder(request, Integer.parseInt(bdto.getNo()), board_no, i+1, img);
				
				bcdto.setImgFile(imgFileName);
			}
			bcservice.BoardCardInsert(bcdto);
		}
		dm.deleteFile(request, Integer.parseInt(bdto.getNo()));
		dm.deleteTempFolder(request, Integer.parseInt(bdto.getNo()), path);
		return board_no;
	}
	
	@GetMapping("board/getIp")
	public String getIp() {
		return ROOTPATH;
	}
	@PostMapping("/board/list")
	public List<BoardDto> list(@RequestBody HashMap<String, Object> map) {
		int startNum = 0;
		int amount = 9;//한페이지에 보여줄 게시물 수
		int pageNum=Integer.parseInt(map.get("pageNum").toString());
		if(pageNum!=1 && pageNum !=0) {
			startNum=(pageNum-1)*amount;
		}
		map.put("startNum", startNum);
		map.put("amount", amount);
		System.out.println(bservice.list(map));
		return bservice.list(map);
	}
	
	@GetMapping("/board/count")
	public int BoardCount(@RequestParam String search) { 
		return bservice.count(search);
	}
	
	@GetMapping("/board/getdata")
	public List<BoardDto> getData(@RequestParam String board_no){
		return bservice.getData(board_no); 
	}
	
	@GetMapping("board/updateReadcount")
	public void updateReadcount(@RequestParam String board_no) {
		bservice.updateReadcount(board_no);
	}
	
	@RequestMapping(value = "/questionimgupload", 
			consumes = {"multipart/form-data"}, 
			method = RequestMethod.POST)
    public Map<String, String> uploadProfileImg(
    		@RequestParam MultipartFile uploadFile,
    		MultipartHttpServletRequest request,
    		@RequestParam String no
    ) {
		String path = request.getSession().getServletContext().getRealPath("/bcard/temp/");
		//임시 폴더 안에 계정의 번호로 된 폴더 생성
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		dm.createTempFolder(request, no, path);
    	
		//폴더 생성 및 확인 후
		path = request.getSession().getServletContext().getRealPath("/bcard/temp/"+no+"/");
    	SpringFileWrite sfw = new SpringFileWrite();
		String fileName = sfw.fileUpload(uploadFile, path);
		Map<String, String> map = new HashMap<String, String>();
		map.put("img", fileName);
		map.put("imgSrc", ROOTPATH+"bcard/temp/"+no+"/"+fileName);
		return map;
    }
	
	@GetMapping("board/delete")
	public void deleteBoard(HttpServletRequest request, @RequestParam String board_no) {
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		dm.deleteImgFile(request, board_no);
		bservice.deleteBoard(board_no);
	}
	
	@GetMapping("/currentPoint")
	public int currentPoint(@RequestParam String member_no) {
		return bservice.currentPoint(member_no);
	}
	
	@PostMapping("board/buy")
	public void buy(@RequestBody HashMap<String, Object> map) {
		System.out.println(map);
		String board_no = map.get("board_no").toString();
		String member_no = map.get("member_no").toString();
		String no = map.get("no").toString();
		int requirepoint = Integer.parseInt(map.get("requirepoint").toString());
		
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("requirepoint", requirepoint);
		map1.put("member_no", member_no);
		bservice.updateMemberPoint(map1);
		
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("requirepoint", requirepoint);
		map2.put("member_no", member_no);
		map2.put("category", "-");
		bservice.pointHistoryOfBoard(map2);
		
		int insert_no=bservice.getHistoryNum();
		
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("no", insert_no);
		map5.put("board_no", board_no);
		map5.put("member_no", member_no);
		bservice.buyBoard(map5);
		
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("requirepoint", requirepoint);
		map3.put("no", no);
		bservice.updatePlusMemberPoint(map3);
		
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("requirepoint", requirepoint);
		map4.put("member_no", no);
		map4.put("category", "+");
		bservice.pointHistoryOfBoard(map4);		
	}
	
	@PostMapping("board/buyedcheck")
	public int buyedcheck(@RequestBody HashMap<String, Object> map) {
		String board_no = map.get("board_no").toString();
		String member_no = map.get("member_no").toString();
		
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		
		map2.put("board_no", board_no);
		map2.put("member_no", member_no);
		
		return bservice.buyedcheck(map2);
	}
	
	//카드 세트 수정할 정보 가져오기
	@PostMapping("/board/getcardset")
	public Map<String,Object> getCardSet(@RequestBody Map<String,Object> map, HttpServletRequest request){
		List<BoardCardDto> list = bcservice.getCardList(map);
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		//문제 사진이 있을 경우 temp폴더로 복사 후 list안에 사진 파일명 변경
		for(int i =0;i<list.size();i++) {
			BoardCardDto dto = list.get(i);
			String fileName = dto.getImgFile();
			if(fileName == null || fileName == "") 
				continue;
			String rootPath = ROOTPATH+"bcard/temp/"+map.get("member_no").toString()+"/";
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
			fileName = dm.moveImgToTempFolder(request, map.get("member_no").toString(), fileName);
			dto.setImgFile(fileName);
			dto.setImgSrc(rootPath+fileName);
			list.set(i, dto);
		}
		map.put("list", list);
		
		return map;
	}
	
	@PostMapping("board/updatecardset")
	public void updateCardSet(@RequestBody Map<String,Object> cardMap,HttpServletRequest request) {
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
		BoardDto dto = new BoardDto();
		
		dto.setBoard_no(Integer.parseInt(obj2.get("board_no").toString()));
		dto.setNo(obj2.get("no").toString());
		dto.setSubject(obj2.get("title").toString());
		dto.setContent(obj2.get("comment").toString());
		dto.setRequirepoint(Integer.parseInt(obj2.get("point").toString()));
		
		bservice.updateBoard(dto);
		
		//기존에 있는 사진 목록 가져오기
		List<String> imgList = bcservice.getImgList(dto.getBoard_no());
		
		//기존에 있는 이미지 목록 지우기
		BoardDirectoryManagement dm = new BoardDirectoryManagement();
		dm.deleteImgFile(request, imgList);
		
		//테이블 레코드 지우기
		bcservice.deleteCard(dto.getBoard_no());
		
		//이미지 바꾸기
		JSONArray arr = (JSONArray) obj2.get("rows");
		//JSONArray로 변환 한 것을 하나씩 꺼내기
		for(int i=0; i<arr.size(); i++){
			BoardCardDto cdto = new BoardCardDto();
			JSONObject obj = (JSONObject)arr.get(i);
			cdto.setQuestion_no(i+1);
			cdto.setCardset_no(dto.getBoard_no());
			cdto.setQuestion(obj.get("question").toString());
			cdto.setAnswer(obj.get("answer").toString());
			String img = obj.get("img").toString();
			System.out.println(img);
			if(img == null || img.equals("")) {
				img = "";
				cdto.setImgFile(img);
			}else {
				//이미지 파일 옮기면서 파일명 바꾸기
				String imgFileName = dm.moveTempToImgFolder(request, Integer.parseInt(dto.getNo()), dto.getBoard_no(), i+1, img);
				
				cdto.setImgFile(imgFileName);
			}			
			bcservice.insertCard(cdto);
		}
		
		dm.deleteFile(request, Integer.parseInt(dto.getNo()));
		dm.deleteTempFolder(request, Integer.parseInt(dto.getNo()));
	}
	
	@PostMapping("/board/sellList")
	public List<BoardDto> sellList(@RequestBody HashMap<String, Integer> map){
		return bservice.getSellBoardOfSet(map);
	}
	
	@GetMapping("/board/sellListCount")
	public int sellListCount(@RequestParam String no){
		System.out.println(no);
		return bservice.getSellListCount(no);
	}
}
