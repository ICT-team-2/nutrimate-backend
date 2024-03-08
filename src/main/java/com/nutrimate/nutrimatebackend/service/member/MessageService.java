package com.nutrimate.nutrimatebackend.service.member;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;


@Service
public class MessageService {
  private final DefaultMessageService defaultMessageService;
  private final ConcurrentHashMap<String, String> authCodes = new ConcurrentHashMap<>();
  private final ScheduledExecutorService executorService =
      Executors.newSingleThreadScheduledExecutor();

  @Autowired
  private MemberMapper memberMapper;

  public MessageService() {
    this.defaultMessageService = NurigoApp.INSTANCE.initialize("NCSXRLX6GSEOQVLX",
        "ZAALEQTX05IBON19YYVVBZNVPZDYCFI7", "https://api.coolsms.co.kr");
  }

  public String sendAuthCode(String phone) {
    String authCode = generateAuthCode();

    Message message = new Message();
    message.setFrom("01092561065");
    message.setTo(phone);
    String authMessage = "[Nutrimate] 회원가입 인증 코드는 " + "[" + authCode + "]" + "입니다.";
    message.setText(authMessage);

    try {
      defaultMessageService.send(message);
      storeAuthCode(phone, authCode);
    } catch (NurigoMessageNotReceivedException exception) {
      System.out.println(exception.getFailedMessageList());
      System.out.println(exception.getMessage());
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }
    return authCode;
  }

  public boolean checkPhoneNumber(String userPhone) {
    boolean exists = memberMapper.checkPhoneNumber(userPhone);
    return exists;
  }

  public String resendAuthCode(String phone) {
    // 기존 인증번호를 만료합니다.
    authCodes.remove(phone);

    // 새로운 인증번호를 생성하고 발송합니다.
    return sendAuthCode(phone);
  }

  private String generateAuthCode() {
    return String.format("%06d", new Random().nextInt(1000000));
  }

  // 인증번호 시간 3분후 자동만료
  private void storeAuthCode(String phone, String authCode) {
    authCodes.put(phone, authCode);
    executorService.schedule(() -> authCodes.remove(phone), 3, TimeUnit.MINUTES);
  }

  public String verifyAuthCode(String phone, String inputAuthCode) {
    String storedAuthCode = authCodes.get(phone);

    if (storedAuthCode == null) {
      return "인증번호가 만료되었습니다.";
    }

    if (!storedAuthCode.equals(inputAuthCode)) {
      return "인증번호가 일치하지 않습니다.";
    }

    return "인증 성공";
  }


}
