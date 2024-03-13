package com.nutrimate.nutrimatebackend.mapper.member;

import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	
	int insertMember(MemberDto memberDto);
	
	int insertMemberWithOAuth(MemberDto memberDto);
	
	int insertOAuth(MemberDto memberDto);
	
	MemberDto findCommonMemberByUid(String userUid);
	
	MemberDto findOAuthMemberByProviderId(String providerId);
	
	MemberDto findMemberInfoById(Long userId);
	
	int updateMemberInfo(MemberDto memberDto);
	
	int updateMemberDiet(MemberDto memberDto);
	
	boolean checkPhoneNumber(String userPhone);
	
	boolean checkNick(String nickName);
	
	boolean checkEmail(String email);
	
	
	int findCheckInsertNutriRatio(MemberDto memberDto); // 영양비율 등록 여부 확인
	
	int insertMemberDiet(MemberDto memberDto); // 영양비율 등록
	
	int deleteMember(int userId); // 영양비율 삭제
}
