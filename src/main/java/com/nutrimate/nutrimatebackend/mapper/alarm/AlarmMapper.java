package com.nutrimate.nutrimatebackend.mapper.alarm;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.alarm.AlarmDto;

@Mapper
public interface AlarmMapper {
  
  //알람저장
   int insertAlarm(AlarmDto dto);
}
