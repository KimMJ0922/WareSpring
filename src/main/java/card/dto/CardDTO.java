package card.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class CardDTO {
	private int setno;
	private String comment;
	private String subject;
}
