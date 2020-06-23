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

import spring.waregg.controller.LocalIPAddress;
import upload.util.BoardDirectoryManagement;
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
	@GetMapping("/board/list")
	public List<BoardDto> list(@RequestParam int pageNum) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startNum = 0;
		int amount = 9;//한페이지에 보여줄 게시물 수 
		if(pageNum!=1 && pageNum !=0) {
			startNum=(pageNum-1)*amount;
		}
		map.put("startNum", startNum);
		map.put("amount", amount);
		return bservice.list(map);
	}
	
	@GetMapping("/board/count")
	public int BoardCount() { 
		return bservice.count();
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
}
