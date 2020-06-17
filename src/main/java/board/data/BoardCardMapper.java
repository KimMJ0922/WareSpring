package board.data;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardCardMapper {
	public void BoardCardInsert(BoardCardDto bcdto);
}
