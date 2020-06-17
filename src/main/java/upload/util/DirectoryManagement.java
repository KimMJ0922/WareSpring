package upload.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class DirectoryManagement {
	//문제 이미지 임시 폴더 생성
	public void createTempFolder(HttpServletRequest request, String no, String path) {
		File file = new File(path+"/"+no);       
		if(!file.exists()) {
			file.mkdir();
		}
	}
}
