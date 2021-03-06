package upload.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

//업로드한 이미지를 특정 경로에 저장
public class SpringFileWrite {
	private FileOutputStream fos;
	
	public void writeFile(MultipartFile file, String path) {
		//파일명
		String fileName = file.getOriginalFilename();
	
		try {
			byte[] fileData = file.getBytes();
			fos = new FileOutputStream(path+"\\"+fileName);
			fos.write(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String fileUpload(MultipartFile file, String path) {
		//파일명
		String fileName = file.getOriginalFilename();
		String fileNameExtension = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		
		fileName = "";
		int cnt = 0;
		while(true) {
			if(cnt == 15) {
				break;
			}
			double ranNum = Math.random()*123;
			int ran = (int)ranNum;
			if(ran>=97 && ran<=122) {
				fileName += (char)ranNum;
				cnt++;
			}
		}
		
		fileName += fileNameExtension;
		try {
			byte[] fileData = file.getBytes();
			fos = new FileOutputStream(path+"\\"+fileName);
			fos.write(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return fileName;
	}
	
	public String writeFile(MultipartFile file, String path, String no) {
		String fName = "";
		if(file.getOriginalFilename()==null||file.getOriginalFilename().equals("")) {
		}else {
			try {
				byte[] fileData = file.getBytes();
				String fileName = file.getOriginalFilename();
				String fileExp = fileName.substring(fileName.lastIndexOf("."),fileName.length());
				fName = no + fileExp;
				fos = new FileOutputStream(path+"\\"+no+fileExp);
				fos.write(fileData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return fName;
	}
	
	public void deleteFile(String filename, String path) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
}
