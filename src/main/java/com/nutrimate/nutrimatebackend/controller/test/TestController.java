package com.nutrimate.nutrimatebackend.controller.test;


import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import com.nutrimate.nutrimatebackend.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping("/time")
	Map testTime() {
		return Map.of("time", testService.getTimeByAnnotation());
	}
	
	@GetMapping("/user/all")
	List<MemberDto> testUser() {
		return testService.getAllUser();
	}
}
