package folder.service;

import java.util.List;
import java.util.Map;

import folder.dto.FolderDTO;
import folder.dto.FolderListDTO;
import record.dto.StudyDTO;

public interface FolderService {
	//폴더 만들기
	public void insertFolder(Map<String, Object> map);
	//폴더 목록 가져오기
	public List<FolderDTO> getFolder(Map<String, Object> map);
	//폴더 수정
	public void modifyFolder(Map<String, Object> map);
	//폴더 삭제
	public void deleteFolder(Map<String, Object> map);
	
	//폴더 안에 카드 목록 가져오기
	public List<FolderListDTO> getFolderList(Map<String, Object> map);
	
	//학습한 목록 가져오기
	public List<StudyDTO> getStudyList(Map<String, Object> map);
	
	//폴더 안에 리스트 지우기
	public void deleteList(Map<String, Object> map);
	//리스트 insert
	public void insertFolderList(FolderListDTO fldto);
	//폴더 카드 목록 가져오기
	public List<FolderListDTO> getFolderCardList(Map<String, Object> map);
}
