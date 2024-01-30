package com.nutrimate.nutrimatebackend.controller.board.feed;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@Log4j2
public class ImageLoadingController {
	
	
	@GetMapping("/imageLoading/{filename}")
	public ResponseEntity<byte[]> display(@PathVariable("filename") String filename,
	                                      HttpServletRequest req) {
		//todo application.yml로 옮기기
		String phisicalPath = "C:/Temp/upload";
		
		// 파일이 저장된 경로
		String savename = phisicalPath + File.separator + filename;
		log.info(savename);
		File file = new File(savename);
		
		// 저장된 이미지파일의 이진데이터 형식을 구함
		byte[] result = null;// 1. data
		ResponseEntity<byte[]> entity = null;
		
		try {
			result = FileCopyUtils.copyToByteArray(file);
			
			// 2. header
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath())); // 파일의 컨텐츠타입을 직접 구해서
			// header에 저장
			
			// 3. 응답본문
			entity = new ResponseEntity<>(result, header, HttpStatus.OK);// 데이터, 헤더, 상태값
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		return entity;
	}
	
}
