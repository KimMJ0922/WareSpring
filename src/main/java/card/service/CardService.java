package card.service;

import java.util.List;
import java.util.Map;

import card.dto.CardAllDTO;
import card.dto.CardDTO;

public interface CardService {
	//카드 테이블에 넣기
	public void insertCard(CardDTO dto);
	//검색하기
	public List<String> searchImgFile(String search);
	//세트 번호에 있는 카드 목록 가져오기
	public List<CardDTO> getCardList(Map<String, Object> map);
	
}
