package com.nutrimate.nutrimatebackend.controller.member;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.service.member.EmailService;
import com.nutrimate.nutrimatebackend.service.member.MessageService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class MemberValidateController {
  private MessageService messageService;
  private EmailService emailService;

  public MemberValidateController(MessageService messageService, EmailService emailService) {
    this.messageService = messageService;
    this.emailService = emailService;
  }

  @PostMapping("/message/send")
  public String userCheck(@RequestBody Map<String, String> params) {
    String phone = params.get("userPhone");
    return messageService.sendAuthCode(phone);

  }


  @PostMapping("/checkPhoneNumber")
  public ResponseEntity<Map<String, Boolean>> checkPhoneNumber(
      @RequestBody Map<String, String> params) {
    String phone = params.get("userPhone");

    boolean exists = messageService.checkPhoneNumber(phone);

    Map<String, Boolean> response = new HashMap();
    response.put("exists", exists);

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/verify")
  public ResponseEntity<String> verifyAuthCode(@RequestBody Map<String, String> params) {
    String phone = params.get("userPhone");
    String inputAuthCode = params.get("authCode");

    String verificationResult = messageService.verifyAuthCode(phone, inputAuthCode);

    if (verificationResult.equals("인증 성공")) {
      return ResponseEntity.ok().body("인증 성공");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(verificationResult);
    }
  }


  // @PostMapping("/resendVerificationCode")
  // public ResponseEntity<String> resendVerificationCode(@RequestBody Map<String, String> params) {
  // String phone = params.get("userPhone");
  //
  // // 인증번호를 만료하고 새로운 인증번호를 발급합니다.
  // String newAuthCode = messageService.resendAuthCode(phone);
  //
  // if (newAuthCode != null) {
  // return ResponseEntity.ok().body("인증번호 재발송 성공");
  // } else {
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증번호 재발송 실패");
  // }
  // }

  @PostMapping("/email/send")
  @ResponseBody
  public String mailCheck(@RequestBody Map<String, String> params) throws Exception {
    String email = params.get("userEmail");
    String code = emailService.joinEmail(email);

    log.info("인증코드 : " + code);
    return code;
  }

  @PostMapping("/verifyEmail")
  public ResponseEntity<String> verifyECode(@RequestBody Map<String, String> params) {
    String email = params.get("userEmail");
    String inputEcode = params.get("code");

    String verificationResult = emailService.verifyECode(email, inputEcode);
    log.info(verificationResult);
    if (verificationResult.equals("인증 성공")) {
      return ResponseEntity.ok().body("인증 성공");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(verificationResult);
    }

  }

  // //이메일 인증
  // @GetMapping("/email/send")
  // @ResponseBody
  // public ResponseEntity<String> mailCheck(@RequestParam("email") String email) {
  // try {
  // System.out.println("이메일 요청이 들어옴!!!! : " + email);
  // String result = emailService.joinEmail(email);
  // return new ResponseEntity<>(result, HttpStatus.OK);
  // } catch (Exception e) {
  // return new ResponseEntity<>("에러 발생: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  // }
  // }



  @PostMapping("/checkEmail")
  public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> params) {
    String email = params.get("userEmail");

    boolean exists = emailService.checkEmail(email);

    Map<String, Boolean> response = new HashMap();
    response.put("exists", exists);

    return ResponseEntity.ok().body(response);
  }
}

