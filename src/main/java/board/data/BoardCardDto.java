package board.data;

import lombok.Data;

@Data
public class BoardCardDto {
	private int cardset_no;
	private int question_no;
	private String question;
	private String answer;
	private String imgFile;
}
