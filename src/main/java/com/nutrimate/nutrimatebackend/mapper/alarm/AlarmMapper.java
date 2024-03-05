package com.nutrimate.nutrimatebackend.mapper.alarm;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.alarm.AlarmDto;

@Mapper
public interface AlarmMapper {
  
  //알람저장
   int insertAlarm(AlarmDto dto);

   
  //알람주별가져오기
  List<AlarmDto> findWeekAlarm(AlarmDto dto);

  
  //알람삭제
  int deleteAlarmByAlarmId(AlarmDto dto);

  //알람월별가져오기
  List<AlarmDto> findMonthAlarm(AlarmDto dto);
}
