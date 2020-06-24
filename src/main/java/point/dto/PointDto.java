package point.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PointDto {
	private int point_no;
	private int point_amount;
	private String category;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp history_day;
}
