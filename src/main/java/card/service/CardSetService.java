package card.service;

import java.util.List;
import java.util.Map;

import card.dto.CardDTO;
import card.dto.CardSetDTO;

public interface CardSetService {
	//카드세트에 값 넣기
	public void insertCardSet(CardSetDTO dto);
	//해당 카드세트 번호 가져오기
	public int getCardSetNo(int no);
	//카드 세트 목록 가져오기
	public List<CardSetDTO> getCardSetList(int no);
}