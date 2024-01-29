package com.nutrimate.nutrimatebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;

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
