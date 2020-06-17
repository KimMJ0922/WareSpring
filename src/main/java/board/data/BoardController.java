package board.data;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import card.dto.CardDTO;
import card.mapper.CardMapper;

@RestController
@CrossOrigin
public class BoardController {
	@Autowired
	private BoardMapper bmapper;
	@Autowired
	private CardMapper cmapper;
	
	@PostMapping("/board/insert")
	public String insert(@RequestBody HashMap<String,Object> cardMap) {
//		System.out.println(cardMap);

		JSONObject jsonObject = new JSONObject(cardMap);
		String card = jsonObject.toJSONString();
		JSONObject obj2 = (JSONObject)JSONValue.parse(card);
		
//		System.out.println(" writer : "+obj2.get("email"));
//		System.out.println(" title : "+obj2.get("title"));
//		System.out.println(" comment : "+obj2.get("comment"));
//		System.out.println(" point : "+obj2.get("point"));
		
		BoardDto bdto = new BoardDto();
		bdto.setWriter(obj2.get("email").toString());
		bdto.setSubject(obj2.get("title").toString());
		bdto.setContent(obj2.get("comment").toString());
		bdto.setRequirepoint(Integer.parseInt(obj2.get("point").toString()));		
		
		bmapper.BoardInsert(bdto);
		int board_no = bmapper.getInsertNum(obj2.get("email").toString());
		
		JSONArray arr = (JSONArray) obj2.get("rows");
//		System.out.println(arr);
//		System.out.println(arr.size());
		
		for(int i=0; i<arr.size(); i++){
			CardDTO cdto = new CardDTO();
			JSONObject obj = (JSONObject)arr.get(i);
			cdto.setCardset_no(board_no);
			cdto.setQuestion_no(Integer.parseInt(obj.get("id").toString()));
			cdto.setQuestion(obj.get("question").toString());
			cdto.setAnswer(obj.get("answer").toString());
			cdto.setImgFile(obj.get("img").toString());
			cdto.setCategory("board");
			cmapper.CardInsert(cdto);
//			System.out.println(obj.get("id"));
//			System.out.println(obj.get("question"));
//			System.out.println(obj.get("answer"));
//			System.out.println(obj.get("img"));
		}
		return "redirect:board";
	}
	
	@GetMapping("/board/list")
	public List<BoardDto> list(@RequestParam int pageNum){
		System.out.println("pageNum:"+pageNum);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startNum = 0;
		int amount = 6;//한페이지에 보여줄 게시물 수 
		System.out.println("1startNum:"+startNum);
		if(pageNum!=1 && pageNum !=0) {
			startNum=(pageNum-1)*amount;
		}
		System.out.println("2startNum:"+startNum);
		map.put("startNum", startNum);
		map.put("amount", amount);
		
		return bmapper.list(map);
	}
	
	@GetMapping("/board/count")
	public int BoardCount() { 
		return bmapper.count();
	}
	
	@GetMapping("/board/getdata")
	public List<BoardDto> getData(@RequestParam String board_no){
		System.out.println(board_no);
		return bmapper.getData(board_no); 
	}
}
