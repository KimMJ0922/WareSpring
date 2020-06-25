package record.service;

import java.util.List;
import java.util.Map;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;

public interface RecordService {
	//record 테이블에 값 넣기
	public void insertRecord(RecordDTO rdto);
	//record 최대 번호 얻기
	public int getMaxNo(RecordDTO rdto);
	//상세 정보 저장
	public void insertDetailRecord(DetailRecordDTO ddto);
	//학습 저장
	public void insertStudy(Map<String, Object> map);
	//다이어그램 리스트
	public List<RecordDTO> getList(Map<String, Object> map);
	//최근 목록
	public List<RecordDTO> getLastList(Map<String, Object> map);
	
}
