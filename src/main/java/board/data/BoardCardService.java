package board.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardCardService implements BoardCardServiceInter {
	
	@Autowired
	private BoardCardMapper bcmapper;
	
	@Override
	public void BoardCardInsert(BoardCardDto bcdto) {
		// TODO Auto-generated method stub
		bcmapper.BoardCardInsert(bcdto);
	}

}
