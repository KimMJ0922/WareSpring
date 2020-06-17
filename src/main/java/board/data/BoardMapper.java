package board.data;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	public void BoardInsert(BoardDto dto);
	public int getInsertNum(String no);
	public List<BoardDto> list(HashMap<String, Integer> map);
	public int count();
	public List<BoardDto> getData(String board_no);
}
