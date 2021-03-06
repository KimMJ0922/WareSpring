package member.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MemberDTO {
	private int no;
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
}
