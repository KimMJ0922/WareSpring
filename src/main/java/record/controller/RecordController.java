package record.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import record.dto.StudyDTO;
import record.service.RecordService;

@RestController
@CrossOrigin
public class RecordController{
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
//		List<RecordDTO> rlist = rs.getList(map);
//		List<RecordDTO> lastList = rs.getLastList(map);
//		List<RecordDTO> chartList = rs.getChartList(map);
//		
//		map.put("lastList",lastList);
//		map.put("rlist",rlist);
//		map.put("chartList",chartList);
		
		List<RecordDTO> allList = rs.getAllList(map);
		List<Integer> boardNo = new ArrayList<Integer>();
		List<Integer> cardSetNo = new ArrayList<Integer>();

		for(RecordDTO dto : allList) {
			if(dto.getCategory().equals("cardset")) {
				cardSetNo.add(dto.getNo());
			}else {
				boardNo.add(dto.getNo());
			}
		}
		
		map.put("cardSetNo", cardSetNo);
		map.put("boardNo", boardNo);
		
		allList.clear();
		
		if(cardSetNo.size() != 0) {
			allList.addAll(rs.getCardSetRecordList(map));
		}
		
		if(boardNo.size() != 0) {
			allList.addAll(rs.getBoardRecordList(map));
		}
		
		//최근꺼
		List<RecordDTO> lastList = rs.getLastList(map);
		cardSetNo.clear();
		boardNo.clear();
		
		for(RecordDTO dto : lastList) {
			if(dto.getCategory().equals("cardset")) {
				cardSetNo.add(dto.getNo());
			}else {
				boardNo.add(dto.getNo());
			}
		}
		
		map.put("cardSetNo", cardSetNo);
		map.put("boardNo", boardNo);
		
		lastList.clear();
		
		if(cardSetNo.size() != 0) {
			lastList.addAll(rs.getCardSetRecordList(map));
		}
		
		if(boardNo.size() != 0) {
			lastList.addAll(rs.getBoardRecordList(map));
		}
		
		List<RecordDTO> chartList = rs.getChartList(map);
		
		map.put("lastList",lastList);
		map.put("rlist",allList);
		map.put("chartList",chartList);
		
		return map;
	}
	
	@PostMapping("/getdiagram")
	@ResponseBody
	public Map<String,Object> getDiagram(@RequestBody Map<String, Object> map){
		List<RecordDTO> diagram = rs.getDiagram(map);
		List<RecordDTO> last = rs.getLast(map);
		List<RecordDTO> chart = rs.getChart(map);
		
		map.put("last",last);
		map.put("diagram",diagram);
		map.put("chart",chart);
		return map;
	}
	
	@PostMapping("/getdetailrecordlist")
	@ResponseBody
	public Map<String,Object> getDetailRecordList(@RequestBody Map<String, Object> map){
		List<DetailRecordDTO> dlist = rs.getDetailRecordList(map);

		map.put("dlist", dlist);
		return map;
	}
	
	@PostMapping("/getstudylist")
	@ResponseBody
	public Map<String,Object> getStudyList(@RequestBody Map<String, Object> map){
		List<StudyDTO> slist = rs.getStudyList(map);
		List<Integer> cardsetNo = new ArrayList<Integer>();
		List<Integer> boardNo = new ArrayList<Integer>();
		int cardsetCnt = 0;
		int boardCnt = 0;
		
		//카드 세트와 장터 번호 나누기
		for(StudyDTO sdto : slist) {
			if(sdto.getCategory().equals("cardset")) {
				cardsetNo.add(cardsetCnt, sdto.getNo());
				cardsetCnt++;
			}else {
				boardNo.add(boardCnt, sdto.getNo());
				boardCnt++;
			}
		}

		List<StudyDTO> list = new ArrayList<StudyDTO>();
		//각각 가져오기
		if(boardNo.size() != 0) {
			//list 형태를 파라미터로 사용하기 위해선 map이나 hashmap에 넣고 보내야함.
			map.put("boardNo", boardNo);
			List<StudyDTO> boardList = rs.getStudyBoardList(map);
			//하나로 합치기
			list.addAll(boardList);
		}
		
		if(cardsetNo.size() != 0) {
			//list 형태를 파라미터로 사용하기 위해선 map이나 hashmap에 넣고 보내야함.
			map.put("cardsetNo", cardsetNo);
			List<StudyDTO> cardsetList = rs.getStudyCardSetList(map);
			//하나로 합치기
			list.addAll(cardsetList);
		}

		//날짜순으로 정렬
		Sorting sorting = new Sorting();
		Collections.sort(list, sorting);
		map.put("slist", list);
		return map;
	}
	
	class Sorting implements Comparator<StudyDTO>{
		@Override
		public int compare(StudyDTO o1, StudyDTO o2) {
			// TODO Auto-generated method stub
			int no1 = o1.getNo();
			int no2 = o2.getNo();
            
            if(no2 == no1) return 0;
            else if(no2 > no1) return 1;
            else return -1;
		}
	}
}
