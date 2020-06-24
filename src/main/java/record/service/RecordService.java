package record.service;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;

public interface RecordService {
	//record 테이블에 값 넣기
	public void insertRecord(RecordDTO rdto);
	//record 최대 번호 얻기
	public int getMaxNo(RecordDTO rdto);
	//상세 정보 저장
	public void insertDetailRecord(DetailRecordDTO ddto);
}
