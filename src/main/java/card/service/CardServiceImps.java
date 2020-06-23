package card.service;

import java.util.List;
import java.util.Map;

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
	
	@Override
	public List<CardDTO> getCardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cardMapper.getCardList(map);
	}
	@Override
	public List<String> getImgList(int no) {
		// TODO Auto-generated method stub
		return cardMapper.getImgList(no);
	}
	
	@Override
	public void deleteCard(int no) {
		// TODO Auto-generated method stub
		cardMapper.deleteCard(no);
	}
}
