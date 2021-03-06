package card.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.dto.CardSetDTO;
import card.dto.SearchDTO;
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
	public List<CardSetDTO> getCardSetList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.getCardSetList(map);
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
	
	@Override
	public void deleteCardSet(Map<String, Object> map) {
		// TODO Auto-generated method stub
		cardSetMapper.deleteCardSet(map);
	}
	
	@Override
	public boolean updatePassCheck(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.updatePassCheck(map) == 1 ? true : false;
	}
	
	@Override
	public void updateCardSet(CardSetDTO dto) {
		// TODO Auto-generated method stub
		cardSetMapper.updateCardSet(dto);
	}
	
	@Override
	public List<SearchDTO> getCardSetSearchList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardSetMapper.getCardSetSearchList(map);
	}
}
