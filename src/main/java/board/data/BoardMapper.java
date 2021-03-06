package board.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import Buy.dto.BuyDto;

@Mapper
public interface BoardMapper {
	public void BoardInsert(BoardDto dto);
	public int getInsertNum(String no);
	public List<BoardDto> list(HashMap<String, Object> map);
	public int count(String search);
	public BoardDto getData(String board_no);
	public void updateReadcount(String board_no);
	public void deleteBoard(String board_no);
	public void buyBoard(HashMap<String, Object> map);
	public void pointHistoryOfBoard(HashMap<String, Object> map);
	public void updateMemberPoint(HashMap<String, Object> map);
	public void updatePlusMemberPoint(HashMap<String, Object> map);
	public int currentPoint(String member_no);
	public int buyedcheck(HashMap<String, Object> map);
	public int getHistoryNum();
	public void updateBoard(BoardDto dto);
	public List<BoardDto> getSellBoardOfSet(HashMap<String, Integer> map);
	public int getSellListCount(String no);
	public List<BuyDto> purchaseList(HashMap<String, Integer> map);
	public int getPurchaseListCount(String no);
}
