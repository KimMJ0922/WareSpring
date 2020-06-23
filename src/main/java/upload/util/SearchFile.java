package upload.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

public class SearchFile {
	public List<String> defaultProfile(HttpServletRequest request, String ROOTPATH, List<String> list){
		String path = request.getSession().getServletContext().getRealPath("/profile/default/");
		File file = new File(path);
		File[] fileList = file.listFiles();
		
		for(int i=0; i < fileList.length; i++){
	  		list.add(ROOTPATH+"/profile/default/"+fileList[i].getName()	);
		}
		
		return list;
	}
	
	public List<String> userProfile(HttpServletRequest request, String ROOTPATH, List<String> list, String no){
		String path = request.getSession().getServletContext().getRealPath("/profile/users/");
		File file = new File(path);
		File[] fileList = file.listFiles();
		
		for(int i=0; i < fileList.length; i++){
	  		String fileName = fileList[i].getName();
	  		fileName = fileName.substring(0,fileName.indexOf("."));
	  		if(fileName.equals(no)) {
	  			list.add(ROOTPATH+"/profile/users/"+fileList[i].getName());
	  			break;
	  		}
		}
		return list;
	}
	
	public void userProfileDelete(HttpServletRequest request, String profile) {
		System.out.println("왔냐");
		String path = request.getSession().getServletContext().getRealPath("/profile/users/"+profile);
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
}
