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
	public List<BoardDto> list(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return bmapper.list(map);
	}

	@Override
	public int count(String search) {
		// TODO Auto-generated method stub
		return bmapper.count(search);
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

	@Override
	public void deleteBoard(String board_no) {
		// TODO Auto-generated method stub
		bmapper.deleteBoard(board_no);
	}

	@Override
	public void buyBoard(String board_no, String member_no) {
		// TODO Auto-generated method stub
		bmapper.buyBoard(board_no, member_no);
	}

	@Override
	public void pointHistoryOfBoard(int requirepoint) {
		// TODO Auto-generated method stub
		bmapper.pointHistoryOfBoard(requirepoint);
	}

	@Override
	public void updateMemberPoint(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		bmapper.updateMemberPoint(map);
	}

	@Override
	public int currentPoint(String member_no) {
		// TODO Auto-generated method stub
		return bmapper.currentPoint(member_no);
	}
	
}
