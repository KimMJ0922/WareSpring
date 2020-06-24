package board.data;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	public void BoardInsert(BoardDto dto);
	public int getInsertNum(String no);
	public List<BoardDto> list(HashMap<String, Object> map);
	public int count(String search);
	public List<BoardDto> getData(String board_no);
	public void updateReadcount(String board_no);
	public void deleteBoard(String board_no);
	public void buyBoard(String board_no, String member_no);
	public void pointHistoryOfBoard(int requirepoint);
	public void updateMemberPoint(HashMap<String, Object> map);
	public int currentPoint(String member_no);
}
