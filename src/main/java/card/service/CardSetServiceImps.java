package card.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.mapper.CardMapper;

@Service
public class CardSetServiceImps implements CardSetService{
	
	@Autowired
	private CardMapper cardMapper;
	
	@Override
	public void insertCardSet(Map<String, Object> map) {
		// TODO Auto-generated method stub
	}
}
