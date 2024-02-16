package com.nutrimate.nutrimatebackend.mapper.test;

import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {
	
	@Select(value = "SELECT sysdate FROM dual")
	String getTimeByAnnotation();
	
	@Select(value = "SELECT * FROM member")
	List<MemberDto> getAllUser();
}
