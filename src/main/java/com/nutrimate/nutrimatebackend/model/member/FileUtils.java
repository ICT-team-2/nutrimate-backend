package com.nutrimate.nutrimatebackend.model.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FileUtils {
	// [파일 이름 중복 체크용 메소드]
	public static String getNewFileName(String path, String fileName) {
		// fileName=원격.txt
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			return fileName;
		} else {
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).trim();
			String fileNameExcludeExt = fileName.substring(0, fileName.lastIndexOf("."));
			
			String newFileName;
			while (true) {
				newFileName = "";
				if (fileNameExcludeExt.indexOf("_") != -1) {// 파일명에 _가 포함됨
					String[] arrFiles = fileNameExcludeExt.split("_");
					String lastName = arrFiles[arrFiles.length - 1];
					try {
						int index = Integer.parseInt(lastName);
						for (int i = 0; i < arrFiles.length; i++) {
							if (i != arrFiles.length - 1)
								newFileName += arrFiles[i] + "_";
							else
								newFileName += String.valueOf(index + 1);
						}
						newFileName += "." + ext;
					} catch (Exception e) {
						newFileName += fileNameExcludeExt + "_1." + ext;
					}
				} else {// _가 없음
					newFileName += fileNameExcludeExt + "_1." + ext;
				}
				File f = new File(path + File.separator + newFileName);
				if (f.exists()) {
					fileNameExcludeExt = newFileName.substring(0, newFileName.lastIndexOf("."));
					continue;
				} else
					break;
			} //////////// while
			
			return newFileName;
		}
	}/////////////////////
	
	//업로드 로직
	public static StringBuffer upload(List<MultipartFile> files, String saveDirectory) throws IllegalStateException, IOException {
		//업로드된 파일명들 저장용
		StringBuffer fileNames = new StringBuffer();
		
		try {
			
			
			for (MultipartFile file : files) {
				if (file.getContentType() != null) {//input type="file"인 경우
					String newFilename = FileUtils.getNewFileName(saveDirectory, file.getOriginalFilename());
					File file_ = new File(saveDirectory + File.separator + newFilename);
					//해당 폴더 없으면 자동 생성
					if (!file_.exists()) {
						file_.mkdirs();
					}
					file.transferTo(file_);
					//파일명을 스프링버퍼에 저장
					fileNames.append(File.separator + "imageLoading" + File.separator)
							.append(newFilename).append(",");//구분자 ,
				}
			}
		} catch (Exception e) {//파일 미 첨부시 에러시:null반환
			log.info("파일 미 첨부시 에러시:null반환", e);
			return null;
		}
		return fileNames.deleteCharAt(fileNames.length() - 1);
	}
	
	//삭제 로직
	public static void deletes(StringBuffer fileNames, String saveDirectory, String delim) {
		String[] files = fileNames.toString().split(delim);
		for (String filename : files) {
			File f = new File(saveDirectory + File.separator + filename);
			if (f.exists()) f.delete();
		}
	}
	
}
