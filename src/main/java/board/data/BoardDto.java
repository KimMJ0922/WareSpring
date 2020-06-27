package board.data;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BoardDto {
	private int board_no;
	private String no;
	private String subject;
	private int requirepoint;
	private String content;
	private int readcount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Timestamp writeday;
	private String email;
	private String password;
	private String birth;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Timestamp joinday;
	private String emailcheck;
	private String profile;
	private int admin;
	private String provider;
	private int point;
	private String ip;
	private int cnt;
}
