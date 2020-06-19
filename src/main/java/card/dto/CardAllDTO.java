package card.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CardAllDTO {
	//member
	private String email;
	private String name;
	private String profile;
	private String provider;
	//cardset
	private int no;
	private int member_no;
	private String title;
	private String comment;
	private String open_scope;
	private String update_scope;
	@JsonFormat(pattern = "yy-MM-dd")
	private Timestamp createday;
	//card
	private int cardset_no;
	private int question_no;
	private String question;
	private String answer;
	private String imgFile;
}
