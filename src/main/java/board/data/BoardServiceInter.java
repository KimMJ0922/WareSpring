package board.data;

import java.util.HashMap;
import java.util.List;

public interface BoardServiceInter {
	public void BoardInsert(BoardDto dto);
	public int getInsertNum(String no);
	public List<BoardDto> list(HashMap<String, Integer> map);
	public int count();
	public List<BoardDto> getData(String board_no);
	public void updateReadcount(String board_no);
}	
