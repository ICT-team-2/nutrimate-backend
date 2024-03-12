package com.nutrimate.nutrimatebackend.mapper.android.fcm;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FCMMapper {
    // 토큰 저장
    int insertFcmToken(String token);

    // 모든 토큰 가져오기
    List<String> findAllTokens();
}
