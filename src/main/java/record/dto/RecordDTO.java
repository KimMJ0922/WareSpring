package record.dto;

import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RecordDTO {
	private int no;
	private int member_no;
	private int cardset_no;
	private String category;
	private int rightcnt;
	private int wrongcnt;
	private String method;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp recordday;
	private String title;
	private String comment;
	private String name;
	private String profile;  
}
