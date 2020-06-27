package record.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;
import record.dto.StudyDTO;

@Mapper
public interface RecordMapper {
	public void insertRecord(RecordDTO rdto);
	public int getMaxNo(RecordDTO rdto);
	public void insertDetailRecord(DetailRecordDTO ddto);
	public void insertStudy(Map<String, Object> map);
	public List<RecordDTO> getList(Map<String, Object> map);
	public List<RecordDTO> getLastList(Map<String, Object> map);
	public List<RecordDTO> getChartList(Map<String, Object> map);
	public List<RecordDTO> getDiagram(Map<String, Object> map);
	public List<RecordDTO> getLast(Map<String, Object> map);
	public List<RecordDTO> getChart(Map<String, Object> map);
	public List<DetailRecordDTO> getDetailRecordList(Map<String, Object> map);
	public List<StudyDTO> getStudyList(Map<String, Object> map);
	public List<StudyDTO> getStudyBoardList(Map<String, Object> map);
	public List<StudyDTO> getStudyCardSetList(Map<String, Object> map);
}
