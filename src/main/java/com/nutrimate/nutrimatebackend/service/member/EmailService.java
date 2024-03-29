package com.nutrimate.nutrimatebackend.service.member;

import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Log4j2
@Component
public class EmailService {
	// private final ConcurrentHashMap<String, String> authCodes = new ConcurrentHashMap<>();
	private Map<String, String> authCodes = new HashMap<>();
	@Autowired
	private JavaMailSender mailSender;
	private int authNumber;
	@Autowired
	private MemberMapper memberMapper;
	// private Object authCodes;
	
	
	public void makeRandomNumber() {
		
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}
	
	// 보내는 이메일 양식!
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = "uujean12@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
		String toMail = email;
		log.info("보내는 이메일 :" + email);
		String title = "[NutriMate] 인증 이메일 입니다."; // 이메일 제목
		StringBuffer content = new StringBuffer();
		content.append("<h1>저희 NutriMate 홈페이지를 방문해주셔서 감사합니다.</h1>").append("<br><br>")
				.append("<p>인증 번호는 <strong>").append(authNumber).append("</strong>입니다.</p>").append("<br>")
				.append("<p>해당 인증번호를 인증번호 확인란에 기입하여 주세요.</p>");
		
		// 이메일을 키로, 인증번호를 값으로 맵에 저장
		authCodes.put(email, Integer.toString(authNumber));
		
		mailSend(setFrom, toMail, title, content.toString());
		return Integer.toString(authNumber);
	}
	
	// 이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content, true);
			mailSender.send(message);
			// log.info("이메일 전송 성공: 수신자 - {}, 제목 - {}", toMail, title);
		} catch (MessagingException e) {
			e.printStackTrace();
			// log.error("이메일 전송 실패: 수신자 - {}, 제목 - {}", toMail, title);
		}
		
	}
	
	public boolean checkEmail(String email) {
		boolean exists = memberMapper.checkEmail(email);
		return exists;
	}
	
	
	public String verifyECode(String email, String inputEcode) {
		String storedECode = authCodes.get(email);
		if (storedECode == null) {
			return "인증번호가 만료되었습니다.";
		}
		if (!storedECode.equals(inputEcode)) {
			return "인증번호가 일치하지 않습니다.";
		}
		// 인증 성공했으므로 해당 이메일의 인증 코드 정보를 삭제
		
		return "인증 성공";
	}
}
