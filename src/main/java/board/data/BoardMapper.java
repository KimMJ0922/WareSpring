package board.data;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	public void BoardInsert(BoardDto dto);
}
