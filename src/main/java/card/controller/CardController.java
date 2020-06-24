package card.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import card.dto.CardDTO;
import card.dto.CardSetDTO;
import card.dto.ChoiceDTO;
import card.service.CardService;
import card.service.CardSetService;
import member.dto.MemberDTO;
import member.service.MemberService;
import spring.waregg.controller.LocalIPAddress;
import upload.util.DirectoryManagement;

@RestController
@CrossOrigin
public class CardController {
	
	@Autowired
	private CardService cs;
	@Autowired
	private CardSetService css;
	@Autowired
	private MemberService ms;
	
	
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
	
	//학습 페이지 카드 목록 가져오기
	@PostMapping("/getcardlist")
	@ResponseBody
	public Map<String,Object> getCardList(@RequestBody Map<String, Object> map){

		//카드 리스트
		List<CardDTO> cList = cs.getCardList(map);

		//카드 세트의 정보
		CardSetDTO csdto = css.getCardSet(map);

		//카드 세트 만든 계정의 정보
		MemberDTO mdto = ms.getMember(csdto.getMember_no());

		map.put("mdto", mdto);
		map.put("csdto", csdto);
		map.put("cList", cList);

		return map;
	}
	
	//카드 목록 가져오기
	@PostMapping("/getcardchoicelist")
	@ResponseBody
	public Map<String,Object> getCardChoiceList(@RequestBody Map<String, Object> map){

		//카드 리스트
		List<CardDTO> cList = cs.getCardList(map);
		List<ChoiceDTO> choiceList = new ArrayList<ChoiceDTO>();
		for(CardDTO cdto : cList) {
			int question_no = cdto.getQuestion_no();
			//객관식 만들기 위한 배열
			List<Integer> ranList = new ArrayList<Integer>();
			ranList.add(question_no-1);
			while(true) {
				if(ranList.size() == 4) {
					break;
				}
				double ran = Math.random()*cList.size();
				int rand = (int)ran;
				
				if(ranList.indexOf(rand) == -1) {
					ranList.add(rand);
				}
			}
			
			//랜덤하게 섞기
			Collections.shuffle(ranList);
			String t = "";
			ChoiceDTO codto = new ChoiceDTO();
			for(int i=0; i<ranList.size();i++) {
				CardDTO cddto = cList.get(ranList.get(i));
				switch (i) {
				case 0:
					codto.setAnswer1(cddto.getAnswer());
					break;
				case 1:
					codto.setAnswer2(cddto.getAnswer());
					break;
				case 2:
					codto.setAnswer3(cddto.getAnswer());
					break;
				case 3:
					codto.setAnswer4(cddto.getAnswer());
					break;
				}
			}
			
			choiceList.add(codto);
		}
		
		//카드 세트의 정보
		CardSetDTO csdto = css.getCardSet(map);

		//카드 세트 만든 계정의 정보
		MemberDTO mdto = ms.getMember(csdto.getMember_no());

		map.put("mdto", mdto);
		map.put("csdto", csdto);
		map.put("cList", cList);
		map.put("choiceList", choiceList);
		
		return map;
	}
	
	@PostMapping("/getcardsetcount")
	@ResponseBody
	public int getCardSetCount(@RequestBody Map<String,Object> map) {
		return cs.getCardSetCount(map);
	}
	
	@PostMapping("/gettestlist")
	@ResponseBody
	public Map<String, Object> getTestList(@RequestBody Map<String,Object> map){
		int choice = Integer.parseInt(map.get("choice").toString());
		int subjective = Integer.parseInt(map.get("subjective").toString());
		
		List<CardDTO> cList = cs.getTestCardList(map);
		List<CardDTO> choiceQuestionList = new ArrayList<CardDTO>();
		List<ChoiceDTO> choiceList = new ArrayList<ChoiceDTO>();
		List<CardDTO> subjectiveList = new ArrayList<CardDTO>();
		
		//객관식 갯수에 맞게 for문 돌리기
		for(int i=0; i<choice;i++) {
			CardDTO cdto = cList.get(i);
			choiceQuestionList.add(cdto);
			int question_no = cdto.getQuestion_no();
			//객관식 만들기 위한 배열
			List<Integer> ranList = new ArrayList<Integer>();
			ranList.add(i);
			while(true) {
				if(ranList.size() == 4) {
					break;
				}
				double ran = (Math.random()*cList.size());
				int rand = (int)ran;
				
				if(ranList.indexOf(rand) == -1) {
					ranList.add(rand);
				}
			}
			//랜덤하게 섞기
			Collections.shuffle(ranList);
			String t = "";
			ChoiceDTO codto = new ChoiceDTO();
			for(int j=0; j<ranList.size();j++) {
				CardDTO cddto = cList.get(ranList.get(j));
				switch (j) {
				case 0:
					codto.setAnswer1(cddto.getAnswer());
					break;
				case 1:
					codto.setAnswer2(cddto.getAnswer());
					break;
				case 2:
					codto.setAnswer3(cddto.getAnswer());
					break;
				case 3:
					codto.setAnswer4(cddto.getAnswer());
					break;
				}
			}
			choiceList.add(codto);
		}
		
		//주관식으로 만들기 전에 카드 목록 섞기
		Collections.shuffle(cList);
		//주관식 랜덤으로 뽑기
		for(int k=0; k<subjective;k++) {
			subjectiveList.add(cList.get(k));
		}
		
		map.put("choiceQuestionList",choiceQuestionList);
		map.put("choiceList", choiceList);
		map.put("subjectiveList", subjectiveList);

		return map;
	}
}
