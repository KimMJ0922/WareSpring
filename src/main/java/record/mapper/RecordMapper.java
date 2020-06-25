package record.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;

@Mapper
public interface RecordMapper {
	public void insertRecord(RecordDTO rdto);
	public int getMaxNo(RecordDTO rdto);
	public void insertDetailRecord(DetailRecordDTO ddto);
	public void insertStudy(Map<String, Object> map);
	public List<RecordDTO> getList(Map<String, Object> map);
	public List<RecordDTO> getLastList(Map<String, Object> map);
}
