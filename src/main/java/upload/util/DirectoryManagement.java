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
	
	//폴더 삭제
	public void deleteTempFolder(HttpServletRequest request, String no, String path) {
		File file = new File(path+"/"+no);       
		if(file.exists()) {
			file.delete();
		}
	}
	
	//파일 이름 바꾸면서 옮기기
	public String moveTempToImgFolder(HttpServletRequest request, int memberNo, int cardSetNo, int idx, String path, String img) {
		String tempPath = path+"card/temp/"+memberNo+"/";
		String imgPath = path+"card/img/";
		String imgFileExtension = img.substring(img.lastIndexOf("."),img.length());
		String moveFileName = cardSetNo+"-"+idx+imgFileExtension;
		File tempFile = new File(tempPath);
		File[] fileList = tempFile.listFiles();
		for(File f : fileList) {
			String fileName = f.getName();
			
			if(fileName.equals(img)) {
				f.renameTo(new File(imgPath+moveFileName));
			}
		}
		
		return moveFileName;
		
	}
}
