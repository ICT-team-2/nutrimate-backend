package com.nutrimate.nutrimatebackend.service.test;

import com.nutrimate.nutrimatebackend.mapper.test.TestMapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	public String getTimeByAnnotation() {
		return testMapper.getTimeByAnnotation();
	}
	
	public List<MemberDto> getAllUser() {
		return testMapper.getAllUser();
	}
}
