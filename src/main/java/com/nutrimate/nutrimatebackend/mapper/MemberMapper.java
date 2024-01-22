package com.nutrimate.nutrimatebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;

@Mapper
public interface MemberMapper {

  int insertCommonMember(MemberDto memberDto);

  int insertMember(MemberDto memberDto);

  int insertMemberId(MemberDto memberDto);

  int insertOAuthMember(MemberDto memberDto);

  MemberDto findCommonMemberByUid(String userUid);

  MemberDto findOAuthMemberByProviderId(String providerId);


}
