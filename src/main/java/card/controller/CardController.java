package card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import card.mapper.CardMapper;

@Controller
public class CardController {
	
	@Autowired
	CardMapper map;
	
	@GetMapping("/list")
	public void list() {
		System.out.println("왔다");
		int count = map.count();
		
		System.out.println(count);
	}
}
