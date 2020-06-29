package Buy.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BuyDto {
	private String board_no;
	private String no;
	private String subject;
	private String content;
	private String buy_mno;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Timestamp buy_day;
	private int cnt;
}
