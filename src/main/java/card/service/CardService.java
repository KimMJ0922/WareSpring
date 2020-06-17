package card.service;

import java.util.List;

import card.dto.CardDTO;

public interface CardService {
	//카드 테이블에 넣기
	public void insertCard(CardDTO dto);
	//검색하기
	public List<String> searchImgFile(String search);
}
