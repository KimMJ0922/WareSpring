package card.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.dto.CardSetDTO;
import card.mapper.CardSetMapper;

@Service
public class CardSetServiceImps implements CardSetService{
	
	@Autowired
	private CardSetMapper cardSetMapper;
	
	@Override
	public void insertCardSet(CardSetDTO dto) {
		// TODO Auto-generated method stub
		cardSetMapper.insertCardSet(dto);
	}
	
	@Override
	public int getCardSetNo(int no) {
		// TODO Auto-generated method stub
		return cardSetMapper.getCardSetNo(no);
	}
	
	@Override
	public List<CardSetDTO> getCardSetList(int no) {
		// TODO Auto-generated method stub
		return cardSetMapper.getCardSetList(no);
	}
	
	@Override
	public int getSetCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.getSetCount(map);
	}
	
	@Override
	public CardSetDTO getCardSet(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.getCardSet(map);
	}
	
	@Override
	public boolean passCheck(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.passCheck(map) == 1 ? true : false;
	}
}
