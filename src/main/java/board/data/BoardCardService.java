package board.data;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<BoardCardDto> getCardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bcmapper.getCardList(map);
	}

	@Override
	public List<String> getImgList(int board_no) {
		// TODO Auto-generated method stub
		return bcmapper.getImgList(board_no);
	}

	@Override
	public void deleteCard(int board_no) {
		// TODO Auto-generated method stub
		bcmapper.deleteCard(board_no);
	}

	@Override
	public void insertCard(BoardCardDto dto) {
		// TODO Auto-generated method stub
		bcmapper.insertCard(dto);
	}

	@Override
	public int getCardSetCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bcmapper.getCardSetCount(map);
	}

	@Override
	public List<BoardCardDto> getTestCardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bcmapper.getTestCardList(map);
	}
}
