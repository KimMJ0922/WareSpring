package board.data;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import card.dto.CardDTO;

@RestController
@CrossOrigin
public class BoardController {
	@Autowired
	private BoardMapper mapper;
	
	@PostMapping("/bminsert")
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
		System.out.println(" title : "+obj2.get("title"));
		System.out.println(" comment : "+obj2.get("comment"));
		System.out.println(" openPassword : "+obj2.get("openPassword"));
		System.out.println(" updatePassword : "+obj2.get("updatePassword"));
		JSONArray arr = (JSONArray) obj2.get("rows");
		System.out.println(arr);
		System.out.println(arr.size());
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
}
