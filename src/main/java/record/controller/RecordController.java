package record.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;
import record.service.RecordService;

@RestController
@CrossOrigin
public class RecordController {
	@Autowired
	private RecordService rs;
	
	//객관, 주관, 테스트 결과 저장
	@PostMapping("/setrecord")
	public void setRecord(@RequestBody Map<String, Object> map) {
		RecordDTO rdto = new RecordDTO();
		rdto.setCardset_no(Integer.parseInt(map.get("cardset_no").toString()));
		rdto.setCategory(map.get("category").toString());
		rdto.setMethod(map.get("method").toString());
		rdto.setMember_no(Integer.parseInt(map.get("member_no").toString()));
		rdto.setRightcnt(Integer.parseInt(map.get("rightcnt").toString()));
		rdto.setWrongcnt(Integer.parseInt(map.get("wrongcnt").toString()));
		
		rs.insertRecord(rdto);
		int no = rs.getMaxNo(rdto);

		//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(map);
		
		//JSON 형태의 string 으로 변환
		String record = jsonObject.toJSONString();
		
		//string으로 변환한 것을 다시 JSONObject로 변환
		JSONObject obj2 = (JSONObject)JSONValue.parse(record);
		JSONArray right = (JSONArray) obj2.get("right");
		JSONArray wrong = (JSONArray) obj2.get("wrong");
		
		for(int i=0; i<right.size(); i++) {
			DetailRecordDTO ddto = new DetailRecordDTO();
			JSONObject obj = (JSONObject)right.get(i);
			ddto.setRecord_no(no);
			ddto.setQuestion_no(Integer.parseInt(obj.get("question_no").toString()));
			ddto.setQuestion(obj.get("question").toString());
			ddto.setAnswer(obj.get("answer").toString());
			ddto.setUseranswer(obj.get("userAnswer").toString());
			ddto.setType(obj.get("category").toString());
			ddto.setResult("정답");
			
			rs.insertDetailRecord(ddto);
		}
		
		for(int i=0; i<wrong.size(); i++) {
			DetailRecordDTO ddto = new DetailRecordDTO();
			JSONObject obj = (JSONObject)wrong.get(i);
			ddto.setRecord_no(no);
			ddto.setQuestion_no(Integer.parseInt(obj.get("question_no").toString()));
			ddto.setQuestion(obj.get("question").toString());
			ddto.setAnswer(obj.get("answer").toString());
			ddto.setUseranswer(obj.get("userAnswer").toString());
			ddto.setType(obj.get("category").toString());
			ddto.setResult("오답");
			
			rs.insertDetailRecord(ddto);
		}
	}
	
	//학습 저장
	@PostMapping("/setstudy")
	public void setStudy(@RequestBody Map<String, Object> map) {
		rs.insertStudy(map);
	}
	
	@PostMapping("/getdiagramlist")
	@ResponseBody
	public Map<String,Object> getDiagramList(@RequestBody Map<String, Object> map){
		List<RecordDTO> rlist = rs.getList(map);
		List<RecordDTO> lastList = rs.getLastList(map);
		
		map.put("lastList",lastList);
		map.put("rlist",rlist);
		return map;
	}
}
