package card.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import card.dto.CardDTO;

@Mapper
public interface CardMapper {
	public List<CardDTO> list();
	public int count();
	public void CardInsert(CardDTO cdto);
}
