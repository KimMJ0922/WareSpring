package folder.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import folder.dto.FolderDTO;
import folder.dto.FolderListDTO;
import record.dto.StudyDTO;

@Mapper
public interface FolderMapper {
	public void insertFolder(Map<String, Object> map);
	public List<FolderDTO> getFolder(Map<String, Object> map);
	public void modifyFolder(Map<String, Object> map);
	public void deleteFolder(Map<String, Object> map);
	public List<FolderListDTO> getFolderList(Map<String, Object> map);
	public List<StudyDTO> getStudyList(Map<String, Object> map);
	public void deleteList(Map<String, Object> map);
	public void insertFolderList(FolderListDTO fldto);
	public List<FolderListDTO> getFolderCardList(Map<String, Object> map);
}
