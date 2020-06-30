package card.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SearchDTO {
	private int no;
	private int member_no;
	private String title;
	private String comment;
	private String open_scope;
	private String update_scope;
	private String open_password;
	private String update_password;
	@JsonFormat(pattern = "yy-MM-dd", timezone="Asia/Seoul")
	private Timestamp createday;
	private int cnt;
	private String name;
	private String profile;
	private String provider;
}
