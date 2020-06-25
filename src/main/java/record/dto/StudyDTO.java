package record.dto;

import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudyDTO {
	private int no;
	private int member_no;
	private int cardset_no;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp studyday;
	private String category;
}
