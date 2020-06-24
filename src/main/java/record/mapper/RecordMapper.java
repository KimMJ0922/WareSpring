package record.mapper;

import org.apache.ibatis.annotations.Mapper;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;

@Mapper
public interface RecordMapper {
	public void insertRecord(RecordDTO rdto);
	public int getMaxNo(RecordDTO rdto);
	public void insertDetailRecord(DetailRecordDTO ddto);
}
