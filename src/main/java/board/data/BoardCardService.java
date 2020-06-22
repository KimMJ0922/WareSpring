package board.data;

import java.util.List;

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

	@Override
	public List<String> searchImgFile(String search) {
		// TODO Auto-generated method stub
		return bcmapper.searchImgFile(search);
	}

	@Override
	public List<BoardCardDto> getAllDatas(String board_no) {
		// TODO Auto-generated method stub
		return bcmapper.getAllDatas(board_no);
	}

}
