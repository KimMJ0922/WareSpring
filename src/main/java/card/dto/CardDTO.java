package card.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class CardDTO {
	private int cardset_no;
	private int question_no;
	private String question;
	private String answer;
	private String imgFile;
	private String imgSrc;	
}
