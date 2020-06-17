package card.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.dto.CardDTO;
import card.mapper.CardMapper;

@Service
public class CardServiceImps implements CardService{
	
	@Autowired
	private CardMapper cardMapper;
	
	@Override
	public void insertCard(CardDTO dto) {
		// TODO Auto-generated method stub
		cardMapper.insertCard(dto);
	}
}
