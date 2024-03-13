package com.nutrimate.nutrimatebackend.service.alarm;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.alarm.AlarmMapper;
import com.nutrimate.nutrimatebackend.model.alarm.AlarmDto;

@Service
public class AlarmService {
	
	@Autowired
	private AlarmMapper alarmMapper;
	//알람저장
    public int saveAlram(AlarmDto dto) {
      return alarmMapper.insertAlarm(dto);
    }
    
    //알람주별가져오기
    public List<AlarmDto> weekAlram(AlarmDto dto) {
      return alarmMapper.findWeekAlarm(dto);
    }
    
    //알람삭제
    public int deleteAlram(AlarmDto dto) {
      return alarmMapper.deleteAlarmByAlarmId(dto);
    }
    //알람월별가져오기
    public List<AlarmDto> monthAlram(AlarmDto dto) {
      return alarmMapper.findMonthAlarm(dto);
    }

    //알람삭제
}
