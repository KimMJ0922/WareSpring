package upload.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public void deleteTempFolder(HttpServletRequest request, int no, String path) {
		File file = new File(path+"card/temp/"+no);       
		if(file.exists()) {
			file.delete();
		}
	}
	
	//파일 이름 바꾸면서 옮기기
	public String moveTempToImgFolder(HttpServletRequest request, int memberNo, int cardSetNo, int idx, String img) {
		String path = request.getSession().getServletContext().getRealPath("/");
		String tempPath = path+"card/temp/"+memberNo+"/";
		String imgPath = path+"card/img/";
		String imgFileExtension = img.substring(img.lastIndexOf("."),img.length());
		String moveFileName = cardSetNo+"-"+idx+imgFileExtension;
		File tempFile = new File(tempPath);
		File[] fileList = tempFile.listFiles();
		if(fileList.length != 0) {
			for(File f : fileList) {
				String fileName = f.getName();
				
				if(fileName.equals(img)) {
					f.renameTo(new File(imgPath+moveFileName));
				}
			}
			
			return moveFileName;
		}
		return "";
	}
	
	//선택한 이미지 임시 폴더로 옮기기
	public String moveImgToTempFolder(HttpServletRequest request, String no, String fileName) {
		
		//임시 폴더 확인 및 생성
		String path = request.getSession().getServletContext().getRealPath("/card/temp");
		createTempFolder(request,no,path);
		
		//파일 옮기기
		String imgFolderPath = request.getSession().getServletContext().getRealPath("/card/img/");
		String tempFolderPath = request.getSession().getServletContext().getRealPath("/card/temp/"+no);
		
		//img 폴더 안에 있는 파일 목록 읽기
		File file = new File(imgFolderPath);
		File[] fileList = file.listFiles();
		
		String finalFileName = "";
		//해당 파일 복사
		for(File f : fileList) {
			String imgFileName = f.getName();
			String imgFilePre = imgFileName.substring(0,imgFileName.lastIndexOf("."));
			String imgExtension = imgFileName.substring(imgFileName.lastIndexOf("."),imgFileName.length());
			
			//옮길 파일 이름과 목록에 있는 파일 이름이 일치할 때
			if(fileName.equals(imgFileName)) {
				try {
					//한 카드세트에 똑같은 이미지를 사용할 경우를 대비해 랜덤 수
					while(true) {
						double ranNum = Math.random()*123;
						int ran = (int)ranNum;
						if(ran>=97 && ran<=122) {
							imgFilePre += (char)ranNum;
							break;
						}
					}
					finalFileName = imgFilePre+imgExtension;
		            FileInputStream fis = new FileInputStream(f); //읽을파일
		            FileOutputStream fos = new FileOutputStream(tempFolderPath+"/"+imgFilePre+imgExtension); //복사할파일
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
		            fis.close();
		            fos.close();
		            
		        } catch (FileNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return finalFileName;
	}
	
	//임시 폴더 안에 있는 파일 삭제
	public void deleteFile(HttpServletRequest request, int no) {
		String path = request.getSession().getServletContext().getRealPath("/card/temp/"+no);
		File file = new File(path);
		if(file.exists()) {
			File[] fileList = file.listFiles();
			System.out.println(fileList.length);
			if(fileList.length != 0) {
				for(File f : fileList) {
					f.delete();
				}
			}
		}
	}
}
