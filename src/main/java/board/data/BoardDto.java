package board.data;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BoardDto {
	private int board_no;
	private String writer;
	private String subject;
	private int requirepoint;
	private String content;
	private int readcount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp writeday;
}
