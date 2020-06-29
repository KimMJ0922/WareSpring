package folder.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import folder.dto.FolderDTO;
import folder.dto.FolderListDTO;
import folder.service.FolderService;
import record.dto.StudyDTO;

@RestController
@CrossOrigin
public class FolderController {
	
	@Autowired
	FolderService fs;
	
	@PostMapping("/createfolder")
	public void insertFolder(@RequestBody Map<String,Object> map) {
		fs.insertFolder(map);
	}
	
	@PostMapping("/getfolder")
	@ResponseBody
	public Map<String, Object> getFolder(@RequestBody Map<String,Object> map){
		List<FolderDTO> flist = fs.getFolder(map);
		map.put("flist", flist);
		return map;
	}
	
	@PostMapping("/modifyfolder")
	public void modifyFolder(@RequestBody Map<String,Object> map) {
		fs.modifyFolder(map);
	}
	
	@PostMapping("/deletefolder")
	public void deleteFolder(@RequestBody Map<String,Object> map) {
		fs.deleteFolder(map);
	}
	
	@PostMapping("/getfolderlist")
	@ResponseBody
	public Map<String, Object> getFolderList(@RequestBody Map<String,Object> map){
		List<FolderListDTO> fllist = fs.getFolderList(map);
		map.put("flist", fllist);
		return map;
	}
	
	@PostMapping("/insertstudylist")
	public void insertStudyList(@RequestBody Map<String,Object> map) {
		//insert하기 전 목록 지우기
		fs.deleteList(map);
		//하나씩 꺼내서 넣기
		//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(map);
		
		//JSON 형태의 string 으로 변환
		String folder = jsonObject.toJSONString();
		
		//string으로 변환한 것을 다시 JSONObject로 변환
		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
		//변환 오류가 생긴다. 이유는 모름
		JSONObject obj2 = (JSONObject)JSONValue.parse(folder);
		JSONArray arr = (JSONArray) obj2.get("insertList");
		
		for(int i=0; i<arr.size(); i++) {
			FolderListDTO fldto = new FolderListDTO();
			JSONObject obj = (JSONObject)arr.get(i);
			fldto.setFolder_no(Integer.parseInt(map.get("folder_no").toString()));
			fldto.setCategory(obj.get("category").toString());
			fldto.setCardset_no(Integer.parseInt(obj.get("cardset_no").toString()));
			
			fs.insertFolderList(fldto);
		}
	}
	
	@PostMapping("/getfoldercardlist")
	@ResponseBody
	public Map<String, Object> getFolderCardList(@RequestBody Map<String,Object> map){
		List<FolderListDTO> fllist = fs.getFolderCardList(map);
		map.put("fllist", fllist);
		return map;
	}
}
