package board.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Buy.dto.BuyDto;

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
	public BoardDto getData(String board_no) {
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
	public void buyBoard(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		bmapper.buyBoard(map);
	}

	@Override
	public void pointHistoryOfBoard(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		bmapper.pointHistoryOfBoard(map);
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

	@Override
	public int buyedcheck(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return bmapper.buyedcheck(map);
	}

	@Override
	public int getHistoryNum() {
		// TODO Auto-generated method stub
		return bmapper.getHistoryNum();
	}

	@Override
	public void updatePlusMemberPoint(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		bmapper.updatePlusMemberPoint(map);
	}

	@Override
	public void updateBoard(BoardDto dto) {
		// TODO Auto-generated method stub
		bmapper.updateBoard(dto);
	}

	@Override
	public List<BoardDto> getSellBoardOfSet(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return bmapper.getSellBoardOfSet(map);
	}

	@Override
	public int getSellListCount(String no) {
		// TODO Auto-generated method stub
		return bmapper.getSellListCount(no);
	}

	@Override
	public List<BuyDto> purchaseList(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return bmapper.purchaseList(map);
	}

	@Override
	public int getPurchaseListCount(String no) {
		// TODO Auto-generated method stub
		return bmapper.getPurchaseListCount(no);
	}
}
