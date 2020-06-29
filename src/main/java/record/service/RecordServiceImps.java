package record.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;
import record.dto.StudyDTO;
import record.mapper.RecordMapper;

@Service
public class RecordServiceImps implements RecordService{
	@Autowired
	private RecordMapper rm;
	
	@Override
	public void insertRecord(RecordDTO rdto) {
		// TODO Auto-generated method stub
		rm.insertRecord(rdto);
	}
	
	@Override
	public int getMaxNo(RecordDTO rdto) {
		// TODO Auto-generated method stub
		return rm.getMaxNo(rdto);
	}
	
	@Override
	public void insertDetailRecord(DetailRecordDTO ddto) {
		// TODO Auto-generated method stub
		rm.insertDetailRecord(ddto);
	}
	
	@Override
	public void insertStudy(Map<String, Object> map) {
		// TODO Auto-generated method stub
		rm.insertStudy(map);
	}
	
	@Override
	public List<RecordDTO> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getList(map);
	}
	
	@Override
	public List<RecordDTO> getLastList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getLastList(map);
	}
	
	@Override
	public List<RecordDTO> getChartList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getChartList(map);
	}
	
	@Override
	public List<RecordDTO> getChart(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getChart(map);
	}
	
	@Override
	public List<RecordDTO> getDiagram(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getDiagram(map);
	}
	
	@Override
	public List<RecordDTO> getLast(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getLast(map);
	}
	
	@Override
	public List<DetailRecordDTO> getDetailRecordList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getDetailRecordList(map);
	}
	
	@Override
	public List<StudyDTO> getStudyList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getStudyList(map);
	}
	
	@Override
	public List<StudyDTO> getStudyBoardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getStudyBoardList(map);
	}
	
	@Override
	public List<StudyDTO> getStudyCardSetList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rm.getStudyCardSetList(map);
	}
}
