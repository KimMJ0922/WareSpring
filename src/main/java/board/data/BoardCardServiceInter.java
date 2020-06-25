package board.data;

import java.util.List;
import java.util.Map;

public interface BoardCardServiceInter {
	public void BoardCardInsert(BoardCardDto bcdto);
	public List<BoardCardDto> getAllDatas(String board_no);
	public List<String> searchImgFile(String search);
	public List<BoardCardDto> getCardList(Map<String, Object> map);
}
