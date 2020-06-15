package upload.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

public class SearchFile {
	public List<String> defaultProfile(HttpServletRequest request, String ROOTPATH){
		List<String> list = new ArrayList<String>();
		String path = request.getSession().getServletContext().getRealPath("/profile/default");
		File file = new File(path);
		File[] fileList = file.listFiles();
		
		for(int i=0; i < fileList.length; i++){
	  		list.add(ROOTPATH+"/profile/default/"+fileList[i].getName()	);
		}
		
		return list;
	}
}
