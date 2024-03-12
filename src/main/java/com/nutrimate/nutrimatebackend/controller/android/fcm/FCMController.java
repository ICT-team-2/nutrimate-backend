package com.nutrimate.nutrimatebackend.controller.android.fcm;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.nutrimate.nutrimatebackend.model.android.fcm.FCMDataDto;
import com.nutrimate.nutrimatebackend.service.android.fcm.FCMService;

@Controller
public class FCMController {
    @Autowired
    private FCMService service;

    @PostMapping("/fcm/token")
    @ResponseBody
    public Map<String, String> fcmTokenSave(@RequestBody Map<String, String> data) {
        String token = data.get("token");
        System.out.println("토큰:" + token);
        return service.saveToken(token);
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/fcm/pushToPhone")
    public @ResponseBody Map pushToPhone(@RequestParam Map<String, String> params) {
        // token값 저장용
        List<String> tokens = service.selectAllTokens();
        System.out.println(String.format("선택옵션1:%s,선택옵션2:%s", params.get("dataTitle"),
                params.get("dataBody")));
        System.out.println("tokens:" + tokens);
        int success = 0;
        // FCM서버로 메시지 보내기
        for (String token : tokens) {
            boolean flag = requestToFCMServer(params, token);
            if (flag)
                success++;
        }
        params.put("success", String.valueOf(success));
        return params;
    }/////////////////////////

    private boolean requestToFCMServer(Map<String, String> params, String token) {

        final String serverKey =
                "AAAA9j1G_j4:APA91bHl4PU7tIn5fYwNClVidQ_nrangnNxVCs-Tc7yTnNtQOTbUQbxgzHKdTk3HCab891f8ea02-gYjg5khEx0P63ilgqmnx1_XDG9LdO0pvAf50uQOx5nEk-9kvpWaeI3ASgqWwulv";
        String gcmURL = "https://fcm.googleapis.com/fcm/send";

        // 1.요청헤더 설정용 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "key=" + serverKey);
        headers.add("Content-Type", "application/json; charset=utf-8");

        // 2.요청 본문(JSON형태)과 동일한 구조의 DTO로 요청바디 설정
        FCMDataDto body = new FCMDataDto();
        // 자바빈에 요청바디 설정
        FCMDataDto.Notification notification = new FCMDataDto.Notification();
        notification.setTitle(params.get("title"));
        notification.setBody(params.get("body"));
        FCMDataDto.Data data = new FCMDataDto.Data();

        data.setDataTitle(params.get("dataTitle"));
        data.setDataBody(params.get("dataBody"));
        body.setTo(token);
        body.setNotification(notification);
        body.setData(data);


        // 3.요청 헤더 정보등을 담은 HttpEntity객체 생성
        HttpEntity entity = new HttpEntity(body, headers);

        // 4.RestTemplate으로 요청 보내기
        ResponseEntity<Map> response = restTemplate.exchange(gcmURL, HttpMethod.POST, // 요청 메소드
                entity, // HttpEntity(요청바디와 요청헤더)
                Map.class // 응답 데이타가 {}일때
        );
        System.out.println("FCM서버에 보내는 데이타:" + body.toString());
        System.out.println("응답코드:" + response.getStatusCode());
        System.out.println("응답헤더:" + response.getHeaders());
        System.out.println("응답바디:" + response.getBody());

        return Integer.parseInt(response.getBody().get("success").toString()) == 1;
    }

}
