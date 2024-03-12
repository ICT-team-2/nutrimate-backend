package com.nutrimate.nutrimatebackend.controller.android.fcm;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import com.nutrimate.nutrimatebackend.model.android.fcm.FCMDataDto;
import com.nutrimate.nutrimatebackend.service.android.fcm.FCMService;

@Controller
public class FCMController {
    @Autowired
    private FCMService service;

    @PostMapping("/fcm/token")
    @ResponseBody
    public Map<String, String> fcmTokenSave(@RequestParam String token) {
        System.out.println("토큰:" + token);
        return service.saveToken(token);
    }

    @GetMapping("/fcm/pushMessage")
    public String pushMessage() {
        return "fcm/PushMessageSend.ict";
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

        // Firebase Console->프로젝트 선택->설정 아이콘->프로젝트 설정
        // ->상단의 클라우드 메시징 탭 메뉴->Cloud Messaging API(기존)섹션의
        // 쓰리 닷 메뉴 클릭->Google Cloud Console API관리 클릭->사용버튼 클릭후
        // 다시 Firebase Cloud 콘솔로 이동하면 서버키가 생성되 있다
        // AAAAZdTadQM:APA91bGrVWKyVa_Iu4prcoCLLoiSxEz5x61EYCy4kazYErcSVthPmCAC5wx-Zl8H8uNv3yr5W80FL2tdZJNjJJBmQCQQ5iiKZrAbv87K9KsfabYnauV4dJC1RJqJXnqI8rrWr5NsHq-0



        final String serverKey =
                "AAAAZdTadQM:APA91bGrVWKyVa_Iu4prcoCLLoiSxEz5x61EYCy4kazYErcSVthPmCAC5wx-Zl8H8uNv3yr5W80FL2tdZJNjJJBmQCQQ5iiKZrAbv87K9KsfabYnauV4dJC1RJqJXnqI8rrWr5NsHq-0";
        String gcmURL = "https://fcm.googleapis.com/fcm/send";

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatusCode status = response.getStatusCode();
                return status.is5xxServerError(); // == HttpStatus.Series.SERVER_ERROR;
            }
        });

        // 1.요청헤더 설정용 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "key=" + serverKey);
        headers.add("Content-Type", "application/json; charset=utf-8");

        // {"notificaton",{"title":"알림제목","body":"알림내용"},"data":{"제목키":"제목","내용키":"내용"},"to":"메시지는
        // 보낼 토큰값"}
        // "제목키" 혹은 "내용키"은 임의로..(푸쉬 UI의 파라미터명)


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
        // DTO혹은 Map에는 요청시 서버에 보낼 데이타를 담는다.
        // ※데이타가 Key=Value쌍(application/x-www-form-urlencoded)일때는 반드시 MultiValueMap 사용
        // 데이타가 JSON일때는 (application/json)일때는 MultiValueMap 혹은 Map 사용
        // HttpEntity<DTO혹은 Map> entity = new HttpEntity(DTO혹은 Map객체,headers);

        HttpEntity entity = new HttpEntity(body, headers);

        // 4.RestTemplate으로 요청 보내기
        // 외부 OPEN API서버로부터 받은 데이타 타입이
        // {}인 경우 Map 혹은 DTO
        // [{},{},....]인 경우 List<Map 혹은 DTO>
        ResponseEntity<Map> response = restTemplate.exchange(gcmURL, HttpMethod.POST, // 요청 메소드
                entity, // HttpEntity(요청바디와 요청헤더)
                Map.class // 응답 데이타가 {}일때
        // DTO계열.class//응답 데이타가 {}일때
        // List.class//응답 데이타가 [{},{},....]일때
        );
        System.out.println("FCM서버에 보내는 데이타:" + body.toString());
        System.out.println("응답코드:" + response.getStatusCode());
        System.out.println("응답헤더:" + response.getHeaders());
        System.out.println("응답바디:" + response.getBody());

        return Integer.parseInt(response.getBody().get("success").toString()) == 1;
    }

}
