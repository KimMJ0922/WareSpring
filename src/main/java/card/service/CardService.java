package card.service;

import java.util.List;
import java.util.Map;

import card.dto.CardDTO;

public interface CardService {
	//카드 테이블에 넣기
	public void insertCard(CardDTO dto);
	//검색하기
	public List<String> searchImgFile(String search);
	//세트 번호에 있는 카드 목록 가져오기
	public List<CardDTO> getCardList(Map<String, Object> map);
	//이미지 리스트 가져오기
	public List<String> getImgList(int no);
	//카드 지우기
	public void deleteCard(int no);
	//카드 세트의 카드 갯수 가져오기
	public int getCardSetCount(Map<String, Object> map);
	//테스트 카드 세트 가져오기
	public List<CardDTO> getTestCardList(Map<String, Object> map);
	
}
