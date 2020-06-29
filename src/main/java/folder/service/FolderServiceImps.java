package folder.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import folder.dto.FolderDTO;
import folder.dto.FolderListDTO;
import folder.mapper.FolderMapper;
import record.dto.StudyDTO;

@Service
public class FolderServiceImps implements FolderService{
	@Autowired
	FolderMapper fm;
	
	@Override
	public void insertFolder(Map<String, Object> map) {
		// TODO Auto-generated method stub
		fm.insertFolder(map);
	}
	
	@Override
	public List<FolderDTO> getFolder(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return fm.getFolder(map);
	}
	
	@Override
	public void modifyFolder(Map<String, Object> map) {
		// TODO Auto-generated method stub
		fm.modifyFolder(map);
	}
	
	@Override
	public void deleteFolder(Map<String, Object> map) {
		// TODO Auto-generated method stub
		fm.deleteFolder(map);
	}
	
	@Override
	public List<FolderListDTO> getFolderList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return fm.getFolderList(map);
	}
	
	@Override
	public List<StudyDTO> getStudyList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return fm.getStudyList(map);
	}
	
	@Override
	public void deleteList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		fm.deleteList(map);
	}
	
	@Override
	public void insertFolderList(FolderListDTO fldto) {
		// TODO Auto-generated method stub
		fm.insertFolderList(fldto);
	}
	
	@Override
	public List<FolderListDTO> getFolderCardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return fm.getFolderCardList(map);
	}
}
