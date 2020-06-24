package board.data;

import java.util.HashMap;
import java.util.List;

public interface BoardServiceInter {
	public void BoardInsert(BoardDto dto);
	public int getInsertNum(String no);
	public List<BoardDto> list(HashMap<String, Object> map);
	public int count(String search);
	public List<BoardDto> getData(String board_no);
	public void updateReadcount(String board_no);
	public void deleteBoard(String board_no);
	public void buyBoard(HashMap<String, Object> map);
	public void pointHistoryOfBoard(HashMap<String, Object> map);
	public void updateMemberPoint(HashMap<String, Object> map);
	public int currentPoint(String member_no);
	public int buyedcheck(HashMap<String, Object> map);
}	
