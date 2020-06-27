package record.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudyDTO {
	private int no;
	private int member_no;
	private int cardset_no;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Timestamp studyday;
	private String category;
	private String name;
	private String profile;
	private String update_scope;
	private String open_scope;
	private String title;
	private String comment;
	private int cnt;
}
