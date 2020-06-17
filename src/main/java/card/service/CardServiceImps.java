package card.service;

import java.util.List;

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
	
	@Override
	public List<String> searchImgFile(String search) {
		// TODO Auto-generated method stub
		return cardMapper.searchImgFile(search);
	}
}
