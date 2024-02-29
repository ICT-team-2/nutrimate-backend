package com.nutrimate.nutrimatebackend.controller.alarm;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.alarm.AlarmDto;
import com.nutrimate.nutrimatebackend.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Slf4j
public class AlarmController {
	
	@Autowired
	private final AlarmService alramService;
	
	
	//알람저장
	@PostMapping("/list")
	public Map saveAlarm(@RequestBody AlarmDto dto) {
	    Map alarmMap = new HashMap();
		//채팅에 참여해 본적이 있는지 확인
	    for (String alarm : dto.getUpdatedAlarmWeek()) {
	      dto.setAlarmTime(alarm); // DTO에 알람 시간 설정
	      System.out.println(dto);
	      int affect = alramService.saveAlram(dto);
	      if (affect == 1) {
	          alarmMap.put("alarmOk", 1); // 알람 성공 상태 코드 설정
	      } else {
	          alarmMap.put("alarmOk", 0); // 알람 실패 상태 코드 설정
	      }
	  }
	  return alarmMap;
	
	
	}
	
	//알람주별가져오기
	/*
	   @PostMapping("/list/week")
	    public Map listAlarm(@RequestBody AlarmDto dto) {
	        Map alarmMap = new HashMap();
	        //채팅에 참여해 본적이 있는지 확인
	        for (String alarm : dto.getUpdatedAlarmWeek()) {
	          dto.setAlarmTime(alarm); // DTO에 알람 시간 설정
	          System.out.println(dto);
	          int affect = alramService.saveAlram(dto);
	          if (affect == 1) {
	              alarmMap.put("alarmOk", 1); // 알람 성공 상태 코드 설정
	          } else {
	              alarmMap.put("alarmOk", 0); // 알람 실패 상태 코드 설정
	          }
	      }
	      return alarmMap;
	    
	    
	    }
	       */
    //알람월별가져오기
    
	
	
}
