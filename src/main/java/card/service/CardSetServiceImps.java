package card.service;

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
}
