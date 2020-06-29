package record.dto;

import java.sql.Timestamp;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Timestamp recordday;
	private String method;
	private String title;
	private String comment;
	private String name;
	private String profile;  
}
