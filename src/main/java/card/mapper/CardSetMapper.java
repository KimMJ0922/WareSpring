package card.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import card.dto.CardDTO;
import card.dto.CardSetDTO;

@Mapper
public interface CardSetMapper {
	public void insertCardSet(CardSetDTO dto);
	public int getCardSetNo(int no);
	public void insertCard(CardDTO dto);
	public List<CardSetDTO> getCardSetList(int no);
	public int getSetCount(Map<String,Object> map);
	public CardSetDTO getCardSet(Map<String,Object> map);
}
