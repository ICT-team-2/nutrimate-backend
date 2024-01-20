package com.nutrimate.nutrimatebackend.mapper;

import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	
	int insertCommonMember(MemberDto memberDto);
	
	int insertMember(MemberDto memberDto);
	
	int insertMemberOAuth(MemberDto memberDto);
	
	MemberDto findCommonMemberByUid(String userUid);
	
	MemberDto findOAuthMemberByProviderId(String providerId);
}
