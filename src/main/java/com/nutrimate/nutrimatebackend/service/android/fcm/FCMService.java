package com.nutrimate.nutrimatebackend.service.android.fcm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.android.fcm.FCMMapper;

@Service
public class FCMService {
    private FCMMapper mapper;

    @Autowired // 생성자가 하나임으로 생략가능
    public FCMService(FCMMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, String> saveToken(String token) {
        int flag = mapper.insertFcmToken(token);
        Map<String, String> map = new HashMap<String, String>();
        if (flag == 1)
            map.put("TOKEN", "입력 성공");
        else
            map.put("TOKEN", "입력 실패");
        return map;

    }

    public List<String> selectAllTokens() {
        return mapper.findAllTokens();
    }
}
