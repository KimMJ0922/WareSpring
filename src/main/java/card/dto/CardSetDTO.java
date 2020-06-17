package card.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class CardSetDTO {
	private int no;
	private int member_no;
	private String title;
	private String comment;
	private String open_scope;
	private String update_scope;
	private String open_password;
	private String update_password;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp createday;
}
