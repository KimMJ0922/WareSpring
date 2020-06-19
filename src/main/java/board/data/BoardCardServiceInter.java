package board.data;

import java.util.List;

public interface BoardCardServiceInter {
	public void BoardCardInsert(BoardCardDto bcdto);
	public List<BoardCardDto> getAllDatas(String board_no);
	public List<String> searchImgFile(String search);
}
