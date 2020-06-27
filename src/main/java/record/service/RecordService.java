package record.service;

import java.util.List;
import java.util.Map;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;
import record.dto.StudyDTO;

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
	//차트 목록
	public List<RecordDTO> getChartList(Map<String, Object> map);
	//다이어그램
	public List<RecordDTO> getDiagram(Map<String, Object> map);
	//최근 목록
	public List<RecordDTO> getLast(Map<String, Object> map);
	//차트 목록
	public List<RecordDTO> getChart(Map<String, Object> map);
	//정답 오답 리스트
	public List<DetailRecordDTO> getDetailRecordList(Map<String, Object> map);
	//최근 학습한 목록 가져오기
	public List<StudyDTO> getStudyList(Map<String, Object> map);
	//최근 학습한 것 중 장터 목록
	public List<StudyDTO> getStudyBoardList(Map<String, Object> map);
	//최근 학습한 것 중 카드 세트 목록
	public List<StudyDTO> getStudyCardSetList(Map<String, Object> map);
}
