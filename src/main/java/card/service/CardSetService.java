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
	public List<CardSetDTO> getCardSetList(Map<String,Object> map);
	//메뉴쪽에 카드 세트 갯수 출력
	public int getSetCount(Map<String,Object> map);
	//카드세트의 정보
	public CardSetDTO getCardSet(Map<String,Object> map);
	//카드 비밀번호 확인
	public boolean passCheck(Map<String,Object> map);
	//카드 세트 지우기
	public void deleteCardSet(Map<String,Object> map);
	//카드 세트 업데이트 비밀번호 확인
	public boolean updatePassCheck(Map<String,Object> map);
}
