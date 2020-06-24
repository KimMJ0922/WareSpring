package record.dto;

import lombok.Data;

@Data
public class DetailRecordDTO {
	private int record_no;
	private int question_no;
	private String question;
	private String answer;
	private String useranswer;
	private String type;
	private String result;
}
