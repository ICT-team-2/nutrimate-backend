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
	
	
}