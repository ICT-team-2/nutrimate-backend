package com.nutrimate.nutrimatebackend.service.alarm;

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

	
}
