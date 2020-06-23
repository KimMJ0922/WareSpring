package card.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import card.dto.CardDTO;

@Mapper
public interface CardMapper {
	public List<CardDTO> list();
	public int count();
	public void CardInsert(CardDTO cdto);
	//카드 테이블에 넣기
	public void insertCard(CardDTO dto);
	
	public List<String> searchImgFile(String search);
	
	public List<CardDTO> getCardList(Map<String, Object> map);
	
	public List<String> getImgList(int no);
	
	public void deleteCard(int no);
	
	public int getCardSetCount(Map<String, Object> map);
	
	public List<CardDTO> getTestCardList(Map<String, Object> map);
}
