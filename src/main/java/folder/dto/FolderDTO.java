package folder.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FolderDTO {
	private int no;
	private String title;
	private String comment;
	private int member_no;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp createday;
	private int cnt;
}
