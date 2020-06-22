package board.data;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements BoardServiceInter{
	@Autowired
	private BoardMapper bmapper;

	@Override
	public void BoardInsert(BoardDto dto) {
		// TODO Auto-generated method stub
		bmapper.BoardInsert(dto);
	}

	@Override
	public int getInsertNum(String no) {
		// TODO Auto-generated method stub
		return bmapper.getInsertNum(no);
	}

	@Override
	public List<BoardDto> list(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return bmapper.list(map);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return bmapper.count();
	}

	@Override
	public List<BoardDto> getData(String board_no) {
		// TODO Auto-generated method stub
		return bmapper.getData(board_no);
	}

	@Override
	public void updateReadcount(String board_no) {
		// TODO Auto-generated method stub
		bmapper.updateReadcount(board_no);
	}
	
}