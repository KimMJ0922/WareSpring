package record.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import record.dto.DetailRecordDTO;
import record.dto.RecordDTO;
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
}
